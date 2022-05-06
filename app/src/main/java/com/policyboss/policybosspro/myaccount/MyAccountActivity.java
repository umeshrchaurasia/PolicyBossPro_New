package com.policyboss.policybosspro.myaccount;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.IncomeCalculator.IncomePotentialActivity;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.helpfeedback.HelpFeedBackActivity;
import com.policyboss.policybosspro.home.HomeActivity;
import com.policyboss.policybosspro.homeMainKotlin.BottomSheetDialogMenuFragment;
import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity;
import com.policyboss.policybosspro.notification.NotificationActivity;
import com.policyboss.policybosspro.notification.NotificationSmsActivity;
import com.policyboss.policybosspro.splashscreen.SplashScreenActivity;
import com.policyboss.policybosspro.switchuser.SwitchUserActivity;
import com.policyboss.policybosspro.utility.CircleTransform;
import com.policyboss.policybosspro.utility.Constants;
import com.policyboss.policybosspro.webviews.CommonWebViewActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.ak1.BubbleTabBar;
import io.ak1.OnBubbleClickListener;
import io.realm.Realm;
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.MasterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.AccountDtlEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DocAvailableEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.IfscEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.NotifyEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.RegisterRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.DocumentResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.IfscCodeResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MyAccountResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MyAcctDtlResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PincodeResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserConstatntResponse;
import okhttp3.MultipartBody;


/**
 * Created by daniyalshaikh on 10/01/18.
 */

public class MyAccountActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener, IResponseSubcriber, BaseActivity.PopUpListener , BottomSheetDialogMenuFragment.IBottomMenuCallback {

    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1800;
    PrefManager prefManager;
    int type;

    BubbleTabBar bubbleTabBar;
    ShimmerFrameLayout shimmerMyAcccount;

    LinearLayout  llMyProfile, llAddress, llBankDetail, llDocumentUpload, llPosp, llAbout, llNotify;
    ImageView ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload, ivPOSP, ivProfile, ivAbout,
            ivPhotoCam, ivPhotoView, ivPanCam, ivPanView, ivCancelCam, ivCancelView, ivAadharCam, ivAadharView,
            ivAadhar, ivCancel, ivPan, ivPhoto, ivUser, ivNotify;
    RelativeLayout rlParent, rlMyProfile, rlAddress, rlBankDetail, rlDocumentUpload, rlPOSP, rlAbout, rlNotify;

    EditText etSubHeading, etMobileNo, etEmailId, etAddress1, etAddress2, etAddress3, etPincode,
            etCity, etState, etAccountHolderName, etAadhaar, etPAN, etBankAcNo, etIfscCode,
            etMicrCode, etBankName, etBankBranch, etBankCity, etSubHeading_posp, etMobileNo_posp, etEmailId_posp;
    TextView txtSaving, txtCurrent, tvName, txtManagerName, tvFbaCode, tvPospNo, tvLoginId, tvPospStatus, txtManagerMobile, txtManagerEmail, txtSupportMobile, txtSupportEmail;

    AppCompatImageView ivManagerMobile, ivManagerEmail, ivSupportMobile, ivSupportEmail;
    ScrollView mainScrollView;
    Switch swNotify;

    Button btnSave;
    RegisterRequestEntity registerRequestEntity;
    DBPersistanceController dbPersistanceController;
    LoginResponseEntity loginEntity;
    AccountDtlEntity accountDtlEntity;
    public String ACCOUNT_TYPE = "SAVING";

    HashMap<String, String> body;
    MultipartBody.Part part;
    File Docfile;
    File file;
    Uri imageUri;
    private Uri cropImageUri;
    InputStream inputStream;
    ExifInterface ei;
    // private String PROFILE = "1", PHOTO = "2", PAN = "3", CANCEL_CHQ = "4", AADHAR = "5";

    private int PROFILE = 1, PHOTO = 2, PAN = 3, CANCEL_CHQ = 4, AADHAR = 5;

    private String PHOTO_File = "FBAPhotograph", PAN_File = "LoanRepPanCard", CANCEL_CHQ_File = "LoanRepCancelChq", AADHAR_File = "OtherAadharCard";

    private String PHOTO_EXT = "FBAPhotograph.jpg", PAN_EXT = "LoanRepPanCard.jpg", CANCEL_CHQ_EXT = "LoanRepCancelChq.jpg", AADHAR_EXT = "OtherAadharCard.jpg";

    Boolean isDataUploaded = true;
    Bitmap bitmapPhoto = null;
    LoginResponseEntity loginResponseEntity;
    String[] permissionsRequired = new String[]{Manifest.permission.CALL_PHONE};

    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        dbPersistanceController = new DBPersistanceController(this);
        loginResponseEntity = dbPersistanceController.getUserData();
        loginEntity = dbPersistanceController.getUserData();

        registerPopUp(this);
        registerRequestEntity = new RegisterRequestEntity();
        registerRequestEntity.setFBAID(loginEntity.getFBAId());
        prefManager = new PrefManager(this);
        initWidgets();
        setListener();
        initLayouts();
        setfileView();



        shimmerMyAcccount.setVisibility(View.VISIBLE);
        rlParent.setVisibility(View.GONE);
        shimmerMyAcccount.startShimmerAnimation();


        if (dbPersistanceController.getUserConstantsData() != null) {
            bindAboutMe();
        } else {
            new MasterController(this).geUserConstant(1, this);
        }

        // region Handling Bottom bar selection

        if (getIntent().getExtras() != null) {

            // For getting User Click Action
            if (getIntent().getExtras().getString(Constants.BOTTOM_TYPE) != null) {

                String type = getIntent().getExtras().getString(Constants.BOTTOM_TYPE);

                switch(type) {

                    case "nav_home" :


                        bubbleTabBar.setSelectedWithId(R.id.nav_home, true);

                        break;

                    case "nav_menu":
                        bubbleTabBar.setSelectedWithId(R.id.nav_menu, true);


                        break;



                    case "nav_notification" :

                        bubbleTabBar.setSelectedWithId(R.id.nav_notification, true);


                        break;


                    case "nav_profile" :

                        bubbleTabBar.setSelectedWithId(R.id.nav_profile, true);


                        break;
                }

            }

        }

        //endregion

       // showDialog("Fetching Detail...");



        new RegisterController(MyAccountActivity.this).getMyAcctDtl(String.valueOf(loginEntity.getFBAId()), MyAccountActivity.this);





    }


    private void initLayouts() {
        llMyProfile.setVisibility(View.GONE);
        llAddress.setVisibility(View.GONE);
        llBankDetail.setVisibility(View.GONE);
        llDocumentUpload.setVisibility(View.GONE);
        hideAllLayouts(llMyProfile, ivMyProfile);
    }

    private void setListener() {
        ivAddress.setOnClickListener(this);
        ivMyProfile.setOnClickListener(this);
        rlMyProfile.setOnClickListener(this);
        rlAddress.setOnClickListener(this);

        rlBankDetail.setOnClickListener(this);
        ivBankDetail.setOnClickListener(this);

        rlDocumentUpload.setOnClickListener(this);
        ivDocumentUpload.setOnClickListener(this);

        rlPOSP.setOnClickListener(this);
        ivPOSP.setOnClickListener(this);

        rlAbout.setOnClickListener(this);
        ivAbout.setOnClickListener(this);

        rlNotify.setOnClickListener(this);
        ivNotify.setOnClickListener(this);

        ivProfile.setOnClickListener(this);
        ivPhotoCam.setOnClickListener(this);
        ivPhotoView.setOnClickListener(this);
        ivPanCam.setOnClickListener(this);
        ivPanView.setOnClickListener(this);

        ivCancelCam.setOnClickListener(this);
        ivCancelView.setOnClickListener(this);
        ivAadharCam.setOnClickListener(this);
        ivAadharView.setOnClickListener(this);

        ivManagerMobile.setOnClickListener(this);
        ivManagerEmail.setOnClickListener(this);
        ivSupportMobile.setOnClickListener(this);
        ivSupportEmail.setOnClickListener(this);


        btnSave.setOnClickListener(this);
        txtSaving.setOnClickListener(this);
        txtCurrent.setOnClickListener(this);


        etPincode.addTextChangedListener(pincodeTextWatcher);
        etIfscCode.setOnFocusChangeListener(this);

        etAadhaar.setOnFocusChangeListener(acAdhaarFocusChange);

        swNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    prefManager.updateNotificationsetting("0");
                    //  Toast.makeText(getApplicationContext(), "Switch is on", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(getApplicationContext(), "Switch is off", Toast.LENGTH_LONG).show();
                    prefManager.updateNotificationsetting("1");
                }
            }
        });


       // bubbleTabBar.setSelectedWithId(R.id.nav_profile,true);
      //  bubbleTabBar.set



        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int id) {



               switch (id){
                   case R.id.nav_home:


                       Intent intent = new Intent(MyAccountActivity.this, HomeMainActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       intent.putExtra(Constants.BOTTOM_TYPE,"nav_home");
                       startActivity(intent);
                       finish();
                       overridePendingTransition(0,0);

                       break;

                   case R.id.nav_menu:


                       BottomSheetDialogMenuFragment bottomSheetDialogMenuFragment =new  BottomSheetDialogMenuFragment();
                       bottomSheetDialogMenuFragment.show(getSupportFragmentManager(), bottomSheetDialogMenuFragment.getTag());

                       break;


                   case R.id.nav_notification:

                       Intent intent3 = new Intent(MyAccountActivity.this, NotificationActivity.class);
                       intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       intent3.putExtra(Constants.BOTTOM_TYPE,"nav_notification");
                       startActivity(intent3);
                       finish();
                       overridePendingTransition(0,0);

                       break;

               }
            }
        });

    }

    private void initWidgets() {
        bubbleTabBar =  (BubbleTabBar) findViewById(R.id.bubbleTabBar);
        shimmerMyAcccount = (ShimmerFrameLayout) findViewById(R.id.shimmerMyAcccount);

        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);
        ivAddress = (ImageView) findViewById(R.id.ivAddress);
        ivMyProfile = (ImageView) findViewById(R.id.ivMyProfile);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        llMyProfile = (LinearLayout) findViewById(R.id.llMyProfile);
        llAddress = (LinearLayout) findViewById(R.id.llAddress);
        llDocumentUpload = (LinearLayout) findViewById(R.id.llDocumentUpload);
        llBankDetail = (LinearLayout) findViewById(R.id.llBankDetail);
        llPosp = (LinearLayout) findViewById(R.id.llPosp);
        llAbout = (LinearLayout) findViewById(R.id.llAbout);
        llNotify = (LinearLayout) findViewById(R.id.llNotify);

        rlParent = (RelativeLayout) findViewById(R.id.rlParent);
        rlMyProfile = (RelativeLayout) findViewById(R.id.rlMyProfile);
        rlAddress = (RelativeLayout) findViewById(R.id.rlAddress);
        rlBankDetail = (RelativeLayout) findViewById(R.id.rlBankDetail);
        ivBankDetail = (ImageView) findViewById(R.id.ivBankDetail);
        rlDocumentUpload = (RelativeLayout) findViewById(R.id.rlDocumentUpload);
        rlPOSP = (RelativeLayout) findViewById(R.id.rlPOSP);
        rlAbout = (RelativeLayout) findViewById(R.id.rlAbout);
        rlNotify = (RelativeLayout) findViewById(R.id.rlNotify);

        ivDocumentUpload = (ImageView) findViewById(R.id.ivDocumentUpload);
        ivPOSP = (ImageView) findViewById(R.id.ivPOSP);
        ivAbout = (ImageView) findViewById(R.id.ivAbout);


        etSubHeading = (EditText) findViewById(R.id.etSubHeading);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etAddress1 = (EditText) findViewById(R.id.etAddress1);
        etAddress2 = (EditText) findViewById(R.id.etAddress2);

        etAddress3 = (EditText) findViewById(R.id.etAddress3);
        etPincode = (EditText) findViewById(R.id.etPincode);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        etAccountHolderName = (EditText) findViewById(R.id.etAccountHolderName);

        etAadhaar = (EditText) findViewById(R.id.etAadhaar);
        etPAN = (EditText) findViewById(R.id.etPAN);
        etBankAcNo = (EditText) findViewById(R.id.etBankAcNo);
        etIfscCode = (EditText) findViewById(R.id.etIfscCode);

        etMicrCode = (EditText) findViewById(R.id.etMicrCode);
        etBankName = (EditText) findViewById(R.id.etBankName);
        etBankBranch = (EditText) findViewById(R.id.etBankBranch);
        etBankCity = (EditText) findViewById(R.id.etBankCity);

        etSubHeading_posp = (EditText) findViewById(R.id.etSubHeading_posp);
        etMobileNo_posp = (EditText) findViewById(R.id.etMobileNo_posp);
        etEmailId_posp = (EditText) findViewById(R.id.etEmailId_posp);


        txtSaving = (TextView) findViewById(R.id.txtSaving);
        txtCurrent = (TextView) findViewById(R.id.txtCurrent);

        ivUser = (ImageView) findViewById(R.id.ivUser);
        ivPhotoCam = (ImageView) findViewById(R.id.ivPhotoCam);
        ivPhotoView = (ImageView) findViewById(R.id.ivPhotoView);
        ivPanCam = (ImageView) findViewById(R.id.ivPanCam);
        ivPanView = (ImageView) findViewById(R.id.ivPanView);

        ivCancelCam = (ImageView) findViewById(R.id.ivCancelCam);
        ivCancelView = (ImageView) findViewById(R.id.ivCancelView);
        ivAadharCam = (ImageView) findViewById(R.id.ivAadharCam);
        ivAadharView = (ImageView) findViewById(R.id.ivAadharView);

        ivAadhar = (ImageView) findViewById(R.id.ivAadhar);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        ivPan = (ImageView) findViewById(R.id.ivPan);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);


        // region About Me
        ivManagerMobile = (AppCompatImageView) findViewById(R.id.ivManagerMobile);
        ivManagerEmail = (AppCompatImageView) findViewById(R.id.ivManagerEmail);
        ivSupportMobile = (AppCompatImageView) findViewById(R.id.ivSupportMobile);
        ivSupportEmail = (AppCompatImageView) findViewById(R.id.ivSupportEmail);

        tvName = (TextView) findViewById(R.id.tvName);
        txtManagerName = (TextView) findViewById(R.id.txtManagerName);
        tvFbaCode = (TextView) findViewById(R.id.tvFbaCode);
        tvPospNo = (TextView) findViewById(R.id.tvPospNo);
        tvLoginId = (TextView) findViewById(R.id.tvLoginId);

        tvPospStatus = (TextView) findViewById(R.id.tvPospStatus);
        txtManagerMobile = (TextView) findViewById(R.id.txtManagerMobile);
        txtManagerEmail = (TextView) findViewById(R.id.txtManagerEmail);
        txtSupportMobile = (TextView) findViewById(R.id.txtSupportMobile);
        txtSupportEmail = (TextView) findViewById(R.id.txtSupportEmail);
        //endregion

        // regionNotify Setting
        ivNotify = (ImageView) findViewById(R.id.ivNotify);
        swNotify = (Switch) findViewById(R.id.swNotify);
        // region About Me

        btnSave = (Button) findViewById(R.id.btnSave);


    }



    private void bindAboutMe() {
        UserConstantEntity userConstantEntity = dbPersistanceController.getUserConstantsData();

        tvName.setText(loginResponseEntity.getFullName());
        tvFbaCode.setText("" + userConstantEntity.getFBAId());
        if (userConstantEntity.getPOSPNo() != null) {
            tvPospNo.setText("" + userConstantEntity.getPOSPNo());
        }
        tvLoginId.setText("" + userConstantEntity.getLoginID());
        tvPospStatus.setText(userConstantEntity.getPOSP_STATUS());

        txtManagerName.setText(userConstantEntity.getManagName());
        txtManagerMobile.setText(userConstantEntity.getMangMobile());
        txtManagerEmail.setText(userConstantEntity.getMangEmail());

        txtSupportMobile.setText(userConstantEntity.getSuppMobile());
        txtSupportEmail.setText(userConstantEntity.getSuppEmail());
    }

    private void setfileView(){

        ivAadharView.setVisibility(View.GONE);
        ivPanView.setVisibility(View.GONE);
        ivPhotoView.setVisibility(View.GONE);
        ivCancelView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view, this);

        mainScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 600);
        switch (view.getId()) {

            case R.id.ivMyProfile:
            case R.id.rlMyProfile:
                manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload, llPosp, llAbout, llNotify);
                manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload, ivPOSP, ivAbout, ivNotify);
                break;
            case R.id.ivAddress:
            case R.id.rlAddress:
                manageMainLayouts(llAddress, llMyProfile, llBankDetail, llDocumentUpload, llPosp, llAbout, llNotify);
                manageImages(llAddress, ivAddress, ivMyProfile, ivBankDetail, ivDocumentUpload, ivPOSP, ivAbout, ivNotify);
                saveProfile();
                break;
            case R.id.ivBankDetail:
            case R.id.rlBankDetail:
                manageMainLayouts(llBankDetail, llMyProfile, llAddress, llDocumentUpload, llPosp, llAbout, llNotify);
                manageImages(llBankDetail, ivBankDetail, ivAddress, ivMyProfile, ivDocumentUpload, ivPOSP, ivAbout, ivNotify);
                saveAddress();
                break;
            case R.id.ivDocumentUpload:
            case R.id.rlDocumentUpload:
                manageMainLayouts(llDocumentUpload, llBankDetail, llMyProfile, llAddress, llPosp, llAbout, llNotify);
                manageImages(llDocumentUpload, ivDocumentUpload, ivBankDetail, ivAddress, ivMyProfile, ivPOSP, ivAbout, ivNotify);
                saveBankDtl();
                break;

            case R.id.ivPOSP:
            case R.id.rlPOSP:
                manageMainLayouts(llPosp, llDocumentUpload, llBankDetail, llMyProfile, llAddress, llAbout, llNotify);
                manageImages(llPosp, ivPOSP, ivDocumentUpload, ivBankDetail, ivAddress, ivMyProfile, ivAbout, ivNotify);
                savePOSP();
                break;

            case R.id.ivAbout:
            case R.id.rlAbout:
                manageMainLayouts(llAbout, llPosp, llDocumentUpload, llBankDetail, llMyProfile, llAddress, llNotify);
                manageImages(llAbout, ivAbout, ivBankDetail, ivAddress, ivMyProfile, ivDocumentUpload, ivPOSP, ivNotify);
                break;

            case R.id.ivNotify:
            case R.id.rlNotify:
                manageMainLayouts(llNotify, llAbout, llPosp, llDocumentUpload, llBankDetail, llMyProfile, llAddress);
                manageImages(llNotify, ivNotify, ivAbout, ivBankDetail, ivAddress, ivMyProfile, ivDocumentUpload, ivPOSP);


            case R.id.txtSaving:
                setSavingAcc();
                break;
            case R.id.txtCurrent:
                setCurrentAcc();
                break;

            case R.id.ivProfile:
                type = 1;
                galleryCamPopUp();
                break;

            case R.id.ivPhotoCam:
                type = 2;
                // launchCamera();
                galleryCamPopUp();
                break;


            case R.id.ivPanCam:
                type = 3;
                // launchCamera();
                galleryCamPopUp();
                break;


            case R.id.ivCancelCam:
                type = 4;
                //launchCamera();
                galleryCamPopUp();
                break;


            case R.id.ivAadharCam:
                type = 5;
//                launchCamera();
                galleryCamPopUp();
                break;

            case R.id.ivPhotoView:

                new createBitmapFromURL(ivPhotoView.getTag().toString(),"FBA PHOTOGRAPH").execute();

                break;
            case R.id.ivPanView:
                new createBitmapFromURL(ivPanView.getTag().toString(), "FBA PAN CARD").execute();

                break;
            case R.id.ivCancelView:
                new createBitmapFromURL(ivCancelView.getTag().toString(), "CANCELLED CHQ").execute();

                break;

            case R.id.ivAadharView:

                new createBitmapFromURL(ivAadharView.getTag().toString(),"FBA AADHAR CARD").execute();

                break;


            case R.id.ivManagerMobile:

                if (dbPersistanceController.getUserConstantsData().getManagName() != null) {


//                    if (ActivityCompat.checkSelfPermission(MyAccountActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {
//
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(MyAccountActivity.this, permissionsRequired[0])) {
//                            //Show Information about why you need the permission
//                            ActivityCompat.requestPermissions(MyAccountActivity.this, permissionsRequired, Constants.PERMISSION_CALLBACK_CONSTANT);
//
//                        } else {
//
//                            openPopUp(ivManagerMobile, "Need  Permission", "This app needs all permissions.", "GRANT", true);
//
//
//                        }
//                    } else {

                    ConfirmAlert("Calling", getResources().getString(R.string.RM_Calling) + " " + dbPersistanceController.getUserConstantsData().getManagName(), dbPersistanceController.getUserConstantsData().getMangMobile());
                    //}

                }

                break;

            case R.id.ivManagerEmail:
                if (dbPersistanceController.getUserConstantsData().getMangEmail() != null) {
                    composeEmail(dbPersistanceController.getUserConstantsData().getMangEmail(), "");
                }
                break;

            case R.id.ivSupportMobile:
                if (dbPersistanceController.getUserConstantsData().getManagName() != null) {

//                    if (ActivityCompat.checkSelfPermission(MyAccountActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {
//
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(MyAccountActivity.this, permissionsRequired[0])) {
//                            //Show Information about why you need the permission
//                            ActivityCompat.requestPermissions(MyAccountActivity.this, permissionsRequired, Constants.PERMISSION_CALLBACK_SUPPORT);
//
//                        } else {
//
//                            openPopUp(ivManagerMobile, "Need  Permission", "This app needs all permissions.", "GRANT", true);
//
//                        }
//                    } else {
                    ConfirmAlert("Calling", getResources().getString(R.string.Support_Calling), dbPersistanceController.getUserConstantsData().getSuppMobile());

//                    }

                }

                break;

            case R.id.ivSupportEmail:
                if (dbPersistanceController.getUserConstantsData().getSuppEmail() != null) {
                    composeEmail(dbPersistanceController.getUserConstantsData().getSuppEmail(), "");
                }

                break;

            case R.id.btnSave:

                if (validateProfile() == false) {
                    // llMyProfile.setVisibility(View.GONE);
                    if (llMyProfile.getVisibility() == View.GONE) {
                        manageMainLayouts(llMyProfile, llAddress, llBankDetail, llDocumentUpload, llPosp, llAbout, llNotify);
                        manageImages(llMyProfile, ivMyProfile, ivAddress, ivBankDetail, ivDocumentUpload, ivPOSP, ivAbout, ivNotify);
                    }

                } else {
                    showDialog();
                    saveMain();
                }


                break;
        }


    }

    View.OnFocusChangeListener acAdhaarFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) {
                if (!isValidAadhar(etAadhaar)) {

                    etAadhaar.setError("Invalid Aadhaar Number");
                    etAadhaar.setFocusable(true);
                }
            }
        }
    };

    //region textwatcher
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
            if ((s.length() == 6) && (isDataUploaded)) {
                showDialog("Fetching City...");
                Toast.makeText(MyAccountActivity.this, "Fetching City...Data", Toast.LENGTH_LONG).show();
                new RegisterController(MyAccountActivity.this).getCityState(etPincode.getText().toString(), MyAccountActivity.this);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    //endregion


    private void saveProfile() {

        if ((etSubHeading.getText().toString().trim().length() > 0) || (etMobileNo.getText().toString().trim().length() > 0) ||
                (etEmailId.getText().toString().trim().length() > 0)) {

            registerRequestEntity.setFBA_Designation("" + etSubHeading.getText().toString());
            registerRequestEntity.setMobile_1("" + etMobileNo.getText().toString());
            registerRequestEntity.setEmailId("" + etEmailId.getText().toString().trim());
            registerRequestEntity.setType("1");

            new RegisterController(MyAccountActivity.this).saveAccDtl(registerRequestEntity, MyAccountActivity.this);
        }
    }


    private void savePOSP() {

        if ((etSubHeading_posp.getText().toString().trim().length() > 0) || (etMobileNo_posp.getText().toString().trim().length() > 0) ||
                (etEmailId_posp.getText().toString().trim().length() > 0)) {

            registerRequestEntity.setDisplayDesignation("" + etSubHeading_posp.getText().toString().toUpperCase());
            registerRequestEntity.setDisplayPhoneNo("" + etMobileNo_posp.getText().toString());
            registerRequestEntity.setDisplayEmail("" + etEmailId_posp.getText().toString().trim());
            registerRequestEntity.setType("4");

            new RegisterController(MyAccountActivity.this).saveAccDtl(registerRequestEntity, MyAccountActivity.this);
        }
    }


    private void saveAddress() {
        if ((etAddress1.getText().toString().trim().length() > 0) || (etAddress2.getText().toString().trim().length() > 0) ||
                (etAddress3.getText().toString().trim().length() > 0) || (etPAN.getText().toString().trim().length() > 0)) {
            registerRequestEntity.setAddress_1("" + etAddress1.getText().toString());
            registerRequestEntity.setAddress_2("" + etAddress2.getText().toString());
            registerRequestEntity.setAddress_3("" + etAddress3.getText().toString());
            registerRequestEntity.setPinCode("" + etPincode.getText().toString());
            registerRequestEntity.setCity("" + etCity.getText().toString());
            registerRequestEntity.setState("" + etState.getText().toString());
            registerRequestEntity.setType("2");

            new RegisterController(MyAccountActivity.this).saveAccDtl(registerRequestEntity, MyAccountActivity.this);
        }
    }

    private void saveBankDtl() {
        if ((etAddress1.getText().toString().trim().length() > 0) || (etAddress2.getText().toString().trim().length() > 0) ||
                (etAddress3.getText().toString().trim().length() > 0) || (etPAN.getText().toString().trim().length() > 0)) {
            registerRequestEntity.setLoan_FirstName("" + etAccountHolderName.getText().toString());
            registerRequestEntity.setLoan_PAN("" + etPAN.getText().toString());
            registerRequestEntity.setLoan_Aadhaar("" + etAadhaar.getText().toString());
            registerRequestEntity.setLoan_BankAcNo("" + etBankAcNo.getText().toString());
            registerRequestEntity.setLoan_IFSC("" + etIfscCode.getText().toString());
            registerRequestEntity.setLoan_MICR("" + etMicrCode.getText().toString());
            registerRequestEntity.setLoan_BankName("" + etBankName.getText().toString());
            registerRequestEntity.setLoan_BankBranch("" + etBankBranch.getText().toString());
            registerRequestEntity.setLoan_BankCity("" + etBankCity.getText().toString());
            if (ACCOUNT_TYPE.equals("SAVING")) {
                registerRequestEntity.setLoan_Account_Type("SAVING");
            } else {
                registerRequestEntity.setLoan_Account_Type("CURRENT");
            }

            registerRequestEntity.setType("3");

            new RegisterController(MyAccountActivity.this).saveAccDtl(registerRequestEntity, MyAccountActivity.this);
        }
    }

    private void saveMain() {
        registerRequestEntity.setType("0");
        registerRequestEntity.setFBA_Designation("" + etSubHeading.getText().toString());
        registerRequestEntity.setMobile_1("" + etMobileNo.getText().toString());
        registerRequestEntity.setEmailId("" + etEmailId.getText().toString().trim());

        registerRequestEntity.setDisplayDesignation("" + etSubHeading_posp.getText().toString().toUpperCase());
        registerRequestEntity.setDisplayPhoneNo("" + etMobileNo_posp.getText().toString());
        registerRequestEntity.setDisplayEmail("" + etEmailId_posp.getText().toString().trim());

        registerRequestEntity.setAddress_1("" + etAddress1.getText().toString());
        registerRequestEntity.setAddress_2("" + etAddress2.getText().toString());
        registerRequestEntity.setAddress_3("" + etAddress3.getText().toString());
        registerRequestEntity.setPinCode("" + etPincode.getText().toString());
        registerRequestEntity.setCity("" + etCity.getText().toString());
        registerRequestEntity.setState("" + etState.getText().toString());


        registerRequestEntity.setLoan_FirstName("" + etAccountHolderName.getText().toString());
        registerRequestEntity.setLoan_PAN("" + etPAN.getText().toString());
        registerRequestEntity.setLoan_Aadhaar("" + etAadhaar.getText().toString());
        registerRequestEntity.setLoan_BankAcNo("" + etBankAcNo.getText().toString());
        registerRequestEntity.setLoan_IFSC("" + etIfscCode.getText().toString());
        registerRequestEntity.setLoan_MICR("" + etMicrCode.getText().toString());
        registerRequestEntity.setLoan_BankName("" + etBankName.getText().toString());
        registerRequestEntity.setLoan_BankBranch("" + etBankBranch.getText().toString());
        registerRequestEntity.setLoan_BankCity("" + etBankCity.getText().toString());
        if (ACCOUNT_TYPE.equals("SAVING")) {
            registerRequestEntity.setLoan_Account_Type("SAVING");
        } else {
            registerRequestEntity.setLoan_Account_Type("CURRENT");
        }
        new RegisterController(MyAccountActivity.this).saveAccDtl(registerRequestEntity, MyAccountActivity.this);

    }


    private void setSavingAcc() {
        ACCOUNT_TYPE = "SAVING";
        txtSaving.setBackgroundResource(R.drawable.customeborder_blue);
        txtSaving.setTextColor(ContextCompat.getColor(MyAccountActivity.this, R.color.colorPrimary));

        txtCurrent.setBackgroundResource(R.drawable.customeborder);
        txtCurrent.setTextColor(ContextCompat.getColor(MyAccountActivity.this, R.color.description_text));


    }

    private void setCurrentAcc() {
        ACCOUNT_TYPE = "CURRENT";
        txtCurrent.setBackgroundResource(R.drawable.customeborder_blue);
        txtCurrent.setTextColor(ContextCompat.getColor(MyAccountActivity.this, R.color.colorPrimary));

        txtSaving.setBackgroundResource(R.drawable.customeborder);
        txtSaving.setTextColor(ContextCompat.getColor(MyAccountActivity.this, R.color.description_text));


    }


    private void manageMainLayouts(LinearLayout visibleLayout, LinearLayout hideLayout1, LinearLayout hideLayout2, LinearLayout hideLayout3, LinearLayout hideLayout4, LinearLayout hideLayout5, LinearLayout hideLayout6) {


        if (visibleLayout.getVisibility() == View.GONE) {

            visibleLayout.setVisibility(View.VISIBLE);

//            visibleLayout.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    visibleLayout.setVisibility(View.VISIBLE);
//
//                }
//            }, 800);


            //     visibleLayout.animate().translationY(visibleLayout.getHeight()).alpha(1.0f).setDuration(1000);

            hideLayout1.setVisibility(View.GONE);
            hideLayout2.setVisibility(View.GONE);
            hideLayout3.setVisibility(View.GONE);
            hideLayout4.setVisibility(View.GONE);
            hideLayout5.setVisibility(View.GONE);
            hideLayout6.setVisibility(View.GONE);

        } else {
            visibleLayout.setVisibility(View.GONE);
            //  visibleLayout.animate().translationY(visibleLayout.getHeight()).alpha(0.0f).setDuration(1000);

//            visibleLayout.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    visibleLayout.setVisibility(View.GONE);
//                }
//            }, 800);
        }
    }

    private void manageImages(LinearLayout clickedLayout, ImageView downImage, ImageView upImage1, ImageView upImage2, ImageView upImage3, ImageView upImage4, ImageView upImage5, ImageView upImage6) {

        if (clickedLayout.getVisibility() == View.GONE) {

            downImage.setImageDrawable(getResources().getDrawable(R.drawable.pluse));


            upImage1.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage2.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage3.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage4.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage5.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage6.setImageDrawable(getResources().getDrawable(R.drawable.pluse));


        } else {

            downImage.setImageDrawable(getResources().getDrawable(R.drawable.minus));

            upImage1.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage2.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage3.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage4.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage5.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            upImage5.setImageDrawable(getResources().getDrawable(R.drawable.pluse));


        }


    }

    private void hideAllLayouts(LinearLayout linearLayout, ImageView imageView) {

        if (linearLayout.getVisibility() == View.GONE) {

            //region hideall layout
            ivMyProfile.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            llMyProfile.setVisibility(View.GONE);

            ivAddress.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            llAddress.setVisibility(View.GONE);

            ivPOSP.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            llPosp.setVisibility(View.GONE);

            ivAbout.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
            llAbout.setVisibility(View.GONE);
            //endregion

            linearLayout.setVisibility(View.GONE);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pluse));

        } else {
            linearLayout.setVisibility(View.GONE);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.pluse));
        }
    }

    private boolean validateProfile() {

        if (!isEmpty(etSubHeading)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etSubHeading.requestFocus();
                etSubHeading.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etSubHeading.setError("Enter Designation");
                return false;
            } else {
                etSubHeading.requestFocus();
                etSubHeading.setError("Enter Designation");
                return false;
            }
        } else if (!isEmpty(etMobileNo)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                etMobileNo.requestFocus();
                etMobileNo.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etMobileNo.setError("Enter Mobile Number");
                return false;

            } else {
                etMobileNo.requestFocus();
                etMobileNo.setError("Enter Mobile Number");
                return false;

            }
        } else if (etMobileNo.getText().length() < 9) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                etMobileNo.requestFocus();
                etMobileNo.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etMobileNo.setError("Enter Valid Mobile Number");
                return false;

            } else {
                etMobileNo.requestFocus();
                etMobileNo.setError("Enter Valid Mobile Number");
                return false;
            }
        } else if (!isEmpty(etEmailId)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etEmailId.requestFocus();
                etEmailId.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etEmailId.setError("Enter Email ID");
                return false;
            } else {
                etEmailId.requestFocus();
                etEmailId.setError("Enter Email ID");
                return false;
            }
        } else if (!isValideEmailID(etEmailId)) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                etEmailId.requestFocus();
                etEmailId.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etEmailId.setError("Enter Valid Email ID");
                return false;

            } else {
                etEmailId.requestFocus();
                etEmailId.setError("Enter Valid Email ID");
                return false;

            }
        }
        return true;
    }

    private boolean validatePosp() {

        if (!isEmpty(etSubHeading_posp)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etSubHeading_posp.requestFocus();
                etSubHeading_posp.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etSubHeading_posp.setError("Enter Designation");
                return false;
            } else {
                etSubHeading_posp.requestFocus();
                etSubHeading_posp.setError("Enter Designation");
                return false;
            }
        } else if (!isEmpty(etMobileNo_posp)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                etMobileNo_posp.requestFocus();
                etMobileNo_posp.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etMobileNo_posp.setError("Enter Mobile Number");
                return false;

            } else {
                etMobileNo_posp.requestFocus();
                etMobileNo_posp.setError("Enter Mobile Number");
                return false;

            }
        } else if (etMobileNo_posp.getText().length() < 9) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                etMobileNo_posp.requestFocus();
                etMobileNo_posp.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etMobileNo_posp.setError("Enter Valid Mobile Number");
                return false;

            } else {
                etMobileNo_posp.requestFocus();
                etMobileNo_posp.setError("Enter Valid Mobile Number");
                return false;
            }
        } else if (!isEmpty(etEmailId)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etEmailId_posp.requestFocus();
                etEmailId_posp.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etEmailId_posp.setError("Enter Email ID");
                return false;
            } else {
                etEmailId_posp.requestFocus();
                etEmailId_posp.setError("Enter Email ID");
                return false;
            }
        } else if (!isValideEmailID(etEmailId_posp)) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                etEmailId_posp.requestFocus();
                etEmailId_posp.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                etEmailId_posp.setError("Enter Email ID");
                return false;

            } else {
                etEmailId_posp.requestFocus();
                etEmailId_posp.setError("Enter Email ID");
                return false;

            }
        }
        return true;
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();

        shimmerMyAcccount.setVisibility(View.GONE);
        rlParent.setVisibility(View.VISIBLE);
        shimmerMyAcccount.stopShimmerAnimation();


        if (response instanceof PincodeResponse) {
            if (response.getStatusNo() == 0) {
                etState.setText("" + ((PincodeResponse) response).getMasterData().getState_name());
                etCity.setText("" + ((PincodeResponse) response).getMasterData().getCityname());

                registerRequestEntity.setCity("" + ((PincodeResponse) response).getMasterData().getCityname());
                registerRequestEntity.setState("" + ((PincodeResponse) response).getMasterData().getState_name());
                registerRequestEntity.setStateID("" + ((PincodeResponse) response).getMasterData().getStateid());

            } else {

                etState.setText("");
                etCity.setText("");

                registerRequestEntity.setCity("");
                registerRequestEntity.setState("");
                registerRequestEntity.setStateID("0");

            }
        } else if (response instanceof IfscCodeResponse) {
            if (response.getStatusNo() == 0) {
                Constants.hideKeyBoard(etPincode, this);

                IfscEntity ifscEntity = ((IfscCodeResponse) response).getMasterData().get(0);

                etIfscCode.setText("" + ifscEntity.getIFSCCode());
                etMicrCode.setText("" + ifscEntity.getMICRCode());
                etBankName.setText("" + ifscEntity.getBankName());
                etBankBranch.setText("" + ifscEntity.getBankName());
                etBankCity.setText("" + ifscEntity.getCityName());

                etMicrCode.setSelection(etMicrCode.getText().length());


                registerRequestEntity.setLoan_BankName("" + ifscEntity.getBankName());
                registerRequestEntity.setLoan_BankBranch("" + ifscEntity.getBankBran());
                registerRequestEntity.setLoan_IFSC("" + ifscEntity.getIFSCCode());

            }
        } else if (response instanceof MyAccountResponse) {

            if (registerRequestEntity.getType().equals("0")) {
                Snackbar.make(ivMyProfile, response.getMessage(), Snackbar.LENGTH_SHORT).show();
                saveAcctDtlToDB(0);
            } else if ((registerRequestEntity.getType().equals("1") || registerRequestEntity.getType().equals("4"))) {
                saveAcctDtlToDB(Integer.valueOf(registerRequestEntity.getType()));
            }
        }

        //DocumentResponse
        else if (response instanceof DocumentResponse) {
            if (response.getStatusNo() == 0) {

                // Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
                setDocumentUpload(((DocumentResponse) response).getMasterData().get(0).getPrv_file());
                if (type == 1 || type == 2) {
                    try {
                        updateLoginResponse(((DocumentResponse) response).getMasterData().get(0).getPrv_file());

                        dbPersistanceController.updateUserConstatntProfile(((DocumentResponse) response).getMasterData().get(0).getPrv_file());

                        // Todo : Firing Local BroadCase
                        Intent profileIntent = new Intent(Utility. USER_PROFILE_ACTION);
                        profileIntent.putExtra("PROFILE_PATH", ((DocumentResponse) response).getMasterData().get(0).getPrv_file());

                        LocalBroadcastManager.getInstance(MyAccountActivity.this).sendBroadcast(profileIntent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        } else if (response instanceof MyAcctDtlResponse) {
            if (response.getStatusNo() == 0) {


                accountDtlEntity = ((MyAcctDtlResponse) response).getMasterData().get(0);

                if (accountDtlEntity != null) {
                    isDataUploaded = false;
                    setAcctDtlInfo(accountDtlEntity);
                    dbPersistanceController.updateMyAccountData(accountDtlEntity);
                    isDataUploaded = true;
                }


            }
        } else if (response instanceof UserConstatntResponse) {
            if (response.getStatusNo() == 0) {
                if (((UserConstatntResponse) response).getMasterData() != null) {
                    dbPersistanceController.updateUserConstatntData(((UserConstatntResponse) response).getMasterData());
                    bindAboutMe();
                }
            }
        }

    }

    public void updateLoginResponse(final String fbaProfileUrl) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //loginResponseEntity.setFBAProfileUrl("http://qa.mgfm.in/" + fbaProfileUrl);
                loginResponseEntity.setFBAProfileUrl(fbaProfileUrl);
            }
        });
    }

    private void setAcctDtlInfo(AccountDtlEntity accountDtlEntity) {

        etSubHeading.setText("" + accountDtlEntity.getDesignation());
        etMobileNo.setText("" + accountDtlEntity.getEditMobiNumb());
        etEmailId.setText("" + accountDtlEntity.getEditEmailId().trim());


        etAddress1.setText("" + accountDtlEntity.getAddress_1());
        etAddress2.setText("" + accountDtlEntity.getAddress_2());
        etAddress3.setText("" + accountDtlEntity.getAddress_3());
        etPincode.setText("" + accountDtlEntity.getPinCode());
        etCity.setText("" + accountDtlEntity.getCity());
        etState.setText("" + accountDtlEntity.getStateName());

        etAccountHolderName.setText("" + accountDtlEntity.getLoanName());
        etPAN.setText("" + accountDtlEntity.getLoan_PAN());
        etAadhaar.setText("" + accountDtlEntity.getLoan_Aadhaar());
        etBankAcNo.setText("" + accountDtlEntity.getLoan_BankAcNo());
        etIfscCode.setText("" + accountDtlEntity.getLoan_IFSC());
        etMicrCode.setText("" + accountDtlEntity.getLoan_MICR());
        etBankName.setText("" + accountDtlEntity.getLoan_BankName());
        etBankBranch.setText("" + accountDtlEntity.getLoan_BankBranch());
        etBankCity.setText("" + accountDtlEntity.getLoan_BankCity());
        if (accountDtlEntity.getLoan_Account_Type().equals("SAVING")) {
            setSavingAcc();
        } else if (accountDtlEntity.getLoan_Account_Type().equals("CURRENT")) {
            setCurrentAcc();
        }

        etSubHeading_posp.setText("" + accountDtlEntity.getDisplayDesignation().toString().toUpperCase());
        etMobileNo_posp.setText("" + accountDtlEntity.getDisplayPhoneNo());
        etEmailId_posp.setText("" + accountDtlEntity.getDisplayEmail().trim());

        if (accountDtlEntity.getDoc_available().size() > 0) {
            List<DocAvailableEntity> docList = accountDtlEntity.getDoc_available();


            for (DocAvailableEntity docEntity : docList) {
                if (!docEntity.getFileName().isEmpty()) {

                    setDocumentUpload(docEntity.getDocType(), docEntity.getFileName());
                }
            }

        }


    }

    private void saveAcctDtlToDB(int type) {
        if (accountDtlEntity != null) {
            if (type == 1) {
                accountDtlEntity.setDesignation("" + etSubHeading.getText().toString().toString());
                accountDtlEntity.setEditMobiNumb("" + etMobileNo.getText().toString());
                accountDtlEntity.setEditEmailId("" + etEmailId.getText().toString().trim());
            } else if (type == 4) {
                accountDtlEntity.setDisplayDesignation("" + etSubHeading_posp.getText().toString().toUpperCase());
                accountDtlEntity.setDisplayPhoneNo("" + etMobileNo_posp.getText().toString());
                accountDtlEntity.setDisplayEmail("" + etEmailId_posp.getText().toString().trim());
            } else if (type == 0) {
                accountDtlEntity.setDesignation("" + etSubHeading.getText().toString());
                accountDtlEntity.setEditMobiNumb("" + etMobileNo.getText().toString());
                accountDtlEntity.setEditEmailId("" + etEmailId.getText().toString().trim());

                accountDtlEntity.setDisplayDesignation("" + etSubHeading_posp.getText().toString().toString());
                accountDtlEntity.setDisplayPhoneNo("" + etMobileNo_posp.getText().toString());
                accountDtlEntity.setDisplayEmail("" + etEmailId_posp.getText().toString().trim());
            }
        }

        dbPersistanceController.updateMyAccountData(accountDtlEntity);
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            // do something with the bitmap
            // for demonstration purposes, let's just set it to an ImageView
            ivUser.setImageBitmap(bitmap);
        }
    };


    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    // Set All File When Page was Display .
    private void setDocumentUpload(int fileType, String FileName) {
        if (fileType == 1 || fileType == 2) {
            //ProfiePics
            /*Glide.with(MyAccountActivity.this)
                    .load(FileNmae)
                    .asBitmap()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
                    .into(target);*/

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                final Animation zoomAnim = AnimationUtils.loadAnimation(MyAccountActivity.this, R.anim.zoom_out);
//                ivUser.startAnimation(zoomAnim);
//            }


            if (FileName != null && !FileName.equals("")) {

                ivPhotoView.setTag(FileName);
                ivPhotoView.setVisibility(View.VISIBLE);

                ivPhoto.setImageResource(R.drawable.doc_uploaded);
                Glide.with(MyAccountActivity.this)
                        .load(FileName)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.circle_placeholder)
                        .skipMemoryCache(true)
                        .override(120, 120)
                        .transform(new CircleTransform(MyAccountActivity.this)) // applying the image transformer
                        .into(ivUser);


            } else {
                Glide.with(MyAccountActivity.this)
                        .load(R.drawable.finmart_user_icon)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.finmart_user_icon)
                        .skipMemoryCache(true)
                        .override(120, 120)
                        .transform(new CircleTransform(MyAccountActivity.this)) // applying the image transformer
                        .into(ivUser);
            }
        }

        if (fileType == 2) {
            ivPhoto.setImageResource(R.drawable.doc_uploaded);
            ivPhotoView.setTag(FileName);
            ivPhotoView.setVisibility(View.VISIBLE);

        } else if (fileType == 3) {
            ivPan.setImageResource(R.drawable.doc_uploaded);
            ivPanView.setTag(FileName);
            ivPanView.setVisibility(View.VISIBLE);

        } else if (fileType == 4) {
            ivCancel.setImageResource(R.drawable.doc_uploaded);

            ivCancelView.setTag(FileName);
            ivCancelView.setVisibility(View.VISIBLE);
        } else if (fileType == 5) {
            ivAadhar.setImageResource(R.drawable.doc_uploaded);

            ivAadharView.setTag(FileName);
            ivAadharView.setVisibility(View.VISIBLE);
        }
    }

    // Reflect particular File After File was Uploadwd .
    private void setDocumentUpload(String URL) {
        if (type == 1) {
            ivPhoto.setImageResource(R.drawable.doc_uploaded);
            ivPhotoView.setTag(URL);
            ivPhotoView.setVisibility(View.VISIBLE);
            Glide.with(MyAccountActivity.this)
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.circle_placeholder)
                    .skipMemoryCache(true)
                    .override(120, 120)
                    .transform(new CircleTransform(MyAccountActivity.this)) // applying the image transformer
                    .into(ivUser);
        } else if (type == 2) {
            ivPhoto.setImageResource(R.drawable.doc_uploaded);
            ivPhotoView.setTag(URL);
            ivPhotoView.setVisibility(View.VISIBLE);
            Glide.with(MyAccountActivity.this)
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.circle_placeholder)
                    .skipMemoryCache(true)
                    .override(120, 120)
                    .transform(new CircleTransform(MyAccountActivity.this)) // applying the image transformer
                    .into(ivUser);

        } else if (type == 3) {
            ivPanView.setTag(URL);
            ivPanView.setVisibility(View.VISIBLE);

            ivPan.setImageResource(R.drawable.doc_uploaded);
        } else if (type == 4) {
            ivCancelView.setTag(URL);
            ivCancelView.setVisibility(View.VISIBLE);
            ivCancel.setImageResource(R.drawable.doc_uploaded);
        } else if (type == 5) {
            ivAadharView.setTag(URL);
            ivAadharView.setVisibility(View.VISIBLE);
            ivAadhar.setImageResource(R.drawable.doc_uploaded);
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        shimmerMyAcccount.setVisibility(View.GONE);
        rlParent.setVisibility(View.VISIBLE);
        shimmerMyAcccount.stopShimmerAnimation();


        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (!hasFocus) {

            if ((etIfscCode.getText().length() > 3) && (isDataUploaded)) {

                Constants.hideKeyBoard(v, MyAccountActivity.this);
                showDialog("Fetching Bank Details...");
                new RegisterController(MyAccountActivity.this).getIFSC(etIfscCode.getText().toString(), MyAccountActivity.this);
            }
        }
    }

    private void galleryCamPopUp() {

        if (!checkPermission()) {

            //region comment
            if (checkRationalePermission()) {
                //Show Information about why you need the permission
                requestPermission();

            } else {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                //  permissionAlert(navigationView,"Need Call Permission","This app needs Call permission.");
                openPopUp(btnSave, "Need  Permission", "This app needs all permissions.", "GRANT", true);


            }
            //endregion


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

    private void launchCamera() {


        String FileName = "";

        switch (type) {
            case 1:
                FileName = PHOTO_File;
                break;
            case 2:
                FileName = PHOTO_File;
                break;
            case 3:
                FileName = PAN_File;
                break;
            case 4:
                FileName = CANCEL_CHQ_File;
                break;
            case 5:
                FileName = AADHAR_File;
                break;

        }
        //  Docfile = createFile(FileName);
        Docfile = createImageFile(FileName);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            imageUri = Uri.fromFile(Docfile);
        } else {
            imageUri = FileProvider.getUriForFile(MyAccountActivity.this,
                    getString(R.string.file_provider_authority), Docfile);
        }


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    private void openGallery() {

//        Docfile = createFile(FileName);
//
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
//


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

    private void setProfilePhoto(Bitmap mphoto) {

        bitmapPhoto = mphoto;        //original
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

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        //region handle result of CropImageActivity which was not supporting above Q

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
                        case 1:
                            showDialog();
                            file = saveImageToStorage(mphoto, PHOTO_File);
                            setProfilePhoto(mphoto);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginEntity.getFBAId(), PROFILE, PHOTO_File);

                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                        case 2:
                            showDialog();
                            file = saveImageToStorage(mphoto, PHOTO_File);
                            setProfilePhoto(mphoto);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginEntity.getFBAId(), PHOTO, PHOTO_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                        case 3:

                            showDialog();
                            file = saveImageToStorage(mphoto, PAN_File);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginEntity.getFBAId(), PAN, PAN_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;

                        case 4:
                            showDialog();
                            file = saveImageToStorage(mphoto, CANCEL_CHQ_File);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginEntity.getFBAId(), CANCEL_CHQ, CANCEL_CHQ_File);
                            new RegisterController(this).uploadDocuments(part, body, this);
                            break;
                        case 5:
                            showDialog();
                            file = saveImageToStorage(mphoto, AADHAR_File);
                            part = Utility.getMultipartImage(file);
                            body = Utility.getBody(this, loginEntity.getFBAId(), AADHAR, AADHAR_File);
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



        if (requestCode == Constants.SWITCH_USER_REQUEST_CODE) {
            if (data != null) {
                //switchUserBinding();
//                dbPersistanceController = new DBPersistanceController(this);
//                loginResponseEntity = dbPersistanceController.getUserData();
                // init_headers();

                Intent intent = new Intent(this, MyAccountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }

        }


        // region  commented Below   Code for Image and Camara Handling
//
//        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//        Bitmap mphoto = null;
//        try {
//          //  mphoto = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//            mphoto = getBitmapFromContentResolver(imageUri);
//            mphoto = getResizedBitmap(mphoto, 800);
//            mphoto = rotateImageIfRequired(this, mphoto, Docfile);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        switch (type) {
//            case 1:
//                showDialog();
//                file = saveImageToStorage(mphoto, PHOTO_File);
//                setProfilePhoto(mphoto);
//                part = Utility.getMultipartImage(file);
//                body = Utility.getBody(this, loginEntity.getFBAId(), PROFILE, PHOTO_File);
//
//                new RegisterController(this).uploadDocuments(part, body, this);
//                break;
//            case 2:
//                showDialog();
//                file = saveImageToStorage(mphoto, PHOTO_File);
//                setProfilePhoto(mphoto);
//                part = Utility.getMultipartImage(file);
//                body = Utility.getBody(this, loginEntity.getFBAId(), PHOTO, PHOTO_File);
//                new RegisterController(this).uploadDocuments(part, body, this);
//                break;
//            case 3:
//
//                showDialog();
//                file = saveImageToStorage(mphoto, PAN_File);
//                part = Utility.getMultipartImage(file);
//                body = Utility.getBody(this, loginEntity.getFBAId(), PAN, PAN_File);
//                new RegisterController(this).uploadDocuments(part, body, this);
//                break;
//
//            case 4:
//                showDialog();
//                file = saveImageToStorage(mphoto, CANCEL_CHQ_File);
//                part = Utility.getMultipartImage(file);
//                body = Utility.getBody(this, loginEntity.getFBAId(), CANCEL_CHQ, CANCEL_CHQ_File);
//                new RegisterController(this).uploadDocuments(part, body, this);
//                break;
//            case 5:
//                showDialog();
//                file = saveImageToStorage(mphoto, AADHAR_File);
//                part = Utility.getMultipartImage(file);
//                body = Utility.getBody(this, loginEntity.getFBAId(), AADHAR, AADHAR_File);
//                new RegisterController(this).uploadDocuments(part, body, this);
//                break;
//        }
//
//
//    }
//
//        else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
//        Uri selectedImageUri = data.getData();
//        Bitmap mphoto = null;
//        try {
//            // mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
//            mphoto = getBitmapFromContentResolver(selectedImageUri);
//            mphoto = getResizedBitmap(mphoto, 800);
//          //  mphoto = rotateImageIfRequired(this, mphoto, Docfile);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        switch (type) {
//                case 1:
//                    showDialog();
//                    setProfilePhoto(mphoto);
//                    file = saveImageToStorage(mphoto, PHOTO_File);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginEntity.getFBAId(), PROFILE, PHOTO_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//
//                    break;
//                case 2:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, PHOTO_File);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginEntity.getFBAId(), PHOTO, PHOTO_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 3:
//
//                    showDialog();
//                    file = saveImageToStorage(mphoto, PAN_File);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginEntity.getFBAId(), PAN, PAN_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//
//                case 4:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, CANCEL_CHQ_File);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginEntity.getFBAId(), CANCEL_CHQ, CANCEL_CHQ_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//                case 5:
//                    showDialog();
//                    file = saveImageToStorage(mphoto, AADHAR_File);
//                    part = Utility.getMultipartImage(file);
//                    body = Utility.getBody(this, loginEntity.getFBAId(), AADHAR, AADHAR_File);
//                    new RegisterController(this).uploadDocuments(part, body, this);
//                    break;
//            }
//
//    }
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
            openPopUp(btnSave, "Need  Permission", "This app needs all permissions.", "GRANT", true);

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

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(MyAccountActivity.this, perms[0]);

        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(MyAccountActivity.this, perms[1]);
        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(MyAccountActivity.this, perms[2]);
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



            case Constants.PERMISSION_CALLBACK_CONSTANT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean call_phone = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (call_phone) {

                        ConfirmAlert("Calling", getResources().getString(R.string.RM_Calling) + " " + dbPersistanceController.getUserConstantsData().getManagName(), dbPersistanceController.getConstantsData().getHelpNumber());


                    }

                }

                break;


            case Constants.PERMISSION_CALLBACK_SUPPORT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean call_phone = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (call_phone) {

                        ConfirmAlert("Calling", getResources().getString(R.string.Support_Calling), dbPersistanceController.getUserConstantsData().getSuppMobile());

                    }

                }

                break;
        }
    }

    //endregion
    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {

        dialog.cancel();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);

    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {

        dialog.cancel();
    }



    public void ConfirmAlert(String Title, String strBody, final String strMobile) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAccountActivity.this);
            builder.setTitle(Title);

            builder.setMessage(strBody);
            String positiveText = "Call";
            String NegativeText = "Cancel";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intentCalling = new Intent(Intent.ACTION_DIAL);
                            intentCalling.setData(Uri.parse("tel:" + strMobile));
                            startActivity(intentCalling);
                        }
                    });

            builder.setNegativeButton(NegativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(this, "Please try again..", Toast.LENGTH_SHORT).show();
        }
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

        Intent intent = new Intent(MyAccountActivity.this, HomeMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

        overridePendingTransition(0,0);

    }

    @TargetApi(25)
    private void removeShorcuts() {
        try {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            shortcutManager.disableShortcuts(Arrays.asList("ID1"));
            shortcutManager.disableShortcuts(Arrays.asList("ID2"));
            shortcutManager.disableShortcuts(Arrays.asList("ID3"));
            shortcutManager.disableShortcuts(Arrays.asList("ID4"));
            shortcutManager.removeAllDynamicShortcuts();
        }catch (Exception ex){}

    }

    @Override
    public void onClickLogout() {

        SharedPreferences preferences = getSharedPreferences(Constants.SWITCh_ParentDeatils_FINMART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            removeShorcuts();
        }

        dialogLogout(MyAccountActivity.this);
    }

    @Override
    public void onSwitchUser() {

        startActivityForResult(new Intent(MyAccountActivity.this, SwitchUserActivity.class), Constants.SWITCH_USER_REQUEST_CODE);

    }

    @Override
    public void ConfirmnMyUtilitiesAlert() {

        if (loginResponseEntity != null) {

            ConfirmnUtilitiesAlert(loginResponseEntity);

        }



    }
}
