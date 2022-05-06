package com.policyboss.policybosspro.salesmaterial;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity;
import com.policyboss.policybosspro.utility.Constants;
import com.policyboss.policybosspro.utility.TouchImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.AccountDtlEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DocsEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.SalesProductEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity;

public class SalesShareActivity extends BaseActivity implements BaseActivity.PopUpListener {

    DocsEntity docsEntity;
    TouchImageView ivProduct;
    DBPersistanceController dbPersistanceController;
    LoginResponseEntity loginResponseEntity;
    Bitmap salesPhoto;
    Bitmap POSPBitmap = null;
    Bitmap FBABitmap = null;
    Bitmap combinedImage;
    String pospNAme, pospDesg = "LandMark POSP", pospEmail, PospMobNo;
    SalesProductEntity salesProductEntity;
    AccountDtlEntity accountDtlEntity;
    URL pospPhotoUrl = null;
    UserConstantEntity userConstantEntity;
    boolean isSecondImageToShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_share);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerPopUp(this);
        dbPersistanceController = new DBPersistanceController(this);
        userConstantEntity = dbPersistanceController.getUserConstantsData();
        loginResponseEntity = dbPersistanceController.getUserData();
        accountDtlEntity = dbPersistanceController.getAccountData();


        //Realm Thread access denied in Async
        if (userConstantEntity.getParentid() != null
                && !userConstantEntity.getParentid().equals("")
                && !userConstantEntity.getParentid().equals("0")) {

            isSecondImageToShow = false;

        } else {
            isSecondImageToShow = true;
        }

        if (getIntent().hasExtra(Constants.PRODUCT_ID)) {
            salesProductEntity = getIntent().getExtras().getParcelable(Constants.PRODUCT_ID);

            byte[] bytePOSPArray = getIntent().getByteArrayExtra("POSP_IMAGE");
            POSPBitmap = BitmapFactory.decodeByteArray(bytePOSPArray, 0, bytePOSPArray.length);

            byte[] byteFBAArray = getIntent().getByteArrayExtra("FBA_IMAGE");
            FBABitmap = BitmapFactory.decodeByteArray(byteFBAArray, 0, byteFBAArray.length);
        }
        initialize();
        //new createBitmapFromURL(pospPhotoUrl).execute();
        new createBitmapFromURLNew().execute();
    }

    private void initialize() {
        ivProduct = (TouchImageView) findViewById(R.id.ivProduct);

        if (getIntent().hasExtra(Constants.DOC_DATA)) {
            docsEntity = getIntent().getExtras().getParcelable(Constants.DOC_DATA);

            Log.d("PATH", docsEntity.getImage_path());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                if (salesProductEntity.getProduct_Id() == 1 || salesProductEntity.getProduct_Id() == 2) {
                    if (Utility.checkShareStatus(this) == 1) {
                        showShareProduct();
                    } else {
                        openPopUp(ivProduct, "Message", "Your POSP status is INACTIVE", "OK", true);
                    }
                } else {
                    showShareProduct();
                }


                break;

            case R.id.action_home:

                Intent intent = new Intent(this, HomeMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("MarkTYPE", "FROM_HOME");
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showShareProduct() {
        if (combinedImage != null)
            datashareList(SalesShareActivity.this, combinedImage, "PolicyBoss Pro", "");
//        //new shareImageNormal(docsEntity.getImage_path(), "Finmart", "Look what I found on Finmart!").execute();


    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {
        switch (view.getId()) {
            case R.id.ivProduct:
                dialog.cancel();
                break;
        }
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        switch (view.getId()) {
            case R.id.ivProduct:
                dialog.cancel();
                break;
        }
    }

    //region commented
    //    public class createBitmapFromURL extends AsyncTask<Void, Void, Bitmap> {
//
//        URL url;
//
//        public createBitmapFromURL(URL url) {
//            this.url = url;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            showDialog();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Bitmap doInBackground(Void... voids) {
//            Bitmap networkBitmap = null;
//
//            if (url == null) {
//                AssetManager assetManager = getAssets();
//                InputStream istr;
//                try {
//                    istr = assetManager.open("file:///android_asset/profile_pic.png");
//                    networkBitmap = BitmapFactory.decodeStream(istr);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                try {
//                    networkBitmap = BitmapFactory.decodeStream(
//                            pospPhotoUrl.openConnection().getInputStream());
//                    URL salePhotoUrl = new URL(docsEntity.getImage_path());
//                    salesPhoto = BitmapFactory.decodeStream(
//                            salePhotoUrl.openConnection().getInputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return networkBitmap;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//
//            pospPhoto = result;
//
//            try {
//                if (pospPhoto != null) {
//                    // salesPhoto = ((GlideBitmapDrawable) ivProduct.getDrawable()).getBitmap();
//                    /*if (salesPhoto == null) {
//                        try {
//                            salesPhoto = BitmapFactory.decodeStream(pospPhotoUrl.openConnection().getInputStream());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }*/
//                    //datashareList(BaseActivity.this, result);
//                    pospDetails = createBitmap(pospPhoto, pospNAme, pospDesg, PospMobNo, pospEmail);
//                    combinedImage = combineImages(salesPhoto, pospDetails);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    combinedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    Glide.with(SalesShareActivity.this)
//                            .load(stream.toByteArray())
//                            .asBitmap()
//                            .into(ivProduct);
//                    cancelDialog();
//                }
//            } catch (Exception e) {
//                cancelDialog();
//                e.printStackTrace();
//                Log.e("TAG", "Could not load Bitmap from: " + e.getMessage());
//
//            }
//        }
//    }

    //endregion



    public class createBitmapFromURLNew extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            showDialog();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            // Note : Bind Footer ie Agent detail  to combine Image
            if (isSecondImageToShow) {
                try {
                    if (salesProductEntity.getProduct_Id() == 1 || salesProductEntity.getProduct_Id() == 2)
                        // combinedImage = BitmapFactory.decodeStream(new FileInputStream(getImageFromStorage("pospSalesMaterialDetails")));
                        combinedImage = POSPBitmap;

                    else if (salesProductEntity.getProduct_Id() == 3
                            || salesProductEntity.getProduct_Id() == 4
                            || salesProductEntity.getProduct_Id() == 5) {
                        //  combinedImage = BitmapFactory.decodeStream(new FileInputStream(getImageFromStorage("fbaSalesMaterialDetails")));

                        combinedImage = FBABitmap;
                    }
                } catch (Exception e) {

                }
            } else {
                combinedImage = null;
            }

            /**********************************************************************************************
             //Todo  Note  : combine Sales Material Product image and Footer image using" "combineImages" Method
             // No need But We written  mergeProductToFooter() two times only mention
             // case 1,2  : Belong POSP (Insurance)
             // case 3,4,5 : Belong FOS (Loan)
             // Other : Show only Product Image
             ********************************************************************************************/
            switch (salesProductEntity.getProduct_Id()) {
                case 1:
                case 2:
                    //setPospDetails();
                    mergeProductToFooter();
                    break;

                case 3:
                case 4:
                case 5:
                    //setOtherDetails();
                    mergeProductToFooter();
                    break;

                default:
                {
                    if(salesProductEntity.getProduct_Id() > 5) {

                        getOnlyPoductImage();
                    }
                }
            }
            return combinedImage;
        }

        protected void onPostExecute(Bitmap result) {

            cancelDialog();
            if (result == null) {
                Glide.with(SalesShareActivity.this)
                        .load(docsEntity.getImage_path())
                        .asBitmap()
                        .placeholder(getResources().getDrawable(R.drawable.finmart_placeholder))
                        .into(ivProduct);
            } else {
                ivProduct.setImageBitmap(result);
            }


        }


        public void mergeProductToFooter(){

            try {
                URL salePhotoUrl = new URL(docsEntity.getImage_path());
                salesPhoto = BitmapFactory.decodeStream(
                        salePhotoUrl.openConnection().getInputStream());

                if (salesPhoto != null) {
                    combinedImage = combineImages(salesPhoto, combinedImage);
                    //ivProduct.setImageBitmap(combinedImage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void getOnlyPoductImage(){

            try {
                URL salePhotoUrl = new URL(docsEntity.getImage_path());
                salesPhoto = BitmapFactory.decodeStream(
                        salePhotoUrl.openConnection().getInputStream());
                if (salesPhoto != null) {
                    combinedImage = salesPhoto;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
