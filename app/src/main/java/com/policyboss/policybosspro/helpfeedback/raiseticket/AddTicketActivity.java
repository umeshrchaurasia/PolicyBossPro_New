package com.policyboss.policybosspro.helpfeedback.raiseticket;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.zoho.ZohoController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ZohoTicketCategoryEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.CreateTicketrequest;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.CreateTicketResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.DocumentResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.TicketCategoryResponse;
import okhttp3.MultipartBody;

public class AddTicketActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {
    PrefManager prefManager;
    DBPersistanceController dbPersistanceController;
    LoginResponseEntity loginResponseEntity;
    ZohoTicketCategoryEntity zohoTicketCategoryEntity;

    Spinner spCategory, spSubCategories, spClassification;
    List<String> categoryList, subCategoryList, classList;
    ArrayAdapter<String> categoryAdapter, subCategoriesAdapter, classAdapter;

    TextView tvBrowse;
    EditText etFileNAme, etMessage;
    Button btnSubmit;
    private static final int SELECT_PICTURE = 1802;
    HashMap<String, String> body;
    MultipartBody.Part part;
    File file;
    String categoryId = "",subCategoryId_bind="";
    int subCategoryId = 0, classId = 0;
    CreateTicketrequest createTicketrequest;
    String et_Type_ticket ="",et_Crn_ticket ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dbPersistanceController = new DBPersistanceController(this);
        loginResponseEntity = dbPersistanceController.getUserData();
        prefManager = new PrefManager(this);
        createTicketrequest = new CreateTicketrequest();
        init_widgets();
        setListener();
        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();
        classList = new ArrayList<>();

        /*if (prefManager.getIsZohoMaster()) {
            showDialog();
            new ZohoController(this).getTicketCategories(this);
        } else {
            zohoTicketCategoryEntity = dbPersistanceController.getZohoCategoryList();
        }*/

        showDialog();
        new ZohoController(this).getTicketCategories(this);

        spCategory.setOnItemSelectedListener(spCategoryListener);
        spSubCategories.setOnItemSelectedListener(spSubCategoriesListner);
        spClassification.setOnItemSelectedListener(spClassificationListener);

    }

    private void bindadapterfromotor() {

        try {
            if(getIntent().hasExtra("ProductType")) {
                if (getIntent().getStringExtra("ProductType") != null) {
                    et_Type_ticket = getIntent().getStringExtra("ProductType");

                  //  spCategory.setOnItemSelectedListener(null);
                 //   spSubCategories.setOnItemSelectedListener(null);
                //    spClassification.setOnItemSelectedListener(null);

                //    if(et_Type_ticket.equals("MOTOR"))
               //     {
                        int varientIndex = 0;
                        for (int i = 0; i < categoryList.size(); i++) {

                            String variantName = "Product Related";
                            String vari = categoryList.get(i);
                            if (variantName.equalsIgnoreCase(vari)) {
                                varientIndex = i;
                                break;
                            }
                        }
                        spCategory.setSelection(varientIndex);

                        //


//                        categoryId = dbPersistanceController.getCategoryId(zohoTicketCategoryEntity, spCategory.getSelectedItem().toString());
//                        if (!categoryId.equals("")) {
//                            subCategoryList.clear();
//                            List<String> subCategory_varList  = dbPersistanceController.getSubCategoryList(zohoTicketCategoryEntity, categoryId);
//                            subCategoryList.addAll(subCategory_varList);
//                            subCategoriesAdapter.notifyDataSetChanged();
//                        }


//
//                        int varientIndexSub = 0;
//                        for (int i = 0; i < subCategoryList.size(); i++) {
//
//                            String variantName = "Insurance Products";
//                            String vari = subCategoryList.get(i);
//                            if (variantName.equalsIgnoreCase(vari)) {
//                                varientIndexSub = i;
//                                break;
//                            }
//                        }

//                        spSubCategories.setSelection(varientIndexSub);
//
//
//                        //
//                        subCategoryId = dbPersistanceController.getSubCategoryId(zohoTicketCategoryEntity, spSubCategories.getSelectedItem().toString());
//                        if (subCategoryId !=0) {
//                            classList.clear();
//                            List<String> classList_varList  = dbPersistanceController.getClassificationList(zohoTicketCategoryEntity, subCategoryId);
//                            classList.addAll(classList_varList);
//                            classAdapter.notifyDataSetChanged();
//                        }
//
//                        int varientIndexcl = 0;
//                        for (int i = 0; i < classList.size(); i++) {
//
//                            String variantName = "Motor";
//                            String vari = classList.get(i);
//                            if (variantName.equalsIgnoreCase(vari)) {
//                                varientIndexcl = i;
//                                break;
//                            }
//                        }
//                        spClassification.setSelection(varientIndexcl);
//                    }
//                     else  if(et_Type_ticket.equals("TWO WHEELER"))
//                    {
//
//                    }
                }

                if (getIntent().getStringExtra("crn") != null) {
                    et_Crn_ticket = String.valueOf(getIntent().getStringExtra("crn"));

                  //  etMessage.setText("CRN No : "+et_Crn_ticket);
                    etMessage.setHint("CRN No: "+et_Crn_ticket);
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            spCategory.setOnItemSelectedListener(spCategoryListener);
            spSubCategories.setOnItemSelectedListener(spSubCategoriesListner);
            spClassification.setOnItemSelectedListener(spClassificationListener);
        }
    }

    private void init_adapters() {
        categoryList = dbPersistanceController.getCategoryList(zohoTicketCategoryEntity);
        subCategoryList = dbPersistanceController.getSubCategoryList(zohoTicketCategoryEntity, "198798");
        classList = dbPersistanceController.getClassificationList(zohoTicketCategoryEntity, 9789);
        categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spCategory.setAdapter(categoryAdapter);


        subCategoriesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subCategoryList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spSubCategories.setAdapter(subCategoriesAdapter);


        classAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, classList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spClassification.setAdapter(classAdapter);

        bindadapterfromotor();

    }



        //region category


       AdapterView.OnItemSelectedListener spCategoryListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                categoryId = dbPersistanceController.getCategoryId(zohoTicketCategoryEntity, adapterView.getSelectedItem().toString());
                if (!categoryId.equals("")) {
                    subCategoryList.clear();
                    subCategoryList = dbPersistanceController.getSubCategoryList(zohoTicketCategoryEntity, categoryId);
                    subCategoriesAdapter = new ArrayAdapter(AddTicketActivity.this, android.R.layout.simple_list_item_1, subCategoryList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    spSubCategories.setAdapter(subCategoriesAdapter);
                    if(getIntent().hasExtra("ProductType")) {
                         int varientIndexSub = 0;
                        for (int i = 0; i < subCategoryList.size(); i++) {

                            String variantName = "Insurance Products";
                            String vari = subCategoryList.get(i);
                            if (variantName.equalsIgnoreCase(vari)) {
                                varientIndexSub = i;
                                break;
                            }
                        }
                        spSubCategories.setSelection(varientIndexSub);

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        //endregion

        //region subCategory
        AdapterView.OnItemSelectedListener spSubCategoriesListner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                subCategoryId = dbPersistanceController.getSubCategoryId(zohoTicketCategoryEntity, adapterView.getSelectedItem().toString());
                if (!categoryId.equals("")) {
                    classList.clear();
                    classList = dbPersistanceController.getClassificationList(zohoTicketCategoryEntity, subCategoryId);
                    classAdapter = new ArrayAdapter(AddTicketActivity.this, android.R.layout.simple_list_item_1, classList) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            } else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    spClassification.setAdapter(classAdapter);

                    if(getIntent().hasExtra("ProductType")) {
                        int varientIndexcl = 0;
                        if(et_Type_ticket.equals("MOTOR"))
                        {

                        for (int i = 0; i < classList.size(); i++) {

                            String variantName = "Motor";
                            String vari = classList.get(i);
                            if (variantName.equalsIgnoreCase(vari)) {
                                varientIndexcl = i;
                                break;

                            }
                        }
                        }else  if(et_Type_ticket.equals("TWO WHEELER"))
                        {
                            for (int i = 0; i < classList.size(); i++) {

                                String variantName = "2W";
                                String vari = classList.get(i);
                                if (variantName.equalsIgnoreCase(vari)) {
                                    varientIndexcl = i;
                                    break;

                                }
                            }
                        }
                        spClassification.setSelection(varientIndexcl);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        //endregion

        //region classification
        AdapterView.OnItemSelectedListener spClassificationListener= new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                classId = dbPersistanceController.getClassificationId(zohoTicketCategoryEntity, adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        //endregion



    private void init_widgets() {
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spSubCategories = (Spinner) findViewById(R.id.spSubCategories);
        spClassification = (Spinner) findViewById(R.id.spClassification);
        tvBrowse = (TextView) findViewById(R.id.tvBrowse);
        etFileNAme = (EditText) findViewById(R.id.etFileNAme);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void setListener() {
        tvBrowse.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        spCategory.setOnItemSelectedListener(spCategoryListener);
        spSubCategories.setOnItemSelectedListener(spSubCategoriesListner);
        spClassification.setOnItemSelectedListener(spClassificationListener);

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {
        if (response instanceof TicketCategoryResponse) {
            cancelDialog();
            zohoTicketCategoryEntity = ((TicketCategoryResponse) response).getMasterData();
            init_adapters();
        } else if (response instanceof DocumentResponse) {
            cancelDialog();
            etFileNAme.setText("File Uploaded Success");
            //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            //setDocumentUpload()
        } else if (response instanceof CreateTicketResponse) {
            cancelDialog();
            if (response.getStatusNo() == 0) {
                Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
               /* init_adapters();
                etMessage.setText("");
                etFileNAme.setText("No files attached");*/
                Intent intent = new Intent();
                setResult(2, intent);
                finish();

            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvBrowse:
                openGallery();
                break;
            case R.id.btnSubmit:
                if (categoryId.equals("")) {
                    Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (subCategoryId == 0) {
                    Toast.makeText(this, "Select Sub-Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etMessage.getText().toString().isEmpty()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etMessage.requestFocus();
                        etMessage.setError("Enter Message");
                        etMessage.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etMessage.requestFocus();
                        etMessage.setError("Enter Message");
                        return;
                    }
                }

                createTicketrequest.setFBAID(loginResponseEntity.getFBAId());
                createTicketrequest.setCategoryId(Integer.parseInt(categoryId));
                createTicketrequest.setClassification(classId);
                createTicketrequest.setSubCategoryId(subCategoryId);
                createTicketrequest.setMessage(etMessage.getText().toString());
                createTicketrequest.setProductname(et_Type_ticket);
                createTicketrequest.setCrnloan(et_Crn_ticket);
                showDialog();
                new ZohoController(this).createTicket(createTicketrequest, this);
                break;

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Bitmap mphoto = null;
            try {
               // mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                mphoto = getBitmapFromContentResolver(selectedImageUri);
                mphoto = getResizedBitmap(mphoto, 400);

                showDialog();
                file = saveImageToStorage(mphoto, "" + 12);
                part = Utility.getMultipartImage(file);
                body = Utility.getBody(this, loginResponseEntity.getFBAId(), 12, "Tiket");
                new RegisterController(this).uploadDocuments(part, body, this);

            } catch (Exception ex) {

            }
        }

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
