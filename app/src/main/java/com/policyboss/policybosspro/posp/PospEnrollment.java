package com.policyboss.policybosspro.posp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.myaccount.MyAccountActivity;
import com.policyboss.policybosspro.payment.RazorPaymentActivity;
import com.policyboss.policybosspro.utility.Constants;
import com.policyboss.policybosspro.utility.DateTimePicker;
import com.policyboss.policybosspro.webviews.MyWebViewClient;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.tracking.TrackingController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DocAvailableEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.IfscEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.PospDetailsEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.PospEnrollEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.TrackingData;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.RegisterRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.TrackingRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.DocumentResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.EnrollPospResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.IfscCodeResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PincodeResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PospDetailsResponse;
import okhttp3.MultipartBody;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by daniyalshaikh on 11/01/18.
 */

public class PospEnrollment extends BaseActivity implements View.OnClickListener, BaseActivity.PopUpListener, IResponseSubcriber, View.OnFocusChangeListener {

    private static final int CAMERA_REQUEST = 1889;
    private static final int SELECT_PICTURE = 1801;
    int type;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");

    LinearLayout llMyProfile, llAddress, llBankDetail, llDocumentUpload;
    ImageView ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload;
    RelativeLayout rlMyProfile, rlAddress, rlBankDetail, rlDocumentUpload;
    Dialog dialog;
    boolean isPospInfo, isAddress, isBankDetails, isDocumentsUpload, isMale;

    //region inputs
    EditText etFirstName, etLastName, etDob, etMobileNo1, etMobileNo2,
            etEmailId, etPan, etAadhar, etGST, etChannelPartner;
    TextView tvMale, tvFemale;

    //region address
    EditText etAddress1, etAddress2, etAddress3, etPincode, etCity, etState;

    //region bank details
    EditText etBankAcNo, etAccountType, etIfscCode, erMicrCode, etBankBranch, etBankCity, etBankName;
    TextView txtSaving, txtCurrent;
    IfscEntity ifscEntity;
    public String ACCOUNT_TYPE = "SAVING";
    RegisterRequestEntity registerRequestEntity;
    Button btnSave;
    WebView webView;
    String URL = "http://www.irdaonline.org/PublicAccess/LookUpPAN.aspx";
    int count = 0;
    PrefManager prefManager;
    PospDetailsEntity pospDetailsEntity;
    DBPersistanceController dbPersistanceController;
    PospEnrollEntity pospEnrollEntity;
    LoginResponseEntity loginResponseEntity;

    ImageView ivPhotoCam, ivPhotoView, ivPanCam, ivPanView, ivCancelCam, ivCancelView, ivAadharCam, ivAadharView,
            ivAadharCamBack, ivAadharBackView, ivEduCam, ivEduView,
            ivAadhar, ivAadharBack, ivCancel, ivPan, ivPhoto, ivEdu;

    HashMap<String, String> body;
    MultipartBody.Part part;
    File file;
    File Docfile;
    InputStream inputStream;
    ExifInterface ei;
    Uri imageUri;
    private Uri cropImageUri;
    private int POSP_PHOTO = 6, POSP_PAN = 7, POSP_AADHAR_FRONT = 8, POSP_AADHAR_BACK = 9, POSP_CANCEL_CHQ = 10, POSP_EDU = 11;
    private String PHOTO_File = "POSPPhotograph", PAN_File = "POSPPanCard", CANCEL_CHQ_File = "POSPCancelledChq", AADHAR_FRONT_File = "POSPAadharCard", AADHAR_BACK_File = "POSPAadharCardBack", EDU_FILE = "POSPHighestEducationProof";
    LinearLayout llMain;
    boolean IsAllImageUploaded = false, isPospNoAvailable = false, isPaymentLinkAvailable = true, isPaymentDone = false;

    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posp_enrollment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerPopUp(this);

        dbPersistanceController = new DBPersistanceController(this);
        loginResponseEntity = dbPersistanceController.getUserData();
        registerRequestEntity = new RegisterRequestEntity();
        registerRequestEntity.setFBAID(loginResponseEntity.getFBAId());
        if (loginResponseEntity.getCustID() != null && !loginResponseEntity.getCustID().equals("")) {
            registerRequestEntity.setCustID(Integer.parseInt(loginResponseEntity.getCustID()));
        }

        prefManager = new PrefManager(this);
        initWidgets();
        setListener();
        initLayouts();
        setTextWatcher();
        setfileView();
        showDialog("Fetching Posp Details ...");
        //MObile NO display
        etMobileNo1.setText(loginResponseEntity.getMobiNumb1().trim());
        //  etAddress1.setText(loginResponseEntity.getEmailID());
        etEmailId.setText(loginResponseEntity.getEmailID().trim());


        new RegisterController(this).getPospDetails(this);
        //setInputParameters();

    }

    private void setfileView(){

        ivPhotoView.setVisibility(View.GONE);
        ivAadharView.setVisibility(View.GONE);
        ivAadharBackView.setVisibility(View.GONE);
        ivPanView.setVisibility(View.GONE);
        ivEduView.setVisibility(View.GONE);
        ivCancelView.setVisibility(View.GONE);
    }
    private void setInputParameters() {


        // Date RegDate = simpleDateFormat.parse(motorRequestEntity.getVehicle_registration_date());
        // String regDate = displayFormat.format(RegDate);


        if (prefManager.getPospInformation() != null) {
            registerRequestEntity = prefManager.getPospInformation();
        }
        if (registerRequestEntity != null) {

            //regionset profile Details
            if (!registerRequestEntity.getPosp_FirstName().equals("") && registerRequestEntity.getPosp_FirstName() != null) {
                etFirstName.setText("" + registerRequestEntity.getPosp_FirstName());
            }
            if (!registerRequestEntity.getPosp_LastName().equals("") && registerRequestEntity.getPosp_LastName() != null) {
                etLastName.setText("" + registerRequestEntity.getPosp_LastName());
            }
            if (!registerRequestEntity.getPosp_DOB().equals("") && registerRequestEntity.getPosp_DOB() != null) {
                // etDob.setText("" + registerRequestEntity.getPosp_DOB());

                try {
                    Date dob = simpleDateFormat.parse(registerRequestEntity.getPosp_DOB());
                    String strDOB = displayFormat.format(dob);
                    etDob.setText(strDOB);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (registerRequestEntity.getPosp_Gender().equals("F")) {
                tvFemale.performClick();
            } else {
                tvMale.performClick();
            }
            if (!registerRequestEntity.getPosp_Mobile1().equals("") && registerRequestEntity.getPosp_Mobile1() != null) {
                etMobileNo1.setText("" + registerRequestEntity.getPosp_Mobile1());
            }
            if (!registerRequestEntity.getPosp_Mobile2().equals("") && registerRequestEntity.getPosp_Mobile2() != null) {
                etMobileNo2.setText("" + registerRequestEntity.getPosp_Mobile2());
            }
            if (!registerRequestEntity.getPosp_Email().equals("") && registerRequestEntity.getPosp_Email() != null) {
                etEmailId.setText("" + registerRequestEntity.getPosp_Email().trim());
            }
            if (!registerRequestEntity.getPosp_PAN().equals("") && registerRequestEntity.getPosp_PAN() != null) {
                etPan.setText("" + registerRequestEntity.getPosp_PAN());
            }
            if (!registerRequestEntity.getPosp_Aadhaar().equals("") && registerRequestEntity.getPosp_Aadhaar() != null) {
                etAadhar.setText("" + registerRequestEntity.getPosp_Aadhaar());
            }
            if (!registerRequestEntity.getPosp_ServiceTaxNo().equals("") && registerRequestEntity.getPosp_ServiceTaxNo() != null) {
                etGST.setText("" + registerRequestEntity.getPosp_ServiceTaxNo());
            }
            if (!registerRequestEntity.getPosp_ChanPartCode().equals("") && registerRequestEntity.getPosp_ChanPartCode() != null) {
                etChannelPartner.setText("" + registerRequestEntity.getPosp_ChanPartCode());
            }
            //endregion

            //region set address details

            if (!registerRequestEntity.getPosp_Address1().equals("") && registerRequestEntity.getPosp_Address1() != null) {
                etAddress1.setText("" + registerRequestEntity.getPosp_Address1());
            }
            if (!registerRequestEntity.getPosp_Address2().equals("") && registerRequestEntity.getPosp_Address2() != null) {
                etAddress2.setText("" + registerRequestEntity.getPosp_Address2());
            }
            if (!registerRequestEntity.getPosp_Address3().equals("") && registerRequestEntity.getPosp_Address3() != null) {
                etAddress3.setText("" + registerRequestEntity.getPosp_Address3());
            }
            if (!registerRequestEntity.getPosp_PinCode().equals("") && registerRequestEntity.getPosp_PinCode() != null) {
                etPincode.setText("" + registerRequestEntity.getPosp_PinCode());
            }
            if (!registerRequestEntity.getPosp_City().equals("") && registerRequestEntity.getPosp_City() != null) {
                etCity.setText("" + registerRequestEntity.getPosp_City());
            }
            if (!registerRequestEntity.getPosp_StatID().equals("") && registerRequestEntity.getPosp_StatID() != null) {
                etState.setText("" + registerRequestEntity.getPosp_StatID());
            }
            //endregion

            //region set bank details
            if (!registerRequestEntity.getPosp_BankAcNo().equals("") && registerRequestEntity.getPosp_BankAcNo() != null) {
                etBankAcNo.setText("" + registerRequestEntity.getPosp_BankAcNo());
            }
            if (registerRequestEntity.getPosp_Account_Type().equals("CURRENT")) {
                setSavingAcc();
            } else {
                setSavingAcc();
            }
            if (!registerRequestEntity.getPosp_IFSC().equals("") && registerRequestEntity.getPosp_IFSC() != null) {
                etIfscCode.setText("" + registerRequestEntity.getPosp_IFSC());
            }
            if (!registerRequestEntity.getPosp_MICR().equals("") && registerRequestEntity.getPosp_MICR() != null) {
                erMicrCode.setText("" + registerRequestEntity.getPosp_MICR());
            }
            if (!registerRequestEntity.getPosp_BankName().equals("") && registerRequestEntity.getPosp_BankName() != null) {
                etBankName.setText("" + registerRequestEntity.getPosp_BankName());
            }
            if (!registerRequestEntity.getPosp_BankBranch().equals("") && registerRequestEntity.getPosp_BankBranch() != null) {
                etBankBranch.setText("" + registerRequestEntity.getPosp_BankBranch());
            }
            if (!registerRequestEntity.getPosp_BankCity().equals("") && registerRequestEntity.getPosp_BankCity() != null) {
                etBankCity.setText("" + registerRequestEntity.getPosp_BankCity());
            }
            //endregion
        }

    }

    private void bindUploadImage() {

        if (pospDetailsEntity != null) {
            List<DocAvailableEntity> docAvailableEntityList = pospDetailsEntity.getDoc_available();
            for (DocAvailableEntity docAvailableEntity : docAvailableEntityList) {
                if (!docAvailableEntity.getFileName().equals("")) {
                    setUploadedDocument(docAvailableEntity.getDocType(), docAvailableEntity.getFileName());
                }
            }
        }

    }

    private boolean checkAllImageUpload() {
        int count = 0;
        if (pospDetailsEntity != null) {
            List<DocAvailableEntity> docAvailableEntityList = pospDetailsEntity.getDoc_available();
            for (DocAvailableEntity docAvailableEntity : docAvailableEntityList) {
                if (!docAvailableEntity.getFileName().equals("")) {
                    int docType = docAvailableEntity.getDocType();
                    if (docType == 6 || docType == 7 || docType == 8 || docType == 9 || docType == 10 || docType == 11) {
                        count++;
                    }
                }
            }
        }

        if (count >= 6)
            return true;
        else
            return false;
    }

    private void bindInputFromeServer(PospDetailsEntity registerRequestEntity) {


        //set profile Details
        if (registerRequestEntity.getPosp_First_Name() != null && !registerRequestEntity.getPosp_First_Name().equals("")) {
            etFirstName.setText("" + registerRequestEntity.getPosp_First_Name());
        }
        if (registerRequestEntity.getPosp_Last_Name() != null && !registerRequestEntity.getPosp_Last_Name().equals("")) {
            etLastName.setText("" + registerRequestEntity.getPosp_Last_Name());
        }
        if (registerRequestEntity.getPosp_PinCode() != null && !registerRequestEntity.getPosp_PinCode().equals("")) {
            // showDialog();
            new RegisterController(this).getCityState(registerRequestEntity.getPosp_PinCode(), this);
        }
        /*if (registerRequestEntity.getPosp_IFSC() != null && !registerRequestEntity.getPosp_IFSC().equals("")) {
            //showDialog();
            new RegisterController(this).getIFSC(registerRequestEntity.getPosp_IFSC(), this);
        }*/
        if (registerRequestEntity.getPosp_DOB() != null && !registerRequestEntity.getPosp_DOB().equals("")) {
            //etDob.setText("" + registerRequestEntity.getPosp_DOB());

            try {
                Date dob = simpleDateFormat.parse(registerRequestEntity.getPosp_DOB());
                String strDOB = displayFormat.format(dob);
                etDob.setText(strDOB);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (registerRequestEntity.getPosp_Gender() != null) {
            if (registerRequestEntity.getPosp_Gender().equals("F")) {
                tvFemale.performClick();
            } else {
                tvMale.performClick();
            }
        }

        if (registerRequestEntity.getPosp_Mobile1() != null && !registerRequestEntity.getPosp_Mobile1().equals("")) {
            etMobileNo1.setText("" + registerRequestEntity.getPosp_Mobile1());
        }
        if (registerRequestEntity.getPosp_Mobile2() != null && !registerRequestEntity.getPosp_Mobile2().equals("")) {
            etMobileNo2.setText("" + registerRequestEntity.getPosp_Mobile2());
        }
        if (registerRequestEntity.getPosp_Email() != null && !registerRequestEntity.getPosp_Email().equals("")) {
            etEmailId.setText("" + registerRequestEntity.getPosp_Email().trim());
        }
        if (registerRequestEntity.getPosp_PAN() != null && !registerRequestEntity.getPosp_PAN().equals("")) {
            etPan.setText("" + registerRequestEntity.getPosp_PAN());
        }
        if (registerRequestEntity.getPosp_Aadhaar() != null && !registerRequestEntity.getPosp_Aadhaar().equals("")) {
            etAadhar.setText("" + registerRequestEntity.getPosp_Aadhaar());
        }
        if (registerRequestEntity.getPosp_ServiceTaxNo() != null && !registerRequestEntity.getPosp_ServiceTaxNo().equals("")) {
            etGST.setText("" + registerRequestEntity.getPosp_ServiceTaxNo());
        }
        if (registerRequestEntity.getPosp_ChanPartCode() != null && !registerRequestEntity.getPosp_ChanPartCode().equals("")) {
            etChannelPartner.setText("" + registerRequestEntity.getPosp_ChanPartCode());
        }

        //set address details

        if (registerRequestEntity.getPosp_Address1() != null && !registerRequestEntity.getPosp_Address1().equals("")) {
            etAddress1.setText("" + registerRequestEntity.getPosp_Address1());
        }
        if (registerRequestEntity.getPosp_Address2() != null && !registerRequestEntity.getPosp_Address2().equals("")) {
            etAddress2.setText("" + registerRequestEntity.getPosp_Address2());
        }
        if (registerRequestEntity.getPosp_Address3() != null && !registerRequestEntity.getPosp_Address3().equals("")) {
            etAddress3.setText("" + registerRequestEntity.getPosp_Address3());
        }
        if (registerRequestEntity.getPosp_PinCode() != null && !registerRequestEntity.getPosp_PinCode().equals("")) {
            etPincode.setText("" + registerRequestEntity.getPosp_PinCode());
        }
        if (registerRequestEntity.getPosp_City() != null && !registerRequestEntity.getPosp_City().equals("")) {
            etCity.setText("" + registerRequestEntity.getPosp_City());
        }
        if (registerRequestEntity.getPosp_StatID() != null && !registerRequestEntity.getPosp_StatID().equals("")) {
            etState.setText("" + registerRequestEntity.getPosp_StatID());
        }

        // set bank details
        if (registerRequestEntity.getPosp_BankAcNo() != null && !registerRequestEntity.getPosp_BankAcNo().equals("")) {
            etBankAcNo.setText("" + registerRequestEntity.getPosp_BankAcNo());
        }
        if (registerRequestEntity.getPosp_Account_Type() != null) {
            if (registerRequestEntity.getPosp_Account_Type().equals("CURRENT")) {
                setSavingAcc();
            } else {
                setSavingAcc();
            }
        }

        if (registerRequestEntity.getPosp_IFSC() != null && !registerRequestEntity.getPosp_IFSC().equals("")) {
            etIfscCode.setText("" + registerRequestEntity.getPosp_IFSC());
        }
        if (registerRequestEntity.getPosp_MICR() != null && !registerRequestEntity.getPosp_MICR().equals("")) {
            erMicrCode.setText("" + registerRequestEntity.getPosp_MICR());
        }
        if (registerRequestEntity.getPosp_BankName() != null && !registerRequestEntity.getPosp_BankName().equals("")) {
            etBankName.setText("" + registerRequestEntity.getPosp_BankName());
        }
        if (registerRequestEntity.getPosp_BankBranch() != null && !registerRequestEntity.getPosp_BankBranch().equals("")) {
            etBankBranch.setText("" + registerRequestEntity.getPosp_BankBranch());
        }
        if (registerRequestEntity.getPOSPBankCity() != null && !registerRequestEntity.getPOSPBankCity().equals("")) {
            etBankCity.setText("" + registerRequestEntity.getPOSPBankCity());
        }
    }

    private void setTextWatcher() {
        etDob.setOnClickListener(datePickerDialog);
        //etPincode.addTextChangedListener(pincodeTextWatcher);
    }

    //region pincode textwatcher
    TextWatcher pincodeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (start == 5) {
                etCity.setText("");
                etState.setText("");
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 6) {
                showDialog("Fetching City...");
                new RegisterController(PospEnrollment.this).getCityState(etPincode.getText().toString(), PospEnrollment.this);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //endregion

    private void initLayouts() {
        llMyProfile.setVisibility(View.GONE);
        llAddress.setVisibility(View.GONE);
        llBankDetail.setVisibility(View.GONE);
        llDocumentUpload.setVisibility(View.GONE);
        hideAllLayouts(llMyProfile, ivMyProfile);
        setSavingAcc();
    }

    private void setListener() {
        txtSaving.setOnClickListener(this);
        txtCurrent.setOnClickListener(this);

        ivAddress.setOnClickListener(this);
        ivMyProfile.setOnClickListener(this);
        rlMyProfile.setOnClickListener(this);
        rlAddress.setOnClickListener(this);

        rlBankDetail.setOnClickListener(this);
        ivBankDetail.setOnClickListener(this);

        rlDocumentUpload.setOnClickListener(this);
        ivDocumentUpload.setOnClickListener(this);

        tvFemale.setOnClickListener(this);
        tvMale.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        etIfscCode.setOnFocusChangeListener(this);
        //etPan.setOnFocusChangeListener(this);
        etPincode.setOnFocusChangeListener(this);


        ivPhotoCam.setOnClickListener(this);
        ivPhotoView.setOnClickListener(this);
        ivPanCam.setOnClickListener(this);
        ivPanView.setOnClickListener(this);
        ivAadharCam.setOnClickListener(this);
        ivAadharView.setOnClickListener(this);
        ivAadharCamBack.setOnClickListener(this);
        ivAadharBackView.setOnClickListener(this);
        ivEduCam.setOnClickListener(this);
        ivEduView.setOnClickListener(this);
        ivCancelCam.setOnClickListener(this);
        ivCancelView.setOnClickListener(this);


    }

    private void initWidgets() {
        llMain = (LinearLayout) findViewById(R.id.llMain);
        //region address
        etAddress1 = (EditText) findViewById(R.id.etAddress1);
        etAddress2 = (EditText) findViewById(R.id.etAddress2);
        etAddress3 = (EditText) findViewById(R.id.etAddress3);
        etPincode = (EditText) findViewById(R.id.etPincode);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);

        //region bank details

        etBankAcNo = (EditText) findViewById(R.id.etBankAcNo);
        etAccountType = (EditText) findViewById(R.id.etAccountType);
        etIfscCode = (EditText) findViewById(R.id.etIfscCode);
        etIfscCode.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(15)});

        erMicrCode = (EditText) findViewById(R.id.erMicrCode);
        etBankBranch = (EditText) findViewById(R.id.etBankBranch);
        etBankCity = (EditText) findViewById(R.id.etBankCity);
        etBankName = (EditText) findViewById(R.id.etBankName);
        txtSaving = (TextView) findViewById(R.id.txtSaving);
        txtCurrent = (TextView) findViewById(R.id.txtCurrent);
        //region POSP INFORMATION
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etDob = (EditText) findViewById(R.id.etDob);
        etMobileNo1 = (EditText) findViewById(R.id.etMobileNo1);
        etMobileNo2 = (EditText) findViewById(R.id.etMobileNo2);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPan = (EditText) findViewById(R.id.etPan);
        etPan.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(10)});
        etAadhar = (EditText) findViewById(R.id.etAadhar);
        etGST = (EditText) findViewById(R.id.etGST);
        etChannelPartner = (EditText) findViewById(R.id.etChannelPartner);

        tvMale = (TextView) findViewById(R.id.tvMale);
        tvFemale = (TextView) findViewById(R.id.tvFemale);

        ivAddress = (ImageView) findViewById(R.id.ivAddress);
        ivMyProfile = (ImageView) findViewById(R.id.ivMyProfile);
        llMyProfile = (LinearLayout) findViewById(R.id.llMyProfile);
        llAddress = (LinearLayout) findViewById(R.id.llAddress);
        llDocumentUpload = (LinearLayout) findViewById(R.id.llDocumentUpload);
        llBankDetail = (LinearLayout) findViewById(R.id.llBankDetail);
        rlMyProfile = (RelativeLayout) findViewById(R.id.rlMyProfile);
        rlAddress = (RelativeLayout) findViewById(R.id.rlAddress);

        rlBankDetail = (RelativeLayout) findViewById(R.id.rlBankDetail);
        ivBankDetail = (ImageView) findViewById(R.id.ivBankDetail);
        rlDocumentUpload = (RelativeLayout) findViewById(R.id.rlDocumentUpload);
        ivDocumentUpload = (ImageView) findViewById(R.id.ivDocumentUpload);

        btnSave = (Button) findViewById(R.id.btnSave);
        webView = (WebView) findViewById(R.id.webView);


        ivPhotoCam = (ImageView) findViewById(R.id.ivPhotoCam);
        ivPhotoView = (ImageView) findViewById(R.id.ivPhotoView);
        ivPanCam = (ImageView) findViewById(R.id.ivPanCam);
        ivPanView = (ImageView) findViewById(R.id.ivPanView);

        ivCancelCam = (ImageView) findViewById(R.id.ivCancelCam);
        ivCancelView = (ImageView) findViewById(R.id.ivCancelView);
        ivAadharCam = (ImageView) findViewById(R.id.ivAadharCam);
        ivAadharView = (ImageView) findViewById(R.id.ivAadharView);
        ivAadharCamBack = (ImageView) findViewById(R.id.ivAadharCamBack);
        ivAadharBackView = (ImageView) findViewById(R.id.ivAadharBackView);
        ivEduCam = (ImageView) findViewById(R.id.ivEduCam);
        ivEduView = (ImageView) findViewById(R.id.ivEduView);


        ivAadhar = (ImageView) findViewById(R.id.ivAadhar);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        ivPan = (ImageView) findViewById(R.id.ivPan);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        ivEdu = (ImageView) findViewById(R.id.ivEdu);
        ivAadharBack = (ImageView) findViewById(R.id.ivAadharBack);

    }

    private void registerPosp() {
        ivAddress.performClick();
        ivBankDetail.performClick();
        ivDocumentUpload.performClick();

        if (isPospInfo && isAddress && isBankDetails) {
            registerRequestEntity.setFBAID(dbPersistanceController.getUserData().getFBAId());
            showDialog();
            new RegisterController(this).enrollPosp(registerRequestEntity, this);
        } else {
            Toast.makeText(this, "Please Fill all details.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMyProfile:
            case R.id.rlMyProfile:
                manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
                break;
            case R.id.ivAddress:
            case R.id.rlAddress:

                //region posp Info validation
                if (etFirstName.getText().toString().isEmpty()) {
                    if (llMyProfile.getVisibility() == View.GONE) {
                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etFirstName.requestFocus();
                        etFirstName.setError("Enter First Name");
                        etFirstName.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etFirstName.requestFocus();
                        etFirstName.setError("Enter First Name");
                        return;
                    }
                }

                if (etLastName.getText().toString().isEmpty()) {
                    if (llMyProfile.getVisibility() == View.GONE) {
                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etLastName.requestFocus();
                        etLastName.setError("Enter Last Name");
                        etLastName.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etLastName.requestFocus();
                        etLastName.setError("Enter Last Name");
                        return;
                    }
                }
                if (etDob.getText().toString().isEmpty()) {
                    if (llMyProfile.getVisibility() == View.GONE) {
                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etDob.requestFocus();
                        etDob.setError("Enter Dob");
                        etDob.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etDob.requestFocus();
                        etDob.setError("Enter Dob");
                        return;
                    }
                }
                if (!isValidePhoneNumber(etMobileNo1)) {
                    if (llMyProfile.getVisibility() == View.GONE) {
                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etMobileNo1.requestFocus();
                        etMobileNo1.setError("Enter Mobile1");
                        etMobileNo1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etMobileNo1.requestFocus();
                        etMobileNo1.setError("Enter Mobile1");
                        return;
                    }
                }

                /*if (etMobileNo2.getText().toString().isEmpty()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etMobileNo2.requestFocus();
                        etMobileNo2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etMobileNo2.requestFocus();
                        etMobileNo2.setError("Enter First Name");
                        return;
                    }
                }*/

                if (!isValideEmailID(etEmailId)) {
                    if (llMyProfile.getVisibility() == View.GONE) {
                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etEmailId.requestFocus();
                        etEmailId.setError("Enter Email");
                        etEmailId.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etEmailId.requestFocus();
                        etEmailId.setError("Enter Email");
                        return;
                    }
                }
                if (!isValidPan(etPan)) {
                    if (llMyProfile.getVisibility() == View.GONE) {
                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etPan.requestFocus();
                        etPan.setError("Invalid PAN No.");
                        etPan.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etPan.requestFocus();
                        etPan.setError("Invalid Pan No.");
                        return;
                    }
                }
//                    if (!isValidAadhar(etAadhar)) {
//                        if (llMyProfile.getVisibility() == View.GONE) {
//                            manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
//                            manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            etAadhar.requestFocus();
//                            etAadhar.setError("Enter Aadhar");
//                            etAadhar.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
//                            return;
//                        } else {
//                            etPan.requestFocus();
//                            etAadhar.setError("Enter Aadhar");
//                            return;
//                        }
//                    }

                //endregion

                setProfileDetails();

                isPospInfo = true;

                manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);


                break;
            case R.id.ivBankDetail:
            case R.id.rlBankDetail:

                if (!isPospInfo) {
                    ivAddress.performClick();
                    return;
                }

                //region address validation
                if (etAddress1.getText().toString().isEmpty()) {
                    if (llAddress.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etAddress1.requestFocus();
                        etAddress1.setError("Enter Address 1");
                        etAddress1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etAddress1.requestFocus();
                        etAddress1.setError("Enter Address 1");
                        return;
                    }
                }

                if (etAddress2.getText().toString().isEmpty()) {
                    if (llAddress.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etAddress2.requestFocus();
                        etAddress2.setError("Enter Address 2");
                        etAddress2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etAddress2.requestFocus();
                        etAddress2.setError("Enter Address 2");
                        return;
                    }
                }
                if (etPincode.getText().toString().isEmpty()) {
                    if (llAddress.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etPincode.requestFocus();
                        etPincode.setError("Enter Pincode");
                        etPincode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etPincode.requestFocus();
                        etPincode.setError("Enter Pincode");
                        return;
                    }

                }
                if (!etPincode.getText().toString().isEmpty()) {
                    if (etPincode.getText().toString().length() != 6) {
                        if (llAddress.getVisibility() == View.GONE) {
                            manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                            manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            etPincode.requestFocus();
                            etPincode.setError("Enter Pincode");
                            etPincode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            return;
                        } else {
                            etPincode.requestFocus();
                            etPincode.setError("Enter Pincode");
                            return;
                        }
                    }

                }
                if (etCity.getText().toString().isEmpty()) {
                    if (llAddress.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etCity.requestFocus();
                        etCity.setError("Enter City");
                        etCity.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etCity.requestFocus();
                        etCity.setError("Enter City");
                        return;
                    }
                }
                if (etState.getText().toString().isEmpty()) {
                    if (llAddress.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etState.requestFocus();
                        etState.setError("Enter State");
                        etState.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etState.requestFocus();
                        etState.setError("Enter State");
                        return;
                    }
                }
                //endregion

                setAddressDetails();

                isAddress = true;

                manageMainLayouts(llBankDetail, llMyProfile, llAddress, llDocumentUpload);
                manageImages(llBankDetail, ivBankDetail, ivAddress, ivMyProfile, ivDocumentUpload);

                break;
            case R.id.ivDocumentUpload:
            case R.id.rlDocumentUpload:

                if (!isPospInfo) {
                    ivAddress.performClick();
                    return;
                } else if (!isAddress) {
                    ivBankDetail.performClick();
                    return;
                }
                //region bank validation
                if (etBankAcNo.getText().toString().isEmpty()) {
                    if (llBankDetail.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etBankAcNo.requestFocus();
                        etBankAcNo.setError("Enter Bank Account No");
                        etBankAcNo.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etBankAcNo.requestFocus();
                        etBankAcNo.setError("Enter Bank Account No");
                        return;
                    }
                }
                if (etIfscCode.getText().toString().isEmpty()) {
                    if (llBankDetail.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etIfscCode.requestFocus();
                        etIfscCode.setError("Enter Bank IFSC");
                        etIfscCode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etIfscCode.requestFocus();
                        etIfscCode.setError("Enter Bank IFSC");
                        return;
                    }
                }
                if (erMicrCode.getText().toString().isEmpty()) {
                    if (llBankDetail.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        erMicrCode.requestFocus();
                        erMicrCode.setError("Enter Bank MICR");
                        erMicrCode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        erMicrCode.requestFocus();
                        erMicrCode.setError("Enter Bank MICR");
                        return;
                    }
                }

                if (etBankName.getText().toString().isEmpty()) {
                    if (llBankDetail.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etBankName.requestFocus();
                        etBankName.setError("Enter Bank Name");
                        etBankName.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etBankName.requestFocus();
                        etBankName.setError("Enter Bank Name");
                        return;
                    }
                }
                if (etBankBranch.getText().toString().isEmpty()) {
                    if (llBankDetail.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etBankBranch.requestFocus();
                        etBankBranch.setError("Enter Bank Branch");
                        etBankBranch.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etBankBranch.requestFocus();
                        etBankBranch.setError("Enter Bank Branck");
                        return;
                    }
                }
                if (etBankCity.getText().toString().isEmpty()) {
                    if (llBankDetail.getVisibility() == View.GONE) {
                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        etBankCity.requestFocus();
                        etBankCity.setError("Enter Bank City");
                        etBankCity.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        return;
                    } else {
                        etBankCity.requestFocus();
                        etBankCity.setError("Enter Bank City");
                        return;
                    }
                }

                //endregion

                setBankDetails();

                isBankDetails = true;

                manageMainLayouts(llDocumentUpload, llBankDetail, llMyProfile, llAddress);
                manageImages(llDocumentUpload, ivDocumentUpload, ivBankDetail, ivAddress, ivMyProfile);

                break;

            case R.id.tvMale:
                isMale = true;
                tvMale.setBackgroundResource(R.drawable.customeborder_blue);
                tvFemale.setBackgroundResource(R.drawable.customeborder);
                break;
            case R.id.tvFemale:
                isMale = false;
                tvMale.setBackgroundResource(R.drawable.customeborder);
                tvFemale.setBackgroundResource(R.drawable.customeborder_blue);
                break;
            case R.id.txtSaving:
                setSavingAcc();
                break;
            case R.id.txtCurrent:
                setCurrentAcc();
                break;
            case R.id.btnSave:

                if (validatePOSP_Info() == false) {
                    if (llMyProfile.getVisibility() == View.GONE) {

                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload);

                    }
                } else if (validateAddress_Info() == false) {
                    if (llMyProfile.getVisibility() == View.GONE) {

                        manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload);
                        manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload);

                    }
                } else if (validateBank_Info() == false) {
                    if (llBankDetail.getVisibility() == View.GONE) {

                        manageMainLayouts(llBankDetail, llAddress, llMyProfile, llDocumentUpload);
                        manageImages(llBankDetail, ivBankDetail, ivAddress, ivMyProfile, ivDocumentUpload);

                    }
                } else {
                   // new TrackingController(this).sendData(new TrackingRequestEntity(new TrackingData("enrolled posp : submit button for posp enrollment"), Constants.POSP), null);

                    IsAllImageUploaded = checkAllImageUpload();
                    if (IsAllImageUploaded) {
                        if (isPaymentDone) {
                            llMain.setVisibility(View.GONE);
                            openPopUp(btnSave, "SUCCESS", "POSP Already exist!!", "OK", false);
                        } else {
                            if (false) {
////////////////////////////////////////////////chk
//                                if (isPaymentLinkAvailable)
//                                {
                                openWebView("");
//                                } else {
//                                    bindInputFromeServer(pospDetailsEntity);
//                                    openPopUp(llDocumentUpload, "FAILURE", "Payment Link Not Available !!", "OK", true);
//                                }
                            } else {
                                registerPosp();
                            }
                        }

                    } else {
                        bindUploadImage();
                        if (isPaymentDone) {
                            openPopUp(llDocumentUpload, "MESSAGE", "Payment Alredy Done,Please Upload Remaining Documents !!", "OK", true);
                        } else {
                            if (false) {
///////////////////////////////
//                                if (isPaymentLinkAvailable) {
//                                    openWebView(loginResponseEntity.getPaymentUrl());
//                                } else {
//                                    bindInputFromeServer(pospDetailsEntity);
//                                    openPopUp(llDocumentUpload, "FAILURE", "Payment Link Not Available !!", "OK", true);
//                                }
                            } else {
                                registerPosp();
                            }
                        }

                    }
                }


                /*if (pospDetailsEntity.getPaymStat() == null || pospDetailsEntity.getPaymStat().isEmpty()) {
                    if (loginResponseEntity.getPaymentUrl() != null) {
                        openWebView(loginResponseEntity.getPaymentUrl());
                    } else {

                        ivAddress.performClick();

                        ivBankDetail.performClick();

                        ivDocumentUpload.performClick();
                        if (isPospInfo && isAddress && isBankDetails) {
                            registerRequestEntity.setFBAID(dbPersistanceController.getUserData().getFBAId());
                            showDialog();
                            new RegisterController(this).enrollPosp(registerRequestEntity, this);
                        } else {
                            Toast.makeText(this, "Please Fill all details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // payment done
                    if (checkAllImageUpload()) {
                        llMain.setVisibility(View.GONE);
                        openPopUp(btnSave, "SUCCESS", "POSP Already exist!!", "OK", true);
                        //showPopUpNew("SUCCESS ", "POSP Already exist!!", "OK", false);
                    } else {
                        openPopUp(llDocumentUpload, "SUCCESS", "Upload Remaining Document !", "OK", true);
                        //showPopUpNew("SUCCESS ", "Upload Remaining Document !", "OK", true);
                    }
                }*/
                break;


            case R.id.ivPhotoCam:
                type = 6;
                // launchCamera();
                galleryCamPopUp();
                break;


            case R.id.ivPanCam:
                type = 7;
                galleryCamPopUp();
                break;


            case R.id.ivAadharCam:
                type = 8;
                galleryCamPopUp();
                break;



            case R.id.ivAadharCamBack:
                type = 9;
                galleryCamPopUp();
                break;


            case R.id.ivCancelCam:
                type = 10;
                galleryCamPopUp();
                break;


            case R.id.ivEduCam:
                type = 11;
                galleryCamPopUp();
                break;


            // VIew
            case R.id.ivPhotoView:
                new createBitmapFromURL(ivPhotoView.getTag().toString(),"POSP PHOTOGRAPH").execute();
                break;

            case R.id.ivPanView:
                new createBitmapFromURL(ivPanView.getTag().toString(),"POSP PAN CARD").execute();
                break;

            case R.id.ivAadharView:
                new createBitmapFromURL(ivAadharView.getTag().toString(),"POSP AADHAR CARD FRONT").execute();
                break;

            case R.id.ivAadharBackView:
                new createBitmapFromURL(ivAadharBackView.getTag().toString(),"POSP AADHAR CARD BACK").execute();
                break;

            case R.id.ivCancelView:
                new createBitmapFromURL(ivCancelView.getTag().toString(),"POSP CANCELLED CHQ").execute();
                break;

            case R.id.ivEduView:
                new createBitmapFromURL(ivEduView.getTag().toString(),"POSP HIGHEST EDU. PROOF").execute();
                break;

        }
    }

    private void setAddressDetails() {
        registerRequestEntity.setPosp_Address1(etAddress1.getText().toString());
        registerRequestEntity.setPosp_Address2(etAddress2.getText().toString());
        if (!etAddress3.getText().toString().isEmpty())
            registerRequestEntity.setPosp_Address3(etAddress3.getText().toString());
        registerRequestEntity.setPosp_PinCode(etPincode.getText().toString());
        registerRequestEntity.setPosp_City(etCity.getText().toString());
        registerRequestEntity.setPosp_StatID(etState.getText().toString());// to be changed to id


        //setting nonposp
        registerRequestEntity.setAddress_1(etAddress1.getText().toString());
        registerRequestEntity.setAddress_2(etAddress2.getText().toString());
        if (!etAddress3.getText().toString().isEmpty())
            registerRequestEntity.setAddress_3(etAddress3.getText().toString());
        registerRequestEntity.setPinCode(etPincode.getText().toString());
        registerRequestEntity.setCity(etCity.getText().toString());
        registerRequestEntity.setPosp_StatID(etState.getText().toString());// to be changed to id

        prefManager.setPospInformation(registerRequestEntity);
    }

    private void setProfileDetails() {
        registerRequestEntity.setPosp_FirstName(etFirstName.getText().toString());
        registerRequestEntity.setPosp_LastName(etLastName.getText().toString());

        try {
            Date dob = simpleDateFormat.parse(registerRequestEntity.getPosp_DOB());
            String strDOB = displayFormat.format(dob);
            etDob.setText(strDOB);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        registerRequestEntity.setPosp_DOB(getYYYYMMDDPattern(etDob.getText().toString()));
        if (isMale)
            registerRequestEntity.setPosp_Gender("M");
        else
            registerRequestEntity.setPosp_Gender("F");
        registerRequestEntity.setPosp_Mobile1(etMobileNo1.getText().toString());
        registerRequestEntity.setPosp_Mobile2(etMobileNo2.getText().toString());
        registerRequestEntity.setPosp_Email(etEmailId.getText().toString().trim());
        registerRequestEntity.setPosp_PAN(etPan.getText().toString());
        registerRequestEntity.setPosp_Aadhaar(etAadhar.getText().toString());
        if (!etGST.getText().toString().isEmpty())
            registerRequestEntity.setPosp_ServiceTaxNo(etGST.getText().toString());
        if (!etChannelPartner.getText().toString().isEmpty())
            registerRequestEntity.setPosp_ChanPartCode(etChannelPartner.getText().toString());


        //non posp
        registerRequestEntity.setFirstName(etFirstName.getText().toString());
        registerRequestEntity.setLastName(etLastName.getText().toString());
        registerRequestEntity.setDOB(etDob.getText().toString());
        if (isMale)
            registerRequestEntity.setGender("M");
        else
            registerRequestEntity.setGender("F");
        registerRequestEntity.setMobile_1(etMobileNo1.getText().toString());
        registerRequestEntity.setMobile_2(etMobileNo2.getText().toString());
        registerRequestEntity.setEmailId(etEmailId.getText().toString());
        registerRequestEntity.setFBAPan(etPan.getText().toString());
        //registerRequestEntity.setOther_Aadhaar(etAadhar.getText().toString());
        if (!etGST.getText().toString().isEmpty())
            registerRequestEntity.setGSTNumb(etGST.getText().toString());
       /* if (!etChannelPartner.getText().toString().isEmpty())
            registerRequestEntity.setPosp_ChanPartCode(etChannelPartner.getText().toString());*/

        prefManager.setPospInformation(registerRequestEntity);
    }

    private void setBankDetails() {
        registerRequestEntity.setPosp_BankAcNo(etBankAcNo.getText().toString());
        registerRequestEntity.setPosp_Account_Type(ACCOUNT_TYPE);
        registerRequestEntity.setPosp_IFSC(etIfscCode.getText().toString());

        if (!erMicrCode.getText().toString().isEmpty())
            registerRequestEntity.setPosp_MICR(erMicrCode.getText().toString());

        registerRequestEntity.setPosp_BankName(etBankName.getText().toString());
        registerRequestEntity.setPosp_BankBranch(etBankBranch.getText().toString());
        registerRequestEntity.setPosp_BankCity(etBankCity.getText().toString());// to be changed to id


        prefManager.setPospInformation(registerRequestEntity);
    }

    private void setSavingAcc() {
        ACCOUNT_TYPE = "SAVING";
        txtSaving.setBackgroundResource(R.drawable.customeborder_blue);
        txtSaving.setTextColor(ContextCompat.getColor(PospEnrollment.this, R.color.colorPrimary));

        txtCurrent.setBackgroundResource(R.drawable.customeborder);
        txtCurrent.setTextColor(ContextCompat.getColor(PospEnrollment.this, R.color.description_text));


    }

    private void setCurrentAcc() {
        ACCOUNT_TYPE = "CURRENT";
        txtCurrent.setBackgroundResource(R.drawable.customeborder_blue);
        txtCurrent.setTextColor(ContextCompat.getColor(PospEnrollment.this, R.color.colorPrimary));

        txtSaving.setBackgroundResource(R.drawable.customeborder);
        txtSaving.setTextColor(ContextCompat.getColor(PospEnrollment.this, R.color.description_text));


    }

    private void manageMainLayouts(LinearLayout visibleLayout, LinearLayout hideLayout1, LinearLayout hideLayout2, LinearLayout hideLayout3) {

        if (visibleLayout.getVisibility() == View.GONE) {
            visibleLayout.setVisibility(View.VISIBLE);
            hideLayout1.setVisibility(View.GONE);
            hideLayout2.setVisibility(View.GONE);
            hideLayout3.setVisibility(View.GONE);
        } else {
            visibleLayout.setVisibility(View.GONE);
        }
    }

    private void manageImages(LinearLayout clickedLayout, ImageView downImage, ImageView upImage1, ImageView upImage2, ImageView upImage3) {

        if (clickedLayout.getVisibility() == View.GONE) {
            downImage.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage1.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage2.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage3.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
        } else {
            downImage.setImageDrawable(getResources().getDrawable(R.drawable.minus));
            upImage1.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage2.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage3.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
        }

    }

    private void hideAllLayouts(LinearLayout linearLayout, ImageView imageView) {

        if (linearLayout.getVisibility() == View.GONE) {

            //region hideall layout
            ivMyProfile.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            llMyProfile.setVisibility(View.GONE);

            ivAddress.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            llAddress.setVisibility(View.GONE);
            //endregion

            linearLayout.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.minus));

        } else {
            linearLayout.setVisibility(View.GONE);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        if (response instanceof PincodeResponse) {
            cancelDialog();
            if (response.getStatusNo() == 0) {
                Constants.hideKeyBoard(etPincode, this);
                etState.setText("" + ((PincodeResponse) response).getMasterData().getState_name());
                etCity.setText("" + ((PincodeResponse) response).getMasterData().getCityname());

                registerRequestEntity.setPosp_StatID("" + ((PincodeResponse) response).getMasterData().getStateid());
                registerRequestEntity.setPosp_City("" + ((PincodeResponse) response).getMasterData().getCityname());

                registerRequestEntity.setCity("" + ((PincodeResponse) response).getMasterData().getCityname());
                registerRequestEntity.setState("" + ((PincodeResponse) response).getMasterData().getState_name());
                registerRequestEntity.setStateID("" + ((PincodeResponse) response).getMasterData().getStateid());

            }
        } else if (response instanceof IfscCodeResponse) {
            cancelDialog();
            if (response.getStatusNo() == 0) {
                Constants.hideKeyBoard(etPincode, this);
                if (((IfscCodeResponse) response).getMasterData() != null) {
                    if (((IfscCodeResponse) response).getMasterData().size() > 0) {
                        ifscEntity = ((IfscCodeResponse) response).getMasterData().get(0);

                        etIfscCode.setText("" + ifscEntity.getIFSCCode());
                        if (ifscEntity.getMICRCode() != null)
                            erMicrCode.setText("" + ifscEntity.getMICRCode());
                        etBankName.setText("" + ifscEntity.getBankName());
                        etBankBranch.setText("" + ifscEntity.getBankBran());
                        etBankCity.setText("" + ifscEntity.getCityName());

                        if (!erMicrCode.getText().toString().isEmpty())
                            registerRequestEntity.setPosp_MICR(erMicrCode.getText().toString());

                        registerRequestEntity.setPosp_BankName(etBankName.getText().toString());
                        registerRequestEntity.setPosp_BankBranch(etBankBranch.getText().toString());
                        registerRequestEntity.setPosp_BankCity(etBankCity.getText().toString());// to be changed to id
                    }
                }
            }
        } else if (response instanceof PospDetailsResponse) {
            cancelDialog();
            Constants.hideKeyBoard(etPincode, this);
            if (response.getStatusNo() == 0) {
                if (((PospDetailsResponse) response).getMasterData() != null) {
                    if (((PospDetailsResponse) response).getMasterData().size() > 0) {
                        pospDetailsEntity = ((PospDetailsResponse) response).getMasterData().get(0);
                        if (pospDetailsEntity != null) {

                            IsAllImageUploaded = checkAllImageUpload();
                            if (pospDetailsEntity.getPaymStat() != null && !pospDetailsEntity.getPaymStat().equals("")) {
                                isPaymentDone = true;
                            }
                            if (pospDetailsEntity.getLink() != null && !pospDetailsEntity.getLink().equals("")) {
                                isPaymentLinkAvailable = true;
                                registerRequestEntity.setLink(pospDetailsEntity.getLink());
                            }
                            if (pospDetailsEntity.getPOSPNo() != null && !pospDetailsEntity.getPOSPNo().equals("")) {
                                isPospNoAvailable = true;
                                registerRequestEntity.setPOSPID(Integer.parseInt(pospDetailsEntity.getPOSPNo()));
                            }
                            checkPospStatus();
                         /*   if (checkAllImageUpload()) {
                                // all documents uploaded
                                if (pospDetailsEntity.getPaymStat() == null || pospDetailsEntity.getPaymStat().equals("")) {
                                    // payment   Not done & documents uploaded
                                    bindUploadImage();
                                    if (pospDetailsEntity.getPOSPNo() != null && !pospDetailsEntity.getPOSPNo().equals("")) {
                                        registerRequestEntity.setPOSPID(Integer.parseInt(pospDetailsEntity.getPOSPNo()));
                                        if (pospDetailsEntity.getLink() != null)
                                            registerRequestEntity.setLink(pospDetailsEntity.getLink());
                                        bindInputFromeServer(pospDetailsEntity);
                                    } else {
                                        setInputParameters();
                                    }

                                } else {
                                    // payment done & documents uploaded
                                    llMain.setVisibility(View.GONE);
                                    openPopUp(btnSave, "SUCCESS", "POSP Already exist!!", "OK", true);
                                    //showPopUpNew("SUCCESS ", "POSP Already exist!!", "OK", true);

                                }
                            } else {
                                // some documents uploaded
                                bindUploadImage();
                                if (pospDetailsEntity.getPOSPNo() != null && !pospDetailsEntity.getPOSPNo().equals("")) {
                                    registerRequestEntity.setPOSPID(Integer.parseInt(pospDetailsEntity.getPOSPNo()));
                                    bindInputFromeServer(pospDetailsEntity);
                                } else {
                                    setInputParameters();
                                }
                            }*/
                        }
                    }
                }
            }
        } else if (response instanceof EnrollPospResponse) {
            cancelDialog();
            if (response.getStatusNo() == 0) {
                if (((EnrollPospResponse) response).getMasterData() != null) {

                    if (((EnrollPospResponse) response).getMasterData().getPOSPNo() != null &&
                            !((EnrollPospResponse) response).getMasterData().getPOSPNo().equals(""))
                    {
                        isPospNoAvailable = true;

                        isPaymentLinkAvailable = true;
                        pospEnrollEntity = ((EnrollPospResponse) response).getMasterData();

                        //update login response
                        updateLoginResponse(pospEnrollEntity);
                        openWebView("");

                    }
                    else
                    {
                        Toast.makeText(this, "POSP Number Not Found,Please Login Again", Toast.LENGTH_LONG).show();
                    }


                }
            }
            Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (response instanceof DocumentResponse) {
            cancelDialog();
            if (response.getStatusNo() == 0) {
                if (type == 6) {
                    String temp = ((DocumentResponse) response).getMasterData().get(0).getPrv_file();
                    if (temp != null && !temp.equals(""))
                        updatePhotoUrl(temp);
                }
                // Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
                setDocumentUpload(((DocumentResponse) response).getMasterData().get(0).getPrv_file());

            }
        }

    }

    void checkPospStatus() {
        bindUploadImage();
        if (IsAllImageUploaded) {
            if (isPaymentDone) {
                llMain.setVisibility(View.GONE);
                openPopUp(btnSave, "SUCCESS", "POSP Already exist!!", "OK", false);
            } else {
                if (true) {
                    bindInputFromeServer(pospDetailsEntity);
                    //   if (isPaymentLinkAvailable)
                    {

                        openWebView("");



                    }
//                    else {
//
//                        openPopUp(llDocumentUpload, "FAILURE", "Payment Link Not Available !!", "OK", true);
//                    }
                } else {
                    setInputParameters();
                }
            }

        } else {
            if (isPaymentDone) {
                bindInputFromeServer(pospDetailsEntity);
                openPopUp(llDocumentUpload, "MESSAGE", "Upload remaining documents !!", "OK", true);
            } else {
                if (true) {
                    bindInputFromeServer(pospDetailsEntity);
                } else {
                    setInputParameters();
                }
            }

        }
    }

    public void updateLoginResponse(final PospEnrollEntity pospEnrollEntity) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                loginResponseEntity.setPaymentUrl("");
                loginResponseEntity.setPOSPName(registerRequestEntity.getPosp_FirstName() + " " + registerRequestEntity.getPosp_LastName());
                loginResponseEntity.setPOSPNo(pospEnrollEntity.getPOSPNo());
                loginResponseEntity.setPOSEmail(registerRequestEntity.getPosp_Email().trim());
                loginResponseEntity.setPOSPMobile(registerRequestEntity.getPosp_Mobile1());
            }
        });
    }

    public void updatePhotoUrl(final String pospPhotoUrl) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //loginResponseEntity.setPOSPProfileUrl("http://qa.mgfm.in/" + pospPhotoUrl);
                loginResponseEntity.setPOSPProfileUrl(pospPhotoUrl);
            }
        });
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
       // new TrackingController(this).sendData(new TrackingRequestEntity(new TrackingData("enrolled posp : " + t.getMessage()), Constants.POSP), null);
    }


    //region datepicker
    protected View.OnClickListener datePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Constants.hideKeyBoard(view, PospEnrollment.this);

            //region manufacture date
            if (view.getId() == R.id.etDob) {
                DateTimePicker.showHealthAgeDatePicker(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                                if (view1.isShown()) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, dayOfMonth);
                                    String currentDay = displayFormat.format(calendar.getTime());
                                    etDob.setText(currentDay);
                                }
                            }
                        });
            }
            //endregion

        }
    };
    //endregion

    private void settingWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(false);

        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);


        MyWebViewClient webViewClient = new MyWebViewClient(this);
        webView.setWebViewClient(webViewClient);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image
                showDialog();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO hide your progress image
                cancelDialog();
                if (count == 0) {
                    count++;
                    view.loadUrl("javascript: (function() {document.getElementById('ctl00_ContentPlaceHolder1_tbPAN').value= '" + etPan.getText().toString().toUpperCase() + "';}) ();");

                    view.loadUrl("javascript: (function() {document.getElementById('ctl00_ContentPlaceHolder1_btnView').click();}) ();");

                } else {
                    //view.LoadUrl("javascript: (function() {window.JsHtmlOut.processHTML(document.getElementById('ctl00_ContentPlaceHolder1_lblError').innerHTML);}) ();");
                    view.loadUrl("javascript:window.JsHtmlOut.ProcessHTML(document.getElementById('ctl00_ContentPlaceHolder1_lblError').innerHTML);");
                    count = 0;
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "JsHtmlOut");

        Log.d("URL", URL);
        webView.loadUrl(URL);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        switch (view.getId()) {
            case R.id.etIfscCode:
                if (!hasFocus) {
                    if (etIfscCode.getText().length() > 3) {
                        showDialog("Fetching Bank Details...");
                        new RegisterController(PospEnrollment.this).getIFSC(etIfscCode.getText().toString(), PospEnrollment.this);
                    }
                }
                break;
            case R.id.etPan:
                if (!hasFocus) {
                    if (isValidPan(etPan)) {
                        settingWebview();
                    }
                }

                break;

            case R.id.etPincode:
                if (!hasFocus) {
                    if (etPincode.getText().length() == 6) {
                        showDialog("Fetching City...");
                        new RegisterController(PospEnrollment.this).getCityState(etPincode.getText().toString(), PospEnrollment.this);
                    }
                }
                break;
        }
    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {
        switch (view.getId()) {
            case R.id.webView:
                dialog.cancel();
                PospEnrollment.this.runOnUiThread(new Runnable() {
                    public void run() {
                        etPan.setText("");
                    }
                });
                break;
            case R.id.llDocumentUpload:
                dialog.cancel();
                break;
            case R.id.btnSave:
                finish();
                break;

            case R.id.llBankDetail:
                dialog.cancel();
                openSetting();
                break;


        }
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        switch (view.getId()) {
            case R.id.webView:
                dialog.cancel();
                PospEnrollment.this.runOnUiThread(new Runnable() {
                    public void run() {
                        etPan.setText("");
                    }
                });
                break;
            case R.id.llDocumentUpload:
                dialog.cancel();
                break;
            case R.id.btnSave:
                finish();
                break;

            case R.id.llBankDetail:
                dialog.cancel();
                break;
        }
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void ProcessHTML(String html) {
            Log.d("HTML", html);
            if (html.toLowerCase().contains("not")) {

            } else {
                openPopUp(webView, "ERROR MESSAGE", "Pan No. Already Registered in IRDA", "OK", false);
                //showPopUp("ERROR MESSAGE", "Pan No. Already Registered in IRDA", "OK", false);
                //Toast.makeText(PospEnrollment.this, "Pan Card Already Registered in IRDA", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPopUp(String title, String message, String buttonName, boolean isCancelable) {

        try {
            final Dialog dialog;
            dialog = new Dialog(PospEnrollment.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_pancard_popup);

            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
            TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
            tvOk.setText(buttonName);
            TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
            txtMessage.setText(message);


            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelable);

            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.MATCH_PARENT;
            ; // Width
            lp.height = lp.WRAP_CONTENT; // Height
            dialogWindow.setAttributes(lp);

            dialog.show();
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.cancel();
                    PospEnrollment.this.runOnUiThread(new Runnable() {
                        public void run() {
                            etPan.setText("");
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPopUpNew(String title, String message, String buttonName, boolean isCancelable) {

        try {
            final Dialog dialog;
            dialog = new Dialog(PospEnrollment.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_pancard_popup);

            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
            TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
            tvOk.setText(buttonName);
            TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
            txtMessage.setText(message);


            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelable);

            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.MATCH_PARENT;
            ; // Width
            lp.height = lp.WRAP_CONTENT; // Height
            dialogWindow.setAttributes(lp);

            dialog.show();
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openWebView(String url) {
        startActivity(new Intent(this, RazorPaymentActivity.class));
        PospEnrollment.this.finish();



        //     if (!url.equals("")) {

        //            startActivity(new Intent(this, CommonWebViewActivity.class)
//                    .putExtra("URL", url)
//                    .putExtra("NAME", "MAGIC FIN-MART")
//                    .putExtra("TITLE", "MAGIC FIN-MART"));

//Umesh 14
        //  PospEnrollment.this.finish();
        //   Utility.loadWebViewUrlInBrowser(PospEnrollment.this,loginResponseEntity.getPaymentUrl());
//        }
    }


    private void launchCamera() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);

        String FileName = "";

        switch (type) {
            case 6:
                FileName = PHOTO_File;
                break;
            case 7:
                FileName = PAN_File;
                break;
            case 8:
                FileName = AADHAR_FRONT_File;
                break;
            case 9:
                FileName = AADHAR_BACK_File;
                break;
            case 10:
                FileName = CANCEL_CHQ_File;
                break;
            case 11:
                FileName = EDU_FILE;
                break;

        }
        //  Docfile = createFile(FileName);
        Docfile = createImageFile(FileName);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            imageUri = Uri.fromFile(Docfile);
        } else {
            imageUri = FileProvider.getUriForFile(PospEnrollment.this,
                    getString(R.string.file_provider_authority), Docfile);

        }


        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    private void openGallery() {


        // region old code
        //String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        //     startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        //endregion



        String  mimeType = "image/*";

        Uri collection ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection =  MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
            );
        } else {
            collection =  MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }


        try {
            Intent intent = new  Intent(Intent.ACTION_PICK, collection);

            intent.setType(mimeType);
            intent.resolveActivity(getPackageManager());
            startActivityForResult(intent, SELECT_PICTURE);

        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * //TODO: Crop image activity to crop capture image.
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //region handle result of CropImageActivity

        //  /****** Below For Cropping The Camera Image**************************/ //

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //extractTextFromImage();
            startCropImageActivity(imageUri);
        }
        // Below For Cropping The Gallery Image
        else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            startCropImageActivity(selectedImageUri);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                try {
                    cropImageUri = result.getUri();
                    Bitmap mphoto = null;
                    try {
                        //mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cropImageUri);
                        mphoto = getBitmapFromContentResolver(cropImageUri);
                        mphoto = getResizedBitmap(mphoto, 800);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    showDialog();

                    switch (type) {

                        case 6:
                            showDialog();
                            file = saveImageToStorage(mphoto, "" + POSP_PHOTO);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_PHOTO, PHOTO_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                        case 7:

                            showDialog();
                            file = saveImageToStorage(mphoto, "" + POSP_PAN);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_PAN, PAN_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;

                        case 8:
                            showDialog();
                            file = saveImageToStorage(mphoto, "" + POSP_AADHAR_FRONT);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_AADHAR_FRONT, AADHAR_FRONT_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                        case 9:
                            showDialog();
                            file = saveImageToStorage(mphoto, "" + POSP_AADHAR_BACK);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_AADHAR_BACK, AADHAR_BACK_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                        case 10:
                            showDialog();
                            file = saveImageToStorage(mphoto, "" + POSP_CANCEL_CHQ);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_CANCEL_CHQ, CANCEL_CHQ_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                        case 11:
                            showDialog();
                            file = saveImageToStorage(mphoto, "" + POSP_EDU);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_EDU, EDU_FILE);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                    }



                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

        //endregion


        // region  commented Below   Code for Image and Camara Handling

//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap mphoto = null;
//            // Bitmap mphoto = (Bitmap) data.getExtras().get("data");
//            try {
//                //mphoto = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                mphoto = getBitmapFromContentResolver(imageUri);
//                mphoto = getResizedBitmap(mphoto, 800);
//                mphoto = rotateImageIfRequired(this, mphoto, Docfile);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            switch (type) {
//
//                case 6:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_PHOTO);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_PHOTO, PHOTO_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 7:
//
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_PAN);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_PAN, PAN_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//
//                case 8:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_AADHAR_FRONT);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_AADHAR_FRONT, AADHAR_FRONT_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 9:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_AADHAR_BACK);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_AADHAR_BACK, AADHAR_BACK_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 10:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_CANCEL_CHQ);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_CANCEL_CHQ, CANCEL_CHQ_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 11:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_EDU);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_EDU, EDU_FILE);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//            }
//
//
//        }
//
//        else if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK )
//        {
//            Uri selectedImageUri = data.getData();
//            Bitmap mphoto = null;
//            try {
//                // mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
//                mphoto = getBitmapFromContentResolver(selectedImageUri);
//                mphoto = getResizedBitmap(mphoto, 800);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            switch (type) {
//                case 6:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_PHOTO);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_PHOTO, PHOTO_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 7:
//
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_PAN);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_PAN, PAN_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//
//                case 8:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_AADHAR_FRONT);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_AADHAR_FRONT, AADHAR_FRONT_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 9:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_AADHAR_BACK);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_AADHAR_BACK, AADHAR_BACK_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 10:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_CANCEL_CHQ);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_CANCEL_CHQ, CANCEL_CHQ_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 11:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, "" + POSP_EDU);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginResponseEntity.getFBAId(), POSP_EDU, EDU_FILE);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//            }
//
//
//        }

        //endregion
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

    private void setDocumentUpload(String FileName) {
        if (type == 6) {
            ivPhoto.setImageResource(R.drawable.doc_uploaded);

            ivPhotoView.setTag(FileName);
            ivPhotoView.setVisibility(View.VISIBLE);
        } else if (type == 7) {
            ivPan.setImageResource(R.drawable.doc_uploaded);

            ivPanView.setTag(FileName);
            ivPanView.setVisibility(View.VISIBLE);
        } else if (type == 8) {
            ivAadhar.setImageResource(R.drawable.doc_uploaded);

            ivAadharView.setTag(FileName);
            ivAadharView.setVisibility(View.VISIBLE);

        } else if (type == 9) {
            ivAadharBack.setImageResource(R.drawable.doc_uploaded);

            ivAadharBackView.setTag(FileName);
            ivAadharBackView.setVisibility(View.VISIBLE);

        } else if (type == 10) {
            ivCancel.setImageResource(R.drawable.doc_uploaded);

            ivCancelView.setTag(FileName);
            ivCancelView.setVisibility(View.VISIBLE);

        } else if (type == 11) {
            ivEdu.setImageResource(R.drawable.doc_uploaded);

            ivEduView.setTag(FileName);
            ivEduView.setVisibility(View.VISIBLE);
        }
    }

    private void setUploadedDocument(int type, String FileName) {
        switch (type) {
            case 6:
                ivPhoto.setImageResource(R.drawable.doc_uploaded);

                ivPhotoView.setTag(FileName);
                ivPhotoView.setVisibility(View.VISIBLE);
                break;
            case 7:
                ivPan.setImageResource(R.drawable.doc_uploaded);

                ivPanView.setTag(FileName);
                ivPanView.setVisibility(View.VISIBLE);

                break;
            case 8:
                ivAadhar.setImageResource(R.drawable.doc_uploaded);

                ivAadharView.setTag(FileName);
                ivAadharView.setVisibility(View.VISIBLE);
                break;
            case 9:
                ivAadharBack.setImageResource(R.drawable.doc_uploaded);

                ivAadharBackView.setTag(FileName);
                ivAadharBackView.setVisibility(View.VISIBLE);
                break;
            case 10:
                ivCancel.setImageResource(R.drawable.doc_uploaded);

                ivCancelView.setTag(FileName);
                ivCancelView.setVisibility(View.VISIBLE);
                break;
            case 11:
                ivEdu.setImageResource(R.drawable.doc_uploaded);

                ivEduView.setTag(FileName);
                ivEduView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

    }

    private Bitmap rotateImageIfRequired(Context context, Bitmap bitmap, File file) {
        Bitmap rotatedBitmap = null;
        try {

            // region Handling Default Rotation Of Image
            Uri uri = Uri.fromFile(file);
            inputStream = context.getContentResolver().openInputStream(uri);

            if (Build.VERSION.SDK_INT > 23) {
                ei = new ExifInterface(inputStream);
            } else {

                // ei = new ExifInterface("/storage/emulated/0/FINMART/FBAPhotograph.jpg");
                ei = new ExifInterface(file.getAbsolutePath());
            }


            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;

            }
            //endregion

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rotatedBitmap;
    }

    private boolean validatePOSP_Info() {
        //region posp Info validation
        if (etFirstName.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etFirstName.requestFocus();
                etFirstName.setError("Enter First Name");
                etFirstName.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etFirstName.requestFocus();
                etFirstName.setError("Enter First Name");
                return false;
            }
        }

        if (etLastName.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etLastName.requestFocus();
                etLastName.setError("Enter Last Name");
                etLastName.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etLastName.requestFocus();
                etLastName.setError("Enter Last Name");
                return false;
            }
        }
        if (etDob.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etDob.requestFocus();
                etDob.setError("Enter Dob");
                etDob.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etDob.requestFocus();
                etDob.setError("Enter Dob");
                return false;
            }
        }
        if (!isValidePhoneNumber(etMobileNo1)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etMobileNo1.requestFocus();
                etMobileNo1.setError("Enter Mobile1");
                etMobileNo1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etMobileNo1.requestFocus();
                etMobileNo1.setError("Enter Mobile1");
                return false;
            }
        }


        if (!isValideEmailID(etEmailId)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etEmailId.requestFocus();
                etEmailId.setError("Enter Email");
                etEmailId.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etEmailId.requestFocus();
                etEmailId.setError("Enter Email");
                return false;
            }
        }
        if (!isValidPan(etPan)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etPan.requestFocus();
                etPan.setError("Invalid PAN No.");
                etPan.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etPan.requestFocus();
                etPan.setError("Invalid Pan No.");
                return false;
            }
        }

        return true;

    }

    private boolean validateAddress_Info() {
        //region address validation
        if (etAddress1.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etAddress1.requestFocus();
                etAddress1.setError("Enter Address 1");
                etAddress1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etAddress1.requestFocus();
                etAddress1.setError("Enter Address 1");
                return false;
            }
        }

        if (etAddress2.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etAddress2.requestFocus();
                etAddress2.setError("Enter Address 2");
                etAddress2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etAddress2.requestFocus();
                etAddress2.setError("Enter Address 2");
                return false;
            }
        }
        if (etPincode.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etPincode.requestFocus();
                etPincode.setError("Enter Pincode");
                etPincode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etPincode.requestFocus();
                etPincode.setError("Enter Pincode");
                return false;
            }

        }
        if (!etPincode.getText().toString().isEmpty()) {
            if (etPincode.getText().toString().length() != 6) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    etPincode.requestFocus();
                    etPincode.setError("Enter Pincode");
                    etPincode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    return false;
                } else {
                    etPincode.requestFocus();
                    etPincode.setError("Enter Pincode");
                    return false;
                }
            }

        }
        if (etCity.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etCity.requestFocus();
                etCity.setError("Enter City");
                etCity.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etCity.requestFocus();
                etCity.setError("Enter City");
                return false;
            }
        }
        if (etState.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etState.requestFocus();
                etState.setError("Enter State");
                etState.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etState.requestFocus();
                etState.setError("Enter State");
                return false;
            }
        }

        return true;
        //endregion

    }

    private boolean validateBank_Info() {
        //region bank validation
        if (etBankAcNo.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etBankAcNo.requestFocus();
                etBankAcNo.setError("Enter Bank Account No");
                etBankAcNo.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etBankAcNo.requestFocus();
                etBankAcNo.setError("Enter Bank Account No");
                return false;
            }
        }
        if (etIfscCode.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etIfscCode.requestFocus();
                etIfscCode.setError("Enter Bank IFSC");
                etIfscCode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etIfscCode.requestFocus();
                etIfscCode.setError("Enter Bank IFSC");
                return false;
            }
        }
        if (erMicrCode.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                erMicrCode.requestFocus();
                erMicrCode.setError("Enter Bank MICR");
                erMicrCode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                erMicrCode.requestFocus();
                erMicrCode.setError("Enter Bank MICR");
                return false;
            }
        }

        if (etBankName.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etBankName.requestFocus();
                etBankName.setError("Enter Bank Name");
                etBankName.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etBankName.requestFocus();
                etBankName.setError("Enter Bank Name");
                return false;
            }
        }
        if (etBankBranch.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etBankBranch.requestFocus();
                etBankBranch.setError("Enter Bank Branch");
                etBankBranch.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etBankBranch.requestFocus();
                etBankBranch.setError("Enter Bank Branck");
                return false;
            }
        }
        if (etBankCity.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etBankCity.requestFocus();
                etBankCity.setError("Enter Bank City");
                etBankCity.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etBankCity.requestFocus();
                etBankCity.setError("Enter Bank City");
                return false;
            }


        }
        return true;
        //endregion
    }

    // region permission

//    private boolean checkPermission() {
//
//        int camera = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[0]);
//
//        int WRITE_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[1]);
//        int READ_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[2]);
//
//        return camera == PackageManager.PERMISSION_GRANTED
//                && WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
//                && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private boolean checkRationalePermission() {
//
//        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(PospEnrollment.this, perms[0]);
//
//        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(PospEnrollment.this, perms[1]);
//        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(PospEnrollment.this, perms[2]);
//
//        return camera || write_external || read_external;
//    }
//
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, perms, Constants.PERMISSION_CAMERA_STORACGE_CONSTANT);
//    }


    // region Permission

    private void checkRationale(){
        if (checkRationalePermission()) {
            //Show Information about why you need the permission

            Snackbar.make(ivMyProfile, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    requestPermission();
                }
            }).show();

        } else {
            openPopUp(llBankDetail, "Need  Permission", "This app needs all permissions.", "GRANT", true);

        }
    }

    private boolean checkPermission() {

        int camera = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[0]);

        int WRITE_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int READ_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[2]);
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return camera == PackageManager.PERMISSION_GRANTED

                    && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;
        }else{
            return camera == PackageManager.PERMISSION_GRANTED
                    &&  WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                    && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;

        }

    }

    private boolean checkRationalePermission() {

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(PospEnrollment.this, perms[0]);

        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(PospEnrollment.this, perms[1]);
        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(PospEnrollment.this, perms[2]);
        // boolean minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;


        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return  camera ||  read_external;
        }else{
            return  camera ||write_external   || read_external;

        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, perms, Constants.PERMISSION_CAMERA_STORACGE_CONSTANT);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case Constants.PERMISSION_CAMERA_STORACGE_CONSTANT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternal = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternal = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;

                    if (camera && (writeExternal || minSdk29 ) && readExternal) {

                        showCamerGalleryPopUp();

                    }


                }
                break;




        }
    }


    //endregion


    private void galleryCamPopUp() {

        if (!checkPermission()) {

            if (checkRationalePermission()) {
                //Show Information about why you need the permission
                requestPermission();

            } else {

                openPopUp(llBankDetail, "Need  Permission", "This app needs all permissions.", "GRANT", true);
            }

        } else {

            showCamerGalleryPopUp();
        }
    }


    private void showCamerGalleryPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        LinearLayout lyCamera, lyGallery;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_cam_gallery, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        lyCamera = (LinearLayout) dialogView.findViewById(R.id.lyCamera);
        lyGallery = (LinearLayout) dialogView.findViewById(R.id.lyGallery);

        lyCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
                alertDialog.dismiss();

            }
        });

        lyGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }

    private void openSetting() {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        supportFinishAfterTransition();
        super.onBackPressed();
    }

}
