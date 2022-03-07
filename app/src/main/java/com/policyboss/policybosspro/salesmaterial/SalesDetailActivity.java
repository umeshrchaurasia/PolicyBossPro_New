package com.policyboss.policybosspro.salesmaterial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity;
import com.policyboss.policybosspro.utility.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.salesmaterial.SalesMaterialController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.CompanyEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DocsEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.SalesProductEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.SalesPromotionResponse;

public class SalesDetailActivity extends BaseActivity implements IResponseSubcriber {

    RecyclerView rvCompany;
    RecyclerView rvProduct;
    SalesCompanyAdapter comAdapter;
    SalesDocAdapter docAdapter;
    TextView txtEng, txtHindi;
    Switch swlang;
    SalesProductEntity salesProductEntity;

    private List<CompanyEntity> companyLst;
    private List<DocsEntity> docLst;
    int numberOfColumns = 2;
    DBPersistanceController dbPersistanceController;
    private String langType = "English";
    private String companyID;
    UserConstantEntity userConstantEntity;

    String pospNAme, pospDesg = "LandMark POSP", pospEmail, PospMobNo;
    String fbaNAme, fbaDesg = "FBA SUPPORT ASSISTANT", fbaEmail, fbaMobNo;
    URL pospPhotoUrl = null, fbaPhotoUrl = null;

    byte[] bytePOSPArray = null;
    byte[] byteFBAArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbPersistanceController = new DBPersistanceController(SalesDetailActivity.this);
        userConstantEntity = dbPersistanceController.getUserConstantsData();
        companyID = "0";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            salesProductEntity = extras.getParcelable(Constants.PRODUCT_ID);
            //The key argument here must match that used in the other activity
        }
        init();
        if (userConstantEntity != null) {
            try {
                setOtherDetails();
                setPospDetails();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        showDialog();
        new SalesMaterialController(this).getProductPromotions(salesProductEntity.getProduct_Id(), SalesDetailActivity.this);

    }

    private void init() {

        companyLst = new ArrayList<CompanyEntity>();
        docLst = new ArrayList<DocsEntity>();

        rvCompany = (RecyclerView) findViewById(R.id.rvCompany);
        rvCompany.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCompany.setLayoutManager(layoutManager);

        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, numberOfColumns);
        rvProduct.setLayoutManager(layoutManager1);

        txtEng = (TextView) findViewById(R.id.txtEng);
        txtHindi = (TextView) findViewById(R.id.txtHindi);
        swlang = (Switch) findViewById(R.id.swlang);

        swlang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    langType = "Hindi";

                    txtHindi.setTextColor(ContextCompat.getColor(SalesDetailActivity.this, R.color.colorAccent));
                    // txtHindi.getContext().getResources().getString(R.string.Hindi_bold);;
                    txtHindi.setTypeface(null, Typeface.BOLD);

                    txtEng.setTextColor(ContextCompat.getColor(SalesDetailActivity.this, R.color.seperator_white));
                    //  txtEng.getContext().getResources().getString(R.string.Eng);;
                    txtEng.setTypeface(null, Typeface.NORMAL);


                } else {
                    langType = "English";
                    txtEng.setTextColor(ContextCompat.getColor(SalesDetailActivity.this, R.color.colorAccent));
                    //txtEng.getContext().getResources().getString(R.string.Eng_bold);
                    txtEng.setTypeface(null, Typeface.BOLD);

                    txtHindi.setTextColor(ContextCompat.getColor(SalesDetailActivity.this, R.color.seperator_white));
                    txtHindi.setTypeface(null, Typeface.NORMAL);


                }

                bindDocList(companyID, langType);
            }
        });


    }

    private void bindCompanyList() {

        if (companyLst.size() > 0) {
            companyLst.get(0).setSelected(true);
            comAdapter = new SalesCompanyAdapter(SalesDetailActivity.this, companyLst);
            rvCompany.setAdapter(comAdapter);

            if (swlang.isChecked()) {
                langType = "Hindi";
            } else {
                langType = "English";
            }
            companyID = companyLst.get(0).getCompany_id();
            bindDocList(companyID, langType);
        } else {
            rvCompany.setAdapter(null);

        }
    }

    private void bindDocList(String compID, String langType) {

        List<DocsEntity> docLst = dbPersistanceController.getDocList(compID, langType);
        if (docLst.size() > 0) {
            docAdapter = new SalesDocAdapter(SalesDetailActivity.this, docLst);

            rvProduct.setAdapter(docAdapter);
        } else {
            rvProduct.setAdapter(null);

        }
    }

    private void setPospDetails() throws MalformedURLException {


        pospNAme = "POSP Name";
        pospEmail = "XXXXXX@finmart.com";
        pospDesg = "LandMark POSP";
        PospMobNo = "98XXXXXXXX";
        pospPhotoUrl = new URL("http://api.magicfinmart.com/images/profile_pic.png");
        if (userConstantEntity != null) {
            if (userConstantEntity.getPospsendname() != null && !userConstantEntity.getPospsendname().equals("")) {
                pospNAme = userConstantEntity.getPospsendname();
            }
            if (userConstantEntity.getPospsendemail() != null && !userConstantEntity.getPospsendemail().equals("")) {
                pospEmail = userConstantEntity.getPospsendemail();
            }
            if (userConstantEntity.getPospsendmobile() != null && !userConstantEntity.getPospsendmobile().equals("")) {
                PospMobNo = userConstantEntity.getPospsendmobile();
            }
            if (userConstantEntity.getPospsenddesignation() != null && !userConstantEntity.getPospsenddesignation().equals("")) {
                pospDesg = userConstantEntity.getPospsenddesignation();
            }
            if (userConstantEntity.getPospsendphoto() != null && !userConstantEntity.getPospsendphoto().equals("")) {
                pospPhotoUrl = new URL(userConstantEntity.getPospsendphoto());
            }
        }
    }

    private void setOtherDetails() throws MalformedURLException {


        fbaNAme = "FBA Name";
        fbaEmail = "XXXXXX@finmart.com";
        fbaDesg = "FBA SUPPORT ASSISTANT";
        fbaMobNo = "98XXXXXXXX";
        fbaPhotoUrl = new URL("http://api.magicfinmart.com/images/profile_pic.png");
        if (userConstantEntity != null) {
            if (userConstantEntity.getLoansendname() != null && !userConstantEntity.getLoansendname().equals("")) {
                fbaNAme = userConstantEntity.getLoansendname();
            }
            if (userConstantEntity.getLoansendemail() != null && !userConstantEntity.getLoansendemail().equals("")) {
                fbaEmail = userConstantEntity.getLoansendemail();
            }
            if (userConstantEntity.getLoansendmobile() != null && !userConstantEntity.getLoansendmobile().equals("")) {
                fbaMobNo = userConstantEntity.getLoansendmobile();
            }
            if (userConstantEntity.getLoansenddesignation() != null && !userConstantEntity.getLoansenddesignation().equals("")) {
                fbaDesg = userConstantEntity.getLoansenddesignation();
            }
            if (userConstantEntity.getLoansendphoto() != null && !userConstantEntity.getLoansendphoto().equals("")) {
                fbaPhotoUrl = new URL(userConstantEntity.getLoansendphoto());
            }
        }
    }
    @Override
    public void OnSuccess(APIResponse response, String message) {

      //  cancelDialog();
        if (response instanceof SalesPromotionResponse) {
            companyLst = ((SalesPromotionResponse) response).getMasterData().getCompany();

            bindCompanyList();

            //region Added fba and posp Prof pic

            new createBitmapFromURLPosp(pospPhotoUrl).execute();

            new createBitmapFromURFba(fbaPhotoUrl).execute();

            // endregion
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    public void filterDocViaCompID(String compID) {
        companyID = compID;
        if (swlang.isChecked()) {
            langType = "Hindi";
        } else {
            langType = "English";
        }
        bindDocList(compID, langType);
    }

    public void redirectToApplyMain(DocsEntity docsEntity) {

        Intent intent = new Intent(this, SalesShareActivity.class);
        intent.putExtra(Constants.DOC_DATA, docsEntity);
        intent.putExtra(Constants.PRODUCT_ID, salesProductEntity);
        intent.putExtra("POSP_IMAGE", bytePOSPArray);
        intent.putExtra("FBA_IMAGE", byteFBAArray);
        startActivity(intent);
    }


    private List<CompanyEntity> getComLst() {
        List<CompanyEntity> companyLst = new ArrayList<>();

        companyLst.add(new CompanyEntity("1", "General"));
        companyLst.add(new CompanyEntity("2", "Bharti"));
        companyLst.add(new CompanyEntity("3", "Liberty Videcon"));
        companyLst.add(new CompanyEntity("4", "SRL Diagno"));
        companyLst.add(new CompanyEntity("5", "TATA"));
        companyLst.add(new CompanyEntity("6", "General 2"));
        return companyLst;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_home:

                Intent intent = new Intent(this, HomeMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("MarkTYPE", "FROM_HOME");
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    public class createBitmapFromURLPosp extends AsyncTask<Void, Void, Bitmap> {
        URL url;

        public createBitmapFromURLPosp(URL url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap networkBitmap = null;
            try {
                if (url == null) {
                    Drawable d = getResources().getDrawable(R.drawable.profile_pic);
                    networkBitmap = ((BitmapDrawable) d).getBitmap();
                } else {
                    networkBitmap = BitmapFactory.decodeStream(
                            url.openConnection().getInputStream());
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TAG", "Could not load Bitmap from: " + url);
            } finally {
                if (networkBitmap == null) {
                    Drawable d = getResources().getDrawable(R.drawable.profile_pic);
                    networkBitmap = ((BitmapDrawable) d).getBitmap();
                }
            }

            return networkBitmap;
        }

        @SuppressLint("WrongThread")
        protected void onPostExecute(Bitmap result) {
            if (result != null) {

                Bitmap pospDetails = createBitmap(result, pospNAme, pospDesg, PospMobNo, pospEmail);
                // saveImageToStorage(pospDetails, "pospSalesMaterialDetails");

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                pospDetails.compress(Bitmap.CompressFormat.PNG, 100, bStream);

                byte[] byteArray = bStream.toByteArray();
                bytePOSPArray = byteArray;

            }
            // bitmap_image = result;
        }
    }




    public class createBitmapFromURFba extends AsyncTask<Void, Void, Bitmap> {
        URL url;

        public createBitmapFromURFba(URL url) {
            this.url = url;
        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap networkBitmap = null;
            try {

                if (url == null) {
                    Drawable d = getResources().getDrawable(R.drawable.profile_pic);
                    networkBitmap = ((BitmapDrawable) d).getBitmap();
                } else {
                    networkBitmap = BitmapFactory.decodeStream(
                            url.openConnection().getInputStream());
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TAG", "Could not load Bitmap from: " + url);
            } finally {
                if (networkBitmap == null) {
                    Drawable d = getResources().getDrawable(R.drawable.profile_pic);
                    networkBitmap = ((BitmapDrawable) d).getBitmap();
                }
            }

            return networkBitmap;
        }


        @SuppressLint("WrongThread")
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                cancelDialog();

                Bitmap fbaDetails = createBitmap(result, fbaNAme, fbaDesg, fbaMobNo, fbaEmail);
                // saveImageToStorage(fbaDetails, "fbaSalesMaterialDetails");
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                fbaDetails.compress(Bitmap.CompressFormat.PNG, 100, bStream);

                byte[] byteArray = bStream.toByteArray();

                byteFBAArray = byteArray;

            }
        }
    }

}
