package com.policyboss.policybosspro.webviews;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.file_chooser.utils.FileUtilNew;
import com.policyboss.policybosspro.health.HealthQuoteAppActivity;
import com.policyboss.policybosspro.home.HomeActivity;

import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity;
import com.policyboss.policybosspro.homeMainKotlin.Util;
import com.policyboss.policybosspro.motor.privatecar.activity.InputQuoteBottmActivity;
import com.policyboss.policybosspro.motor.twowheeler.activity.TwoWheelerQuoteAppActivity;
import com.policyboss.policybosspro.paymentEliteplan.RazorPaymentEliteActivity;
import com.policyboss.policybosspro.paymentEliteplan.SyncRazorPaymentActivity;
import com.policyboss.policybosspro.term.termselection.TermSelectionActivity;
import com.policyboss.policybosspro.utility.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.dynamic_urls.DynamicController;
import magicfinmart.datacomp.com.finmartserviceapi.dynamic_urls.model.synctransactionDetailEntity;
import magicfinmart.datacomp.com.finmartserviceapi.dynamic_urls.response.synctransactionDetailReponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.zoho.ZohoController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RaiseTicketWebDocResponse;
import okhttp3.MultipartBody;

import static com.policyboss.policybosspro.file_chooser.utils.FileUtilNew.generateFileName;

public class CommonWebViewActivity extends BaseActivity implements BaseActivity.PopUpListener, BaseActivity.WebViewPopUpListener, IResponseSubcriber {

    WebView webView;
    String url = "";
    String name = "";
    String title = "";
    String dashBoardtype = "";
    String APPMENU = "";
    String CRNID = "";
    String RACResult = "";

    CountDownTimer countDownTimer;
    public static boolean isActive = false;
    Toolbar toolbar;
     String TAG = "POLICYBOSSPRO";

    LoginResponseEntity loginResponseEntity;
    DBPersistanceController db;
    UserConstantEntity userConstantEntity;

    // region Camera Permission
    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1800;
    private int PICK_PDF_REQUEST = 1805;
    Button btnSubmit;
    EditText etComment;
    ImageView ivUser, ivCross, ivProfile;
    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"

    };
    //endregion

    // region Camera Declaration

    File Docfile;
    File file;
    Uri imageUri;
    private Uri cropImageUri;
    //    InputStream inputStream;
//    ExifInterface ei;
//    Bitmap bitmapPhoto = null;
    private String PHOTO_File = "";
    MultipartBody.Part part;
    //HashMap<String, String> body;


    androidx.appcompat.app.AlertDialog alertDialog;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web_view);
        webView = (WebView) findViewById(R.id.webView);

        url = getIntent().getStringExtra("URL");
        name = getIntent().getStringExtra("NAME");
        title = getIntent().getStringExtra("TITLE");
        if (getIntent().getStringExtra("dashBoardtype") != null) {

            dashBoardtype = getIntent().getStringExtra("dashBoardtype");
        }

        // For Handling Activity Back when URL Open using ShortCut Menu of app
        if (getIntent().getStringExtra("APPMENU") != null) {

            APPMENU = getIntent().getStringExtra("APPMENU");
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        db = new DBPersistanceController(this);
        loginResponseEntity = db.getUserData();
        userConstantEntity = db.getUserConstantsData();
        CRNID = "";
        RACResult = "";

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (name.equals("ICICI PRUDENTIAL DOWNLOAD")
                || name.equals("LOAN_AGREEMENT") || name.equals("LIC Business") || name.equals("OfflineQuotes")) {
            // fab.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                downloadPdf(url, name);   //05 temp
            }
        });
        if (isNetworkConnected()) {
            settingWebview();
            startCountDownTimer();
        } else
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
    }

    private void navigateToHome(){

        Intent intent = new Intent(this, HomeMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("MarkTYPE", "FROM_HOME");

        startActivity(intent);
        finish();
    }
    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                try {
                    cancelDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        countDownTimer.start();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void downloadPdf(String url, String name) {
        Toast.makeText(this, "Download started..", Toast.LENGTH_LONG).show();
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name + ".pdf");
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

    private void settingWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setSupportMultipleWindows(false);

        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);


      /*  MyWebViewClient webViewClient = new MyWebViewClient(this);
        webView.setWebViewClient(webViewClient);*/
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image
                if (isActive)
                    showDialog(CommonWebViewActivity.this);
                // new ProgressAsync().execute();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO hide your progress image
                cancelDialog();
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //whatsapp plugin call.. via WEB
//                if (url != null && url.startsWith("whatsapp://")) {
//                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                    return true;
//                } else
                if (url.endsWith(".pdf")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "application/pdf");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //user does not have a pdf viewer installed
                        String googleDocs = "https://docs.google.com/viewer?url=";
                        webView.loadUrl(googleDocs + url);
                    }
                }

                /*qacamp@gmail.com/01011980
                download policy QA user
                878769 crn
                */
                return false;
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");
        webView.addJavascriptInterface(new PaymentInterface(), "PaymentInterface");
        // webView.setWebChromeClient(new WebChromeClient();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //Required functionality here
                return super.onJsAlert(view, url, message, result);
            }
        });
        // webView.setWebChromeClient(new WebChromeClient());
        Log.d("URL", url);

        if (url.endsWith(".pdf")) {

            webView.loadUrl("https://docs.google.com/viewer?url=" + url);
            //webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
        } else {
            webView.loadUrl(url);
        }
        //webView.loadUrl(url);
    }


    // region Popup Method Interface


    @Override
    public void onCancelClick(Dialog dialog, View view) {

        dialog.cancel();
    }

    //endregion


    private class ProgressAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            for (int i = 0; i < 8000000; i++) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cancelDialog();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:

                    Log.i("BACK", "Back Triggered");
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        if (!APPMENU.equals("")) {
                            navigateToHome();
                        }else{
                            finish();
                        }

                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    class PaymentInterface {
        @JavascriptInterface
        public void success(String data) {
        }

        @JavascriptInterface
        public void error(String data) {
        }
    }

    class MyJavaScriptInterface {

        @JavascriptInterface
        public void crossselltitle(String dynamicTitle) {

            getSupportActionBar().setTitle(dynamicTitle);

        }

        // region Raise Ticket Note :Below Method  Upload_doc and Upload_doc_view Called For Activity Not For Dialog
        // For Dialog We have Used "Base Activity" JavaScript (All Insurance Popup Coming from there because it will already open from CommonWebView)
        // In Menu Raise Tickets Activity :Upload_doc and Upload_doc_view comming From Below code since its Activity Page.


        @JavascriptInterface
        public void Upload_doc(String randomID) {

            galleryCamPopUp(randomID);


        }

        @JavascriptInterface
        public void Upload_doc_view(String randomID) {

            galleryCamPopUp(randomID);

        }

        // endregion


        //region RAC Form
        @JavascriptInterface
        public void getraccamera(String crnid) {

            CRNID = crnid;
            galleryCamRACPopUp(crnid);


        }


        @JavascriptInterface
        public String savecameraimage() {

            return  CRNID  +"||"+ RACResult;


        }

        @JavascriptInterface
        public String showpreview() {

            return  CRNID  +"||"+ RACResult;


        }


        @JavascriptInterface

        public void getchromeurl(String url) {


            Utility.loadWebViewUrlInBrowser(CommonWebViewActivity.this,  url);


        }


        //endregion

        @JavascriptInterface
        public void SendShareQuotePdf(String url, String shareHtml) {

            Intent intent = new Intent(CommonWebViewActivity.this, ShareQuoteActivity.class);
            intent.putExtra(Constants.SHARE_WHATSAPP, "SHARE_WHATSAPP");
            intent.putExtra("HTML", shareHtml);
            intent.putExtra("URL", url);
            startActivity(intent);


        }

        @JavascriptInterface
        public void AddNewMotorQuote() { //Android.AddNewMotorQuote();
            Intent intent;
            if (url.contains("buynowTwoWheeler")) {
                intent = new Intent(CommonWebViewActivity.this, TwoWheelerQuoteAppActivity.class);
            } else {
                intent = new Intent(CommonWebViewActivity.this, InputQuoteBottmActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        @JavascriptInterface
        public void AddNewHealthQuote() {//Android.AddNewHealthQuote();
            Intent intent;
            intent = new Intent(CommonWebViewActivity.this, HealthQuoteAppActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        @JavascriptInterface
        public void AddNewTermQuote() {//Android.AddNewTermQuote();
            Intent intent;
            intent = new Intent(CommonWebViewActivity.this, TermSelectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        @JavascriptInterface
        public void RedirectToHomepage() {//Android.RedirectToHomepage();
            Intent intent = new Intent(CommonWebViewActivity.this, HomeMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        @JavascriptInterface
        public void callPDFCREDIT(String u) {

            webView.loadUrl("http://www.google.com");

//            startActivity(new Intent(CommonWebViewActivity.this, CommonWebViewActivity.class)
//                    .putExtra("URL", url)
//                    .putExtra("NAME", "FREE CREDIT REPORT")
//                    .putExtra("TITLE", "LIC FREE CREDIT REPORT"));
        }



        @JavascriptInterface
        public void redirecthomeloan() {//Android.RedirectToHomepage();
//            Intent intent = new Intent(CommonWebViewActivity.this, NewHomeApplicaionActivity.class);
    //        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
     //       startActivity(intent);
     //       finish();
        }

        @JavascriptInterface
        public void redirectbusinessloan() {//Android.RedirectToHomepage();
           // Intent intent = new Intent(CommonWebViewActivity.this, NewbusinessApplicaionActivity.class);
       //     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         //   startActivity(intent);
         //  finish();
        }

        @JavascriptInterface
        public void EliteKotakRazorPay(String cust_id) {
            Intent intent = new Intent(CommonWebViewActivity.this, RazorPaymentEliteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("cust_id", cust_id);
            startActivity(intent);
            finish();
        }

        @JavascriptInterface
        public void syncrazorpay(String transactionId) {

            if (!transactionId.equals("")) {
                getSyncPaymentDetail(transactionId);
            }

//            Intent intent = new Intent(CommonWebViewActivity.this, SyncRazorPaymentActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.putExtra("transactionId", transactionId);
//            startActivity(intent);
//            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if(APPMENU.equals("")){
                    finish();
                }else{
                    navigateToHome();
                }

                return true;

            case R.id.action_home:

                navigateToHome();
                return true;

            case R.id.action_raise:
                // Toast.makeText(this,"Popup",Toast.LENGTH_SHORT).show();
                String url = userConstantEntity.getRaiseTickitUrl() + "&mobile_no=" + userConstantEntity.getMangMobile()
                        + "&UDID=" + userConstantEntity.getUserid();
                Log.d("URL", "Raise Ticket URL: " + url);
                //  openWebViewPopUp(webView,  url, true, CommonWebViewActivity.this);
                openWebViewPopUp(webView, url, true, "Raise Ticket");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (dashBoardtype.equals("INSURANCE")) {
            getMenuInflater().inflate(R.menu.insurance_menu, menu);
       } else {
            getMenuInflater().inflate(R.menu.home_menu, menu);
       }

        return true;
   }

   /*
    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
*/

    private void getSyncPaymentDetail(String transactionId) {

        showDialog();
        new DynamicController(this).getSync_trascat_detail(String.valueOf(transactionId), CommonWebViewActivity.this);
    }


    // region Camera & Gallery Popup For Raise Ticket

    public void galleryCamPopUp(String randomID) {

        PHOTO_File = "";
        PHOTO_File = "fm_file" + "_" + randomID;
        Log.i("RAISE_TICKET Uploding", PHOTO_File);

        if (!checkPermission()) {

            if (checkRationalePermission()) {
                //Show Information about why you need the permission
                requestPermission();

            } else {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                //  permissionAlert(navigationView,"Need Call Permission","This app needs Call permission.");
                openPopUp(ivUser, "Need  Permission", "This app needs all permissions.", "GRANT", true);


            }
        } else {

            showCamerGalleryPopUp();
        }
    }

    public void galleryCamRACPopUp(String crnid) {

        PHOTO_File = "";
        PHOTO_File =  crnid;
        Log.d(TAG,"RAC CRN NO "+ PHOTO_File);

        if (!checkPermission()) {

            if (checkRationalePermission()) {
                //Show Information about why you need the permission
                requestPermission();

            } else {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                //  permissionAlert(navigationView,"Need Call Permission","This app needs Call permission.");
                openPopUp(ivUser, "Need  Permission", "This app needs all permissions.", "GRANT", true);


            }
        } else {

            showCamerGalleryPopUp();
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    // region permission
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

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(CommonWebViewActivity.this, perms[0]);

        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(CommonWebViewActivity.this, perms[1]);
        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(CommonWebViewActivity.this, perms[2]);

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

    // region Camera Dialog
    private void showCamerGalleryPopUp() {

        if (alertDialog != null && alertDialog.isShowing()) {

            return;
        }
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.CustomDialog);

        LinearLayout lyCamera, lyGallery, lyPdf;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_cam_gallery_pdf, null);

        builder.setView(dialogView);
        alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        lyCamera = (LinearLayout) dialogView.findViewById(R.id.lyCamera);
        lyGallery = (LinearLayout) dialogView.findViewById(R.id.lyGallery);
        lyPdf = (LinearLayout) dialogView.findViewById(R.id.lyPdf);

        if(!CRNID.equals("")){

            lyPdf.setVisibility(View.GONE);

        }else{

            lyPdf.setVisibility(View.VISIBLE);
        }

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
        lyPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }

    private void showFileChooser() {
        // Create the ACTION_GET_CONTENT Intent
        Intent getContentIntent = FileUtilNew.createGetContentIntent();    // Only For PDF Pls check createGetContentIntent() method
        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
        startActivityForResult(intent, PICK_PDF_REQUEST);

//        Intent intent = FileUtilNew.getCustomFileChooserIntent(FileUtilNew.DOC, FileUtilNew.PDF, FileUtilNew.XLS,FileUtilNew.TEXT,FileUtilNew.DOCX);
//
//        startActivityForResult(intent, PICK_PDF_REQUEST);

    }


    private void launchCamera() {


        String FileName = PHOTO_File;


        Docfile = createImageFile(FileName);


        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            imageUri = Uri.fromFile(Docfile);
        } else {
            imageUri = FileProvider.getUriForFile(CommonWebViewActivity.this,
                    getString(R.string.file_provider_authority), Docfile);
        }


        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    private void openGallery() {

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

    //endregion


    //region Base PopUp EventFor Handling the Default Setting


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

    //endregion


    public String getFileFromDownload(Context context, Uri uri) {
        final String id = DocumentsContract.getDocumentId(uri);

        if (id != null && id.startsWith("raw:")) {
            return id.substring(4);
        }

        String[] contentUriPrefixesToTry = new String[]{
                "content://downloads/public_downloads",
                "content://downloads/my_downloads",
                "content://downloads/all_downloads"
        };

        for (String contentUriPrefix : contentUriPrefixesToTry) {
            Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
            try {
                String path = FileUtilNew.getDataColumn(context, contentUri, null, null);
                if (path != null) {
                    return path;
                }
            } catch (Exception e) {
            }
        }

        // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
        String fileName = FileUtilNew.getFileName(context, uri);
        File cacheDir = FileUtilNew.getDocumentCacheDir(context);
        File file = generateFileName(fileName, cacheDir);
        String destinationPath = null;
        if (file != null) {
            destinationPath = file.getAbsolutePath();
            FileUtilNew.saveFileFromUri(context, uri, destinationPath);
        }

        return destinationPath;
    }

    // region Event
    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Only For Pdf
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            final Uri uri = data.getData();
            // Get the File path from the Uri
            try {


                String path = FileUtilNew.getPath(this, uri);

                if (!path.contains(".")) {

                    showAlert("Only file type allowed to be uploaded are\n(Image,Docs,PDF,Excel & Text.)");
                    return;
                }
                String extension = path.substring(path.lastIndexOf(".")).toLowerCase();
                if (extension.contains("pdf") || extension.contains("xls") || extension.contains("xlsx") || extension.contains("txt") || extension.contains("doc")
                        || extension.contains("docs") || extension.contains("jpeg") || extension.contains("jpg") || extension.contains("png")) {


                    File pdfFile = new File(path);
                    if (pdfFile.exists()) {
                        showDialog();


                        part = Utility.getMultipartPdf(pdfFile, PHOTO_File, "doc_type");
                        new ZohoController(this).uploadRaiseTicketDocWeb(part, this);

                    } else {
                        showAlert("Select file from File Manager.");
                    }
                } else {

                    showAlert("Only file type allowed to be uploaded are\n(Image,Docs,PDF,Excel & Text.)");
                }


            } catch (Exception ex) {

                showAlert("Select file from File Manager.");
            }
        } else {

            // Below For Cropping The Camera Image
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                //extractTextFromImage();
                startCropImageActivity(imageUri);
            }
            // Below For Cropping The Gallery Image
            else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                startCropImageActivity(selectedImageUri);
            }

            //region Below  handle result of CropImageActivity
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {
                    try {
                        cropImageUri = result.getUri();
                        Bitmap mphoto = null;

                        try {
                           // mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cropImageUri);
                            mphoto = getBitmapFromContentResolver(cropImageUri);
                            //  mphoto = getResizedBitmap(mphoto, 800);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        showDialog();

                        if(!CRNID.equals("")){

                            Handler hndler= new Handler(Looper.getMainLooper());
                            Bitmap finalMphoto = mphoto;

                            hndler.postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    try {
                                        RACResult =   Util.bitmapToBase64(finalMphoto);
                                    }catch (Exception e){}
                                    finally {

                                        cancelDialog();
                                    }

                                }

                            },1000);



                        }else{

                            file = saveImageToStorage(mphoto, PHOTO_File);
                            // setProfilePhoto(mphoto);

                            part = Utility.getMultipartImage(file, "doc_type");

                            new ZohoController(this).uploadRaiseTicketDocWeb(part, this);

                        }


                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
            }

            //endregion
        }


    }




    @Override
    public void OnSuccess(APIResponse response, String message) {


        cancelDialog();
        //RaiseTicketWebDocResponse
        if (response instanceof RaiseTicketWebDocResponse) {
            if (response.getStatusNo() == 0) {


                Toast.makeText(CommonWebViewActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
//                String jsonResponse =  new Gson().toJson(response).toString();
//                jsonResponse = jsonResponse.replace("\"", "'");

                String jsonResponse = ((RaiseTicketWebDocResponse) response).getMasterData().getFile_name() + "|" +
                        ((RaiseTicketWebDocResponse) response).getMasterData().getFile_path();
                Log.i("RAISE_TICKET RESPONSE", jsonResponse);

                // Sending Data to Web Using evaluateJavascript
                // When Activty Page Called than This One is rasied.
                webView.evaluateJavascript("javascript: " +
                        "uploadImagePath(\"" + jsonResponse + "\")", null);


                //////////// When Dialog Page Called via Base Activity below method raised
                uploadWebViewRaiserPath(jsonResponse);

            } else {
                Toast.makeText(CommonWebViewActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        else if (response instanceof synctransactionDetailReponse) {

            if (((synctransactionDetailReponse) response).getStatus().equals("success")) {
                synctransactionDetailEntity synctransactionEntity = ((synctransactionDetailReponse) response).getMasterData();


                if (synctransactionEntity != null) {
                    Intent intent = new Intent(CommonWebViewActivity.this, SyncRazorPaymentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("SYNC_TRANSACTION", synctransactionEntity);
                    startActivity(intent);
                    finish();
                }


            }

        }

    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(CommonWebViewActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    //endregion

    // endregion
}
