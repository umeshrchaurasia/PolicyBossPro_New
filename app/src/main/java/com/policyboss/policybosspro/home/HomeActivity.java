package com.policyboss.policybosspro.home;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.BuildConfig;
import com.policyboss.policybosspro.IncomeCalculator.IncomePotentialActivity;
import com.policyboss.policybosspro.MyApplication;
import com.policyboss.policybosspro.R;

import com.policyboss.policybosspro.certificate.POSP_certicate_appointment;
import com.policyboss.policybosspro.change_password.ChangePasswordFragment;
import com.policyboss.policybosspro.contact_lead.ContactLeadActivity;
import com.policyboss.policybosspro.dashboard.DashboardFragment;
import com.policyboss.policybosspro.festivelink.festivelinkActivity;
import com.policyboss.policybosspro.generatelead.GenerateLeadActivity;
import com.policyboss.policybosspro.health.healthquotetabs.HealthQuoteBottomTabsActivity;
import com.policyboss.policybosspro.healthcheckupplans.HealthCheckUpListActivity;
import com.policyboss.policybosspro.healthcheckupplans.HealthCheckUpPlansActivity;
import com.policyboss.policybosspro.helpfeedback.HelpFeedBackActivity;
import com.policyboss.policybosspro.helpfeedback.raiseticket.RaiseTicketActivity;
import com.policyboss.policybosspro.home.adapter.CallingDetailAdapter;
import com.policyboss.policybosspro.knowledgeguru.KnowledgeGuruActivity;

import com.policyboss.policybosspro.login.LoginActivity;
import com.policyboss.policybosspro.messagecenter.messagecenteractivity;
import com.policyboss.policybosspro.motor.privatecar.activity.InputQuoteBottmActivity;
import com.policyboss.policybosspro.motor.twowheeler.activity.BikeAddQuoteActivity;
import com.policyboss.policybosspro.mps.KnowMoreMPSFragment;
import com.policyboss.policybosspro.mps.MPSFragment;
import com.policyboss.policybosspro.myaccount.MyAccountActivity;
import com.policyboss.policybosspro.mybusiness.MyBusinessActivity;
import com.policyboss.policybosspro.notification.NotificationActivity;
import com.policyboss.policybosspro.notification.NotificationSmsActivity;
import com.policyboss.policybosspro.pendingcases.PendingCasesActivity;
import com.policyboss.policybosspro.posp.POSPListFragment;
import com.policyboss.policybosspro.posp.PospEnrollment;
import com.policyboss.policybosspro.quicklead.QuickLeadActivity;
import com.policyboss.policybosspro.salesmaterial.SalesMaterialActivity;
import com.policyboss.policybosspro.scan_vehicle.VehicleScanActivity;
import com.policyboss.policybosspro.sendTemplateSms.SendTemplateSmsActivity;
import com.policyboss.policybosspro.share_data.ShareDataFragment;
import com.policyboss.policybosspro.splashscreen.SplashScreenActivity;
import com.policyboss.policybosspro.switchuser.SwitchUserActivity;
import com.policyboss.policybosspro.term.compareterm.CompareTermActivity;
import com.policyboss.policybosspro.term.termselection.TermSelectionActivity;
import com.policyboss.policybosspro.transactionhistory.nav_transactionhistoryActivity;
import com.policyboss.policybosspro.utility.CircleTransform;
import com.policyboss.policybosspro.utility.Constants;
import com.policyboss.policybosspro.utility.ReadDeviceID;
import com.policyboss.policybosspro.webviews.CommonWebViewActivity;
import com.policyboss.policybosspro.whatsnew.WhatsNewActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.login.LoginController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.MasterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.tracking.TrackingController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.MenuItemEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.MenuMasterResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.NotifyEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ProductURLShareEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.TrackingData;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserCallingEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.LoginRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.TrackingRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ConstantsResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.LoginResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MpsResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MultiLangResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MyAcctDtlResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ProductURLShareResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserCallingResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserConstatntResponse;
import magicfinmart.datacomp.com.finmartserviceapi.model.DashboardMultiLangEntity;

public class HomeActivity extends BaseActivity implements IResponseSubcriber, BaseActivity.PopUpListener,
        BaseActivity.WebViewPopUpListener, BaseActivity.PermissionListener {

    final String TAG = "HOME";
    final String mapKey = "map_switchuser";
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    TextView textNotifyItemCount, txtEntityName, txtDetails, txtReferalCode, txtFbaID, txtPospNo, txtErpID, txtknwyour, txtswitchuser;
    ImageView ivProfile, ivCancel;
    LinearLayout lySwitchUser;

    LoginResponseEntity loginResponseEntity;
    DBPersistanceController db;
    String versionNAme;
    PackageInfo pinfo;
    PrefManager prefManager;
    int forceUpdate, checkfirstmsg_call, isContactFirstCall;
    ConstantEntity constantEntity;
    AlertDialog mpsDialog;
    androidx.appcompat.app.AlertDialog shareProdDialog;
    CallingDetailAdapter callingDetailAdapter;

    UserConstantEntity userConstantEntity;

    MenuMasterResponse menuMasterResponse;
    AlertDialog callingDetailDialog, finmartContacttDialog, LoanDialog, MoreServiceDialog, MyUtilitiesDialog;
    int selectedLang = -1;
    String LANGUAGE;

    LinearLayout lstswitchuser, lstswitchChild_user;
    TextView txtparentuser, txtchilduser;

    List<TextView> textViewList;

    LinearLayout llSwitchUser;
    DashboardMultiLangEntity dashboardShareEntity;

    ShortcutManager shortcutManager = null;


    //region broadcast receiver
    public BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null) {
                if (intent.getAction().equalsIgnoreCase(Utility.PUSH_BROADCAST_ACTION)) {
                    int notifyCount = prefManager.getNotificationCounter();

                    if (notifyCount == 0) {
                        textNotifyItemCount.setVisibility(View.GONE);
                    } else {
                        textNotifyItemCount.setVisibility(View.VISIBLE);
                        textNotifyItemCount.setText("" + String.valueOf(notifyCount));
                    }
                } else if (intent.getAction().equalsIgnoreCase(Utility.USER_PROFILE_ACTION)) {
                    String PROFILE_PATH = intent.getStringExtra("PROFILE_PATH");

                    Glide.with(HomeActivity.this)
                            .load(Uri.parse(PROFILE_PATH))
                            .placeholder(R.drawable.finmart_user_icon)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .override(64, 64)
                            .transform(new CircleTransform(HomeActivity.this)) // applying the image transformer
                            .into(ivProfile);

                }
            }

        }
    };


    //endregion

    @Override
    public void dialogExit() {
        super.dialogExit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        registerPopUp(this);
        registerPermission(this);
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        llSwitchUser = findViewById(R.id.llSwitchUser);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);


//        try {
//            FirebaseMessaging.getInstance().subscribeToTopic("finmartall");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNAme = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        try {
            Utility.getMacAddress(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        db = new DBPersistanceController(this);
        loginResponseEntity = db.getUserData();
        userConstantEntity = db.getUserConstantsData();
        prefManager = new PrefManager(this);

        textViewList = new ArrayList<>();

        toolbar.setTitle("PolicyBoss Pro");



        getNotificationAction();

        init_headers();


        setNavigationMenu(prefManager.getLanguage());    // Set Navigation Drawer


        if (savedInstanceState == null) {
            selectHome();
        }


        // will be called once when ever app is opened

        if (db.getRTOListNames() != null && db.getRTOListNames().size() <= 0) {
            new MasterController(this).getRTOMaster(this);
        }

        if (loginResponseEntity != null) {
            if (loginResponseEntity.getPOSPNo().equals("5")) {
                verifyPospNo();
                return;
            }

        }


        checkfirstmsg_call = Integer.parseInt(prefManager.getCheckMsgFirst());
        if (checkfirstmsg_call == 0) {
            String type = "";
            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                if (bundle.getString("MarkTYPE") != null) {
                    type = bundle.getString("MarkTYPE");
                    if (!type.equals("FROM_HOME")) {
                        showMarketingPopup();
                    }
                }

            } else {
                prefManager.updateCheckMsgFirst("" + 1);
                showMarketingPopup();
            }


        }
        //region navigation click
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action

                Constants.hideKeyBoard(drawerLayout, HomeActivity.this);
                Fragment fragment = null;

                //hide keyboard
                Constants.hideKeyBoard(drawerLayout, HomeActivity.this);
                if (menuMasterResponse != null) {
                    for (MenuItemEntity menuItemEntity : menuMasterResponse.getMasterData().getMenu()) {
                        int sequence = Integer.parseInt(menuItemEntity.getSequence());
                        sequence = (sequence * 100) + 1;
                        if (menuItem.getItemId() == sequence) {
                            startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                    .putExtra("URL", menuItemEntity.getLink())
                                    .putExtra("NAME", menuItemEntity.getMenuname())
                                    .putExtra("TITLE", menuItemEntity.getMenuname()));
                            return true;
                        }
                    }
                }


                switch (menuItem.getItemId()) {

                    case R.id.nav_attendance:
//                        fragment = new AttendanceFragment();
//                        getSupportActionBar().setTitle("My Attendance");

                        break;

                    case R.id.nav_generateLead:
                        startActivity(new Intent(HomeActivity.this, GenerateLeadActivity.class));
                        break;

                    //added by Nilesh
//                    case R.id.nav_vehicleinfo:
//                        getSupportActionBar().setTitle("VEHICLE DETAIL");
//                        fragment = new VehicleDetailFragment();
//                        break;

//                    case R.id.nav_expressLoan:
//                        startActivity(new Intent(HomeActivity.this, AppliedOnlineLoanListActivity.class));
//                        break;
                    //Replacing the main content with ContentFragment Which is our Inbox View;
//                    case R.id.nav_yesbankbot:
//                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
//                                .putExtra("URL", "https://yesbankbot.buildquickbots.com/chat/rupeeboss/staff/?userid=" + String.valueOf(loginResponseEntity.getFBAId()) + "&usertype=FBA&vkey=b34f02e9-8f1c")
//                                .putExtra("NAME", "" + "YES BANK BOT")
//                                .putExtra("TITLE", "" + "YES BANK BOT"));
//
//                        break;
                    case R.id.nav_home:
                        fragment = new DashboardFragment();
                        // getSupportActionBar().setTitle("MAGIC FIN-MART");
                        if (prefManager.getLanguage().equals("")) {
                            getSupportActionBar().setTitle("PolicyBoss Pro");
                        } else {
                            getSupportActionBar().setTitle(db.getLangData(prefManager.getLanguage(), "Title"));
                        }

                        //Toast.makeText(HomeActivity.this, "Dashboard", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.nav_language:

                        if (db.isMultiLangExist() == false) {
                            showDialog();
                            new RegisterController(HomeActivity.this).getMultiLanguageDetailOld(HomeActivity.this);
                        } else {
                            showMultiLanguage();
                        }
                        break;

                    case R.id.nav_insert_contact:
                        //  startActivity(new Intent(HomeActivity.this, InsertContactActivity.class));

                        break;
                    case R.id.nav_sharedata:
                        fragment = new ShareDataFragment();
                        getSupportActionBar().setTitle("Generate Loan Leads");
                        break;
                    case R.id.nav_changepassword:
                        fragment = new ChangePasswordFragment();
                        getSupportActionBar().setTitle("Change Password");
                        break;
                    // For rest of the options we just show a toast on click .
                    case R.id.nav_myaccount:
                       // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("My ACCOUNT : My ACCOUNT button in menu "), Constants.MY_ACCOUNT), null);
                        startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));
                        //  startActivity(new Intent(HomeActivity.this, HomeLoanApplyActivity.class));
                        // fragment = new BasFragment();
                        // getSupportActionBar().setTitle("BAS 2016-17");
                        // Toast.makeText(HomeActivity.this, "my_account", Toast.LENGTH_SHORT).show();

                        //startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));


                        break;

                    case R.id.nav_pospenrollment:
                        startActivity(new Intent(HomeActivity.this, PospEnrollment.class));
                       // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Posp Enrollment : posp enrollment button in menu "), Constants.POSP), null);
                        break;
                    case R.id.nav_addposp:
                        fragment = new POSPListFragment();
                        getSupportActionBar().setTitle("Sub User List");
                        break;
//                    case R.id.nav_homeloanApplication:
//                        startActivity(new Intent(HomeActivity.this, HomeLoanApplyActivity.class));
//                        break;

                    case R.id.nav_crnpolicy:
                        //  startActivity(new Intent(HomeActivity.this, crnpolicyActivity.class));

                        if (userConstantEntity != null && userConstantEntity.getPBByCrnSearch() != null && !userConstantEntity.getPBByCrnSearch().equalsIgnoreCase("")) {

                            startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                    .putExtra("URL", userConstantEntity.getPBByCrnSearch())
                                    .putExtra("NAME", "" + "Search CRN")
                                    .putExtra("TITLE", "" + "Search CRN"));
                        } else {
                            Toast.makeText(HomeActivity.this, "Please contact to your RM", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.nav_leaddetail:
                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)

                                // .putExtra("URL", "http://bo.magicfinmart.com/motor-lead-details/" + String.valueOf(loginResponseEntity.getFBAId()))
                                .putExtra("URL", "" + userConstantEntity.getLeadDashUrl())
                                .putExtra("NAME", "" + "Lead DashBoard")
                                .putExtra("TITLE", "" + "Lead DashBoard"));
                        break;
                    case R.id.nav_gift:
                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", "http://labs.firsthive.com/magicfinmart/#!/redeem?fbaid=" + String.valueOf(loginResponseEntity.getFBAId()))
                                .putExtra("NAME", "" + "Gift Voucher")
                                .putExtra("TITLE", "" + "Gift Voucher"));
                        break;
/*
                    case R.id.nav_offlineQuotes:
                        //   startActivity(new Intent(HomeActivity.this, OfflineQuotesListActivity.class));
                        //   new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Offline Quotes : Offline Quotes button in menu "), Constants.OFFLINE_QUOTES), null);
                        break;
                    case R.id.nav_myBusiness:
                        startActivity(new Intent(HomeActivity.this, UnderConstructionActivity.class));
                        new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("My Business : My Business button in menu "), Constants.MY_BUSINESS), null);
                        break;
                    case R.id.nav_referFriend:
                        startActivity(new Intent(HomeActivity.this, UnderConstructionActivity.class));
                        new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Refer A Friend : Refer A Friend button in menu "), Constants.REFER), null);
                        break;
                    case R.id.nav_mps:
                        // DialogMPS();
                        showDialog();
                        new MasterController(HomeActivity.this).getMpsData(HomeActivity.this);

                        // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("MPS : MPS button in menu "), Constants.MPS), null);
                        //startActivity(new Intent(HomeActivity.this, UnderConstructionActivity.class));
                        break;
                    case R.id.nav_helpfeedback:
                        startActivity(new Intent(HomeActivity.this, HelpFeedBackActivity.class));
                        new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("HELP & FEEDBACK : HELP & FEEDBACK button in menu "), Constants.HELP), null);
                        break;
                    case R.id.nav_posptraining:
                        startActivity(new Intent(HomeActivity.this, com.datacomp.magicfinmart.pospapp.login.LoginActivity.class));
                        new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("POPS TRAINING : POPS TRAINING button in menu "), Constants.POSP_TRAINING), null);
                        break;
                    case R.id.nav_selfinspection:
                        startActivity(new Intent(HomeActivity.this, SplashScreen.class));
                        // startActivity(new Intent(HomeActivity.this, PreviewVideoActivity.class));
                        new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("INSPECTION : INSPECTION button in menu "), Constants.INSPECTION), null);
                        break;*/
                    case R.id.nav_whatsnew:
                        startActivity(new Intent(HomeActivity.this, WhatsNewActivity.class));
                       // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Whats New : Whats New button in menu "), Constants.WHATSNEW), null);
                        break;

                    case R.id.nav_cobrowser:
                       // dialogCoBrowser();
                        break;
                    case R.id.nav_franchise:
                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", "http://erp.rupeeboss.com/FM/Franchise_Agreement.pdf")
                                .putExtra("NAME", "Referral AGREEMENT")
                                .putExtra("TITLE", "Referral AGREEMENT"));
                       // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Whats New : Whats New button in menu "), Constants.WHATSNEW), null);

                        break;
                    case R.id.nav_raiseTicket:

                        if (userConstantEntity.getRaiseTickitEnabled().equals("0")) {
                            startActivity(new Intent(HomeActivity.this, RaiseTicketActivity.class));
                        } else {

                            startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                    .putExtra("URL", userConstantEntity.getRaiseTickitUrl() + "&mobile_no=" + userConstantEntity.getMangMobile()
                                            + "&UDID=" + userConstantEntity.getUserid())
                                    .putExtra("NAME", "RAISE_TICKET")
                                    .putExtra("TITLE", "RAISE TICKET"));
                        }
                       // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Raise Ticket : Raise Ticket button in menu "), Constants.WHATSNEW), null);

                        break;
//                    case R.id.nav_IncomePotential:
//                        startActivity(new Intent(HomeActivity.this, IncomePotentialActivity.class));
//
//                        break;
                    case R.id.nav_transactionhistory:
                        startActivity(new Intent(HomeActivity.this, nav_transactionhistoryActivity.class));

                        break;

                    case R.id.nav_contact:
                        startActivity(new Intent(HomeActivity.this, ContactLeadActivity.class));
                        break;
                    case R.id.nav_sendSmsTemplate:
                        startActivity(new Intent(HomeActivity.this, SendTemplateSmsActivity.class));
                        break;
                    case R.id.nav_logout:
                        //switch user clear
                        SharedPreferences preferences = getSharedPreferences(Constants.SWITCh_ParentDeatils_FINMART, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                            //  shortcutManager.removeAllDynamicShortcuts();
                        }
                        dialogLogout(HomeActivity.this);
                        break;

                    case R.id.nav_scan_vehicle:
                        // startActivity(new Intent(HomeActivity.this, ScanVehicleActivity.class));
                        startActivity(new Intent(HomeActivity.this, VehicleScanActivity.class));
                        break;
                    case R.id.nav_MessageCentre:
                        MessageCenter();
                        //   startActivity(new Intent(HomeActivity.this, messagecenteractivity.class));
                        break;
                    case R.id.nav_mybusiness_insurance:

                        startActivity(new Intent(HomeActivity.this, MyBusinessActivity.class));
                        break;
                    case R.id.nav_AppointmentLetter:
                        startActivity(new Intent(HomeActivity.this, POSP_certicate_appointment.class)
                                .putExtra("TYPE", "1"));
                        break;
                    case R.id.nav_Certificate:
                        startActivity(new Intent(HomeActivity.this, POSP_certicate_appointment.class)
                                .putExtra("TYPE", "0"));
                        break;

                    case R.id.nav_OtherLoan:
                        ConfirmOtherLoanProductsAlert();

                        break;

                    case R.id.nav_REQUEST:
                        ConfirmMoreServiceAlert();

                        break;
                    case R.id.nav_MYUtilities:
                        ConfirmnMyUtilitiesAlert();

                        break;

                    case R.id.nav_finbox:
                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", userConstantEntity.getFinboxurl())
                                .putExtra("NAME", "MY FINBOX")
                                .putExtra("TITLE", "MY FINBOX"));

                        break;
                    case R.id.nav_finperk:
                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", userConstantEntity.getFinperkurl())
                                .putExtra("NAME", "FINPERKS")
                                .putExtra("TITLE", "FINPERKS"));

                        break;
                    case R.id.nav_festivelink:
                        startActivity(new Intent(HomeActivity.this, festivelinkActivity.class)
                                .putExtra("URL", userConstantEntity.getFinperkurl())
                                .putExtra("NAME", "FESTIVE LINKS")
                                .putExtra("TITLE", "FESTIVE LINKS"));

                        break;

                    case R.id.nav_FreeCreditReport:
                        Utility.loadWebViewUrlInBrowser(HomeActivity.this,
                                "http://www.rupeeboss.com/equifax-finmart?fbaid="
                                        + String.valueOf(loginResponseEntity.getFBAId()));
                        break;

                    case R.id.nav_mybusinessLoan:
                      //  startActivity(new Intent(HomeActivity.this, MyBusiness_LoanActivity.class));
                        break;
                    case R.id.nav_emicalLoan:
                      //  startActivity(new Intent(HomeActivity.this, EmiCalcActivity.class));
                        break;
                    case R.id.nav_LeadSubmission:
                        startActivity(new Intent(HomeActivity.this, QuickLeadActivity.class));

                       // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Quick Lead tab on home page"), Constants.QUICK_LEAD), null);
                        MyApplication.getInstance().trackEvent(Constants.QUICK_LEAD, "Clicked", "Quick Lead tab on home page");
                        break;

                    case R.id.nav_disclosure:
                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", "file:///android_asset/Disclosure.html")
                                .putExtra("NAME", "DISCLOSURE")
                                .putExtra("TITLE", "DISCLOSURE"));
                        break;
                    case R.id.nav_policy:
                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", "https://www.policyboss.com/privacy-policy-policyboss-pro")
                                .putExtra("NAME", "PRIVACY POLICY")
                                .putExtra("TITLE", "PRIVACY POLICY"));
                        break;
                    default:
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();

                    return true;
                }
                return false;
            }
        });
        //endregion

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                hideNavigationItem();
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }


    private void setNavigationMenu(String language) {

        getSupportActionBar().setTitle(db.getLangData(language, "Title"));   // setTitle

        MenuItem nav_home, nav_language, nav_finbox, nav_finperk, nav_festivelink,
                nav_insert_contact, nav_myaccount_pro, nav_myaccount, nav_pospenrollment,
                nav_addposp, nav_raiseTicket, nav_changepassword, nav_Doc, nav_franchise,

                nav_AppointmentLetter, nav_Certificate, nav_TRANSACTIONS, nav_mybusiness_insurance,
                nav_transactionhistory, nav_MessageCentre, nav_crnpolicy,
                nav_LEADS, nav_contact, nav_generateLead, nav_scan_vehicle, nav_sharedata, nav_leaddetail,
                nav_sendSmsTemplate, nav_OtherLoan, nav_REQUEST, nav_MYUtilities, nav_whatsnew,
                nav_cobrowser, nav_logout, nav_FreeCreditReport, nav_mybusinessLoan, nav_emicalLoan, nav_LeadSubmission;


        Menu menu = navigationView.getMenu();


        nav_home = menu.findItem(R.id.nav_home);
        nav_language = menu.findItem(R.id.nav_language);
        nav_finbox = menu.findItem(R.id.nav_finbox);
        nav_finperk = menu.findItem(R.id.nav_finperk);
        nav_festivelink = menu.findItem(R.id.nav_festivelink);

        nav_insert_contact = menu.findItem(R.id.nav_insert_contact);
        nav_myaccount_pro = menu.findItem(R.id.nav_myaccount_pro);
        nav_myaccount = menu.findItem(R.id.nav_myaccount);
        nav_pospenrollment = menu.findItem(R.id.nav_pospenrollment);

        nav_addposp = menu.findItem(R.id.nav_addposp);
        nav_raiseTicket = menu.findItem(R.id.nav_raiseTicket);
        nav_changepassword = menu.findItem(R.id.nav_changepassword);
        nav_Doc = menu.findItem(R.id.nav_Doc);

        nav_franchise = menu.findItem(R.id.nav_franchise);
        nav_AppointmentLetter = menu.findItem(R.id.nav_AppointmentLetter);
        nav_Certificate = menu.findItem(R.id.nav_Certificate);
        nav_TRANSACTIONS = menu.findItem(R.id.nav_TRANSACTIONS);

        nav_mybusiness_insurance = menu.findItem(R.id.nav_mybusiness_insurance);
        nav_transactionhistory = menu.findItem(R.id.nav_transactionhistory);
        nav_MessageCentre = menu.findItem(R.id.nav_MessageCentre);
        nav_crnpolicy = menu.findItem(R.id.nav_crnpolicy);

        nav_LEADS = menu.findItem(R.id.nav_LEADS);
        nav_contact = menu.findItem(R.id.nav_contact);
        nav_generateLead = menu.findItem(R.id.nav_generateLead);
        nav_scan_vehicle = menu.findItem(R.id.nav_scan_vehicle);
        nav_sharedata = menu.findItem(R.id.nav_sharedata);
        nav_FreeCreditReport = menu.findItem(R.id.nav_FreeCreditReport);
        nav_mybusinessLoan = menu.findItem(R.id.nav_mybusinessLoan);
        nav_emicalLoan = menu.findItem(R.id.nav_emicalLoan);
        nav_LeadSubmission = menu.findItem(R.id.nav_LeadSubmission);
        nav_leaddetail = menu.findItem(R.id.nav_leaddetail);
        nav_sendSmsTemplate = menu.findItem(R.id.nav_sendSmsTemplate);
        nav_OtherLoan = menu.findItem(R.id.nav_OtherLoan);
        nav_REQUEST = menu.findItem(R.id.nav_REQUEST);

        nav_MYUtilities = menu.findItem(R.id.nav_MYUtilities);
        nav_whatsnew = menu.findItem(R.id.nav_whatsnew);
        nav_cobrowser = menu.findItem(R.id.nav_cobrowser);
        nav_logout = menu.findItem(R.id.nav_logout);


        if (!language.isEmpty()) {

            HashMap<String, MenuItem> menuItems = new HashMap<String, MenuItem>();
            menuItems.put("MenuHome", nav_home);
            menuItems.put("Switch Language", nav_language);
            menuItems.put("MenuMyFinbox", nav_finbox);
            menuItems.put("MenuFinperks", nav_finperk);
            menuItems.put("FESTIVE LINKS", nav_festivelink);

            menuItems.put("Finmart Business Contact", nav_insert_contact);
            menuItems.put("MenuMyAccount", nav_myaccount_pro);
            menuItems.put("MenuMyProfile", nav_myaccount);
            menuItems.put("Enrol as POSP", nav_pospenrollment);

            menuItems.put("", nav_addposp);
            menuItems.put("MenuRaiseTicket", nav_raiseTicket);
            menuItems.put("MenuChangePwd", nav_changepassword);
            menuItems.put("MenuMyDocs", nav_Doc);

            menuItems.put("MenuLoanAgr", nav_franchise);
            menuItems.put("MenuPospAppLtr", nav_AppointmentLetter);
            menuItems.put("MenuPospAppForm", nav_Certificate);
            menuItems.put("MenuMyTranTitle", nav_TRANSACTIONS);


            menuItems.put("MenuInsBus", nav_mybusiness_insurance);
            menuItems.put("MenuMyTransItm", nav_transactionhistory);
            menuItems.put("MenuMyMsgs", nav_MessageCentre);
            menuItems.put("MenuPolicyByCRN", nav_crnpolicy);

            menuItems.put("MenuMyLeads", nav_LEADS);
            menuItems.put("MenuLeadfromCont", nav_contact);
            menuItems.put("MenuMotLeads", nav_generateLead);
            menuItems.put("", nav_scan_vehicle);
            menuItems.put("MenuLoanLeads", nav_sharedata);


            menuItems.put("MenuLeadDash", nav_leaddetail);
            menuItems.put("MenuSms", nav_sendSmsTemplate);
            menuItems.put("MenuOthLoanProds", nav_OtherLoan);
            menuItems.put("MenuMorServ", nav_REQUEST);

            menuItems.put("MenuUtil", nav_MYUtilities);
            menuItems.put("Menuwtsnew", nav_whatsnew);
            menuItems.put("", nav_cobrowser);
            menuItems.put("MenuLogOut", nav_logout);


            for (String key : menuItems.keySet()) {
                //  String strTitle = db.getLangData(language, key.toString());

                if (!db.getLangData(language, key).equals("")) {
                    menuItems.get(key).setTitle(db.getLangData(language, key));
                    setLanguageFont(this, language, menuItems.get(key));
                }
            }

        }


    }

    public Map<String, String> loadMap() {
        Map<String, String> outputMap = new HashMap<>();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences(Constants.SWITCh_ParentDeatils_FINMART,
                Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString(mapKey, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    outputMap.put(key, jsonObject.get(key).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }



    private void addFinmartContact() {
        if (userConstantEntity.getFinmartwhatsappno() != null) {
            isContactFirstCall = Integer.parseInt(prefManager.getContactMsgFirst());
            if (isContactFirstCall == 0) {

                ConfirmInsertContactAlert("FINMART WHATSAPP CHAT", getResources().getString(R.string.FM_Contact) + " ", "");
            }
        }
    }

    private void MessageCenter() {

        String POSPNO = "" + userConstantEntity.getPospsendid();
        String msgurl = "" + userConstantEntity.getMessagesender();
        //   empCode="232";
        if (POSPNO.equals("5")) {
            startActivity(new Intent(HomeActivity.this, messagecenteractivity.class));


        } else {

            String ipaddress = "0.0.0.0";
            try {
                ipaddress = Utility.getMacAddress(this);
            } catch (Exception io) {
                ipaddress = "0.0.0.0";
            }

            String append = "&ip_address=" + ipaddress
                    + "&app_version=policyboss-" + Utility.getVersionName(this)
                    + "&device_id=" + Utility.getDeviceId(this);
            String fullmsgurl = msgurl + append;
            startActivity(new Intent(this, CommonWebViewActivity.class)
                    .putExtra("URL", fullmsgurl)
                    .putExtra("NAME", "Message Center")
                    .putExtra("TITLE", "Message Center"));

            //   incl_nav.setVisibility(View.VISIBLE);
            //  new PendingController(this).gettransactionhistory(empCode, "1", this);
        }
    }


    private void showMarketingPopup() {

        //region popup dashboard

        if (userConstantEntity != null) {
            if (userConstantEntity.getMarketinghomeenabled() != null &&
                    userConstantEntity.getMarketinghomeenabled().equals("1")) {

                int serverPopUpCount = Integer.parseInt(userConstantEntity.getMarketinghomemaxcount());
                int localPopupCount = Integer.parseInt(prefManager.getPopUpCounter());

                int serverId = Integer.parseInt(userConstantEntity.getMarketinghomepopupid());
                int localId = Integer.parseInt(prefManager.getPopUpId());
                if (localId == 0) {
                    prefManager.updatePopUpId("" + serverId);
                }

                if (localId == serverId) {
                    prefManager.updatePopUpId("" + serverId);
                    Log.d("COUNTER", "localId -" + localId + "counter - " + localPopupCount);
                    if (localPopupCount < serverPopUpCount) {
                        localPopupCount = localPopupCount + 1;
                        prefManager.updatePopUpCounter("" + localPopupCount);
                        openPopUp(ivProfile, userConstantEntity.getMarketinghometitle(), userConstantEntity.getMarketinghomedesciption(), "OK", true);

                    }

                } else {
                    prefManager.updatePopUpId("" + serverId);
                    prefManager.updatePopUpCounter("0");
                    localPopupCount = Integer.parseInt(prefManager.getPopUpCounter());
                    Log.d("COUNTER-", "localId -" + localId + "counter - " + localPopupCount);
                    if (localPopupCount < serverPopUpCount) {
                        localPopupCount = localPopupCount + 1;
                        prefManager.updatePopUpCounter("" + localPopupCount);
                        openPopUp(ivProfile, userConstantEntity.getMarketinghometitle(), userConstantEntity.getMarketinghomedesciption(), "OK", true);

                    }
                }

            }

        }

        //endregion
    }

    public void addDynamicMenu(MenuMasterResponse response) {
        menuMasterResponse = response;

        List<MenuItemEntity> list = response.getMasterData().getMenu();

        Menu menu = navigationView.getMenu();

        //remove menu item from group if exist
        for (int i = 1; i <= list.size() && (list.get(i - 1).getIsActive() == 1); i++) {
            int itemId = Integer.parseInt(list.get(i - 1).getSequence());
            itemId = (itemId * 100) + 1;
            menu.removeItem(itemId);
        }

        //add dynamic menu
        for (int i = 1; i <= list.size() && (list.get(i - 1).getIsActive() == 1); i++) {
            int itemId = Integer.parseInt(list.get(i - 1).getSequence());
            itemId = (itemId * 100) + 1;
            final MenuItem menuItem = menu.add(R.id.dynamic_menu, itemId, itemId, list.get(i - 1).getMenuname());
            Glide.with(this)
                    .load(list.get(i - 1).getIconimage())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            menuItem.setIcon(resource);
                        }
                    });
        }
    }

    public void selectHome() {

        if (prefManager.getLanguage().equals("")) {
            getSupportActionBar().setTitle("PolicyBoss Pro");
        } else {
            getSupportActionBar().setTitle(db.getLangData(prefManager.getLanguage(), "Title"));
        }
        Fragment fragment = new DashboardFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    // endregion


    private void init_headers() {

        View headerView = navigationView.getHeaderView(0);
        txtEntityName = (TextView) headerView.findViewById(R.id.txtEntityName);
        txtknwyour = (TextView) headerView.findViewById(R.id.txtknwyour);
        txtDetails = (TextView) headerView.findViewById(R.id.txtDetails);
        txtReferalCode = (TextView) headerView.findViewById(R.id.txtReferalCode);
        txtFbaID = (TextView) headerView.findViewById(R.id.txtFbaID);
        txtPospNo = (TextView) headerView.findViewById(R.id.txtPospNo);
        txtErpID = (TextView) headerView.findViewById(R.id.txtErpID);

        txtparentuser = (TextView) headerView.findViewById(R.id.txtparentuser);
        txtchilduser = (TextView) headerView.findViewById(R.id.txtchilduser);
        lstswitchuser = (LinearLayout) headerView.findViewById(R.id.lstswitchuser);
        lstswitchChild_user = (LinearLayout) headerView.findViewById(R.id.lstswitchChild_user);

        ivProfile = (ImageView) headerView.findViewById(R.id.ivProfile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent shareIntent = new Intent(HomeActivity.this, MyAccountActivity.class);
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(ivProfile, "profileTransition");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, pairs);
                    startActivity(shareIntent, options.toBundle());
                } else {
                    startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));
                }

            }
        });

        ivCancel = (ImageView) headerView.findViewById(R.id.ivCancel);


        txtknwyour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebViewPopUp(txtFbaID, userConstantEntity.getNotificationpopupurl(), true, "");
                // openWebViewPopUp(txtFbaID, "http://qa.mgfm.in/images/rbasalesmaterial/new.html", true, HomeActivity.this);//For QA only
            }
        });


        lstswitchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNavDrawerOpen()) closeNavDrawer();
                startActivityForResult(new Intent(HomeActivity.this, SwitchUserActivity.class), 10);
            }
        });

        txtEntityName.setText("Ver." + versionNAme);

        if (loginResponseEntity != null) {

            txtDetails.setText("" + loginResponseEntity.getFullName());
            txtFbaID.setText("Fba Id - " + loginResponseEntity.getFBAId());
            txtReferalCode.setText("Referral Code - " + loginResponseEntity.getReferer_code());

            switchUserBinding();

        } else {
            txtDetails.setText("");
            txtFbaID.setText("Fba Id - ");
            txtReferalCode.setText("Referral Code - ");
        }
        if (userConstantEntity != null) {

            try {
                txtPospNo.setText("Posp No - " + userConstantEntity.getPospselfid());
                txtErpID.setText("Erp Id - " + userConstantEntity.getERPID());
                Glide.with(HomeActivity.this)
                        .load(userConstantEntity.getLoansendphoto())
                        .placeholder(R.drawable.circle_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .override(64, 64)
                        .transform(new CircleTransform(HomeActivity.this)) // applying the image transformer
                        .into(ivProfile);
            } catch (Exception e) {

            }

        } else {
            try {
                txtPospNo.setText("");
                txtErpID.setText("");
                Glide.with(HomeActivity.this)
                        .load(R.drawable.finmart_user_icon)
                        .placeholder(R.drawable.circle_placeholder)
                        .transform(new CircleTransform(HomeActivity.this)) // applying the image transformer
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .override(64, 64)
                        .into(ivProfile);
            } catch (Exception e) {

            }

        }


    }


    private void addSwitchUserView() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_switch_user, null);
        TextView txtUserName = view.findViewById(R.id.txtSwitchUserName);
        Button btnSwitchLogout = view.findViewById(R.id.btnSwitchLogout);
        llSwitchUser.addView(view);

    }

    private void switchUserBinding() {
        //region Switch user Binding

        Map<String, String> outputMap = loadMap();
        if (outputMap != null && outputMap.size() > 0) {
            lstswitchuser.setVisibility(View.GONE);
            lstswitchChild_user.setVisibility(View.VISIBLE);

            String mystring = new String("Parent :- " + outputMap.get("Parent_name"));
            SpannableString content = new SpannableString(mystring);
            content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
            txtparentuser.setText(content);

            String currentChild = outputMap.get("Child_name");

            txtchilduser.setText(currentChild);

            //region add view for switch user
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.layout_switch_user, null);
            TextView txtUserName = view.findViewById(R.id.txtSwitchUserName);
            Button btnSwitchLogout = view.findViewById(R.id.btnSwitchLogout);

            txtUserName.setText("Logged in with " + currentChild);
            btnSwitchLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNavDrawerOpen()) {
                        closeNavDrawer();
                    }

                    LoginRequestEntity loginRequestEntity = new LoginRequestEntity();
                    Map<String, String> outputMap = loadMap();
                    if (outputMap != null && outputMap.size() > 0) {

                        loginRequestEntity.setUserName(outputMap.get("Parent_UID"));
                        loginRequestEntity.setPassword(outputMap.get("Parent_PWD"));
                    }

                    loginRequestEntity.setDeviceId("" + new ReadDeviceID(HomeActivity.this).getAndroidID());
                    loginRequestEntity.setTokenId(prefManager.getToken());
                    loginRequestEntity.setIsChildLogin("Y");

                    SharedPreferences preferences = getSharedPreferences(Constants.SWITCh_ParentDeatils_FINMART, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    //  new PrefManager(HomeActivity.this).clearAll();

                    new DBPersistanceController(HomeActivity.this).clearSwitchUser();
                    showDialog();
                    new LoginController(HomeActivity.this).login(loginRequestEntity, HomeActivity.this);
                }
            });

            llSwitchUser.removeAllViews();
            llSwitchUser.addView(view);

            //endregion


        } else {
            if (loginResponseEntity.getIsUidLogin().equals("Y")) {
                lstswitchuser.setVisibility(View.VISIBLE);
                lstswitchChild_user.setVisibility(View.GONE);
            } else {
                lstswitchuser.setVisibility(View.GONE);
                lstswitchChild_user.setVisibility(View.GONE);
            }

        }

        //endregion
    }


    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            dialogExit();
            //super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void getNotificationAction() {

        // region Activity Open Using Notification

        if (getIntent().getExtras() != null) {

            // For getting User Click Action
            if (getIntent().getExtras().getParcelable(Utility.PUSH_NOTIFY) != null) {
                NotifyEntity notifyEntity = getIntent().getExtras().getParcelable(Utility.PUSH_NOTIFY);
                String MESSAGEID = notifyEntity.getMessage_id();

                new RegisterController(HomeActivity.this).getUserClickActionOnNotification(MESSAGEID, null);
            }
            // step1: boolean verifyLogin = prefManager.getIsUserLogin();
            // region verifyUser : when user logout and when Apps in background
            if (loginResponseEntity == null) {

                NotifyEntity notifyEntity = getIntent().getExtras().getParcelable(Utility.PUSH_NOTIFY);
                if (notifyEntity == null) {
                    return;
                }

                prefManager.setPushNotifyPreference(notifyEntity);
                prefManager.setSharePushType(notifyEntity.getNotifyFlag());

                Intent intent = new Intent(this, SplashScreenActivity.class);
                startActivity(intent);
                finish();


            }
            //endregion

            //  region step2: For Notification come via Login for user credential  (step2 perform after step1)
            else if (getIntent().getStringExtra(Utility.PUSH_LOGIN_PAGE) != null) {
                String pushLogin = getIntent().getStringExtra(Utility.PUSH_LOGIN_PAGE);
                if (pushLogin.equals("555")) {

                    NotifyEntity notifyEntity;
                    String type = "", title = "", body = "", web_url = "", web_title = "", web_name = "";
                    if (prefManager.getPushNotifyPreference() != null) {
                        notifyEntity = prefManager.getPushNotifyPreference();

                        type = notifyEntity.getNotifyFlag();
                        title = notifyEntity.getTitle();
                        body = notifyEntity.getBody();
                        web_url = notifyEntity.getWeb_url();
                        web_title = notifyEntity.getWeb_title();

                    }

                    prefManager.clearNotification();

                    if (type.matches("NL")) {
                        Intent intent = new Intent(this, NotificationActivity.class);
                        startActivity(intent);

                    } else if (type.matches("MSG")) {

                        startActivity(new Intent(HomeActivity.this, NotificationSmsActivity.class)
                                .putExtra("NOTIFY_TITLE", title)
                                .putExtra("NOTIFY_BODY", body));

                    } else if (type.matches("WB")) {

                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", web_url)
                                .putExtra("NAME", web_name)
                                .putExtra("TITLE", web_title));

                    }
                }

            }
            //endregion

            // region user already logged in and app in forground
            else if (getIntent().getExtras().getParcelable(Utility.PUSH_NOTIFY) != null) {
                NotifyEntity notificationEntity = getIntent().getExtras().getParcelable(Utility.PUSH_NOTIFY);

                if (notificationEntity.getWeb_url() != null) {

                    navigateViaNotification(notificationEntity.getNotifyFlag(), notificationEntity.getWeb_url(), notificationEntity.getWeb_title());

                }
            }

            //endregion
        }

        //endregion
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_push_notification);
        final MenuItem menuNewItem = menu.findItem(R.id.action_new);

        //  SearchView actionView = (SearchView) menuItem.getActionView();

        View actionView = MenuItemCompat.getActionView(menuItem);
        View actionViewnew = MenuItemCompat.getActionView(menuNewItem);

        textNotifyItemCount = (TextView) actionView.findViewById(R.id.notify_badge);
        textNotifyItemCount.setVisibility(View.GONE);

        ImageView imgNew = (ImageView) actionViewnew.findViewById(R.id.imgNew);

        Glide.with(HomeActivity.this).
                load(R.drawable.newicon)
                .asGif()
                .crossFade()
                .into(imgNew);

        int PushCount = prefManager.getNotificationCounter();

        if (PushCount == 0) {
            textNotifyItemCount.setVisibility(View.GONE);
        } else {
            textNotifyItemCount.setVisibility(View.VISIBLE);
            textNotifyItemCount.setText("" + String.valueOf(PushCount));
        }

        actionViewnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebViewPopUp(txtFbaID, userConstantEntity.getNotificationpopupurl(), true, "");
            }


        });

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }


        });


        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {

            case R.id.action_call:
                if (userConstantEntity.getMangMobile() != null) {


                    if (userConstantEntity.getManagName() != null) {
                        // ConfirmAlert("Manager Support", getResources().getString(R.string.RM_Calling) + " " + userConstantEntity.getManagName());

                        if (callingDetailDialog != null && callingDetailDialog.isShowing()) {

                            return false;
                        } else {
                            showDialog();
                            new RegisterController(this).getUserCallingDetail(String.valueOf(loginResponseEntity.getFBAId()), this);
                        }
                    }

                }


                break;
            case R.id.action_push_notification:
                intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;


        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof LoginResponse) {
            if (response.getStatusNo() == 0) {

                // prefManager.setIsUserLogin(true);

                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            } else {
                Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (response instanceof MpsResponse) {

            if (response.getStatusNo() == 0) {

                prefManager.removeMps();
                prefManager.setMPS(((MpsResponse) response).getMasterData());
                if (loginResponseEntity.getIsFirstLogin() == 1) {
                    DialogMPS();
                } else {
                    DialogMPS();
                }

            }
        } else if (response instanceof MyAcctDtlResponse) {
            if (response.getStatusNo() == 0) {
                if (((MyAcctDtlResponse) response).getMasterData().get(0) != null) {
                    db.updateMyAccountData(((MyAcctDtlResponse) response).getMasterData().get(0));
                }
            }
        } else if (response instanceof UserConstatntResponse) {
            if (response.getStatusNo() == 0) {
                if (((UserConstatntResponse) response).getMasterData() != null) {
                    //db.updateUserConstatntData(((UserConstatntResponse) response).getMasterData());
                    userConstantEntity = ((UserConstatntResponse) response).getMasterData();
                    init_headers();

                    if (prefManager.getPopUpCounter().equals("0")) {
                        showMarketingPopup();
                    }

                    //Notification Url :-1 November
                    int localNotificationenable = Integer.parseInt(prefManager.getNotificationsetting());

                    if (userConstantEntity.getNotificationpopupurltype().toUpperCase().equals("SM")) {
                        if (!userConstantEntity.getNotificationpopupurl().equals("")) {
                            if (prefManager.getIsSeasonal()) {
                                openWebViewPopUp(txtFbaID, userConstantEntity.getNotificationpopupurl(), true, "");
                                prefManager.setIsSeasonal(false);
                            }
                        }
                    } else if (localNotificationenable == 0) {
                        // prefManager.updatePopUpId("" + serverId);
                        if (!userConstantEntity.getNotificationpopupurl().equals("")) {
                            if (prefManager.getIsSeasonal()) {
                                openWebViewPopUp(txtFbaID, userConstantEntity.getNotificationpopupurl(), true, "");
                                prefManager.setIsSeasonal(false);
                            }
                        }

                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // createLaunchAppIconMenu((UserConstatntResponse) response);
                }

            }
        } else if (response instanceof ConstantsResponse) {
            constantEntity = ((ConstantsResponse) response).getMasterData();
            if (response.getStatusNo() == 0) {

                //region check for new version
                int serverVersionCode = Integer.parseInt(((ConstantsResponse) response).getMasterData().getVersionCode());
                if (pinfo != null && pinfo.versionCode < serverVersionCode) {
                    forceUpdate = Integer.parseInt(((ConstantsResponse) response).getMasterData().getIsForceUpdate());
                    if (forceUpdate == 1) {
                        // forced update app
                        openPopUp(navigationView, "UPDATE", "New version available on play store, Please update.", "OK", false);
                    } else {
                        // aap with less version but not forced update
                        if (prefManager.getUpdateShown()) {
                            prefManager.setIsUpdateShown(false);
                            openPopUp(navigationView, "UPDATE", "New version available on play store, Please update.", "OK", true);
                        }
                    }

                    if (new DBPersistanceController(this).getUserData().getIsFirstLogin() == 1) {
                        for (Fragment frg :
                                getSupportFragmentManager().getFragments()) {

                            if (frg instanceof MPSFragment || frg instanceof KnowMoreMPSFragment) {
                                if (!frg.isVisible()) {
                                    //DialogMPS();
                                }
                            }
                        }
                    }

                } else if (((ConstantsResponse) response).getMasterData().
                        getMPSStatus().toLowerCase().equalsIgnoreCase("p")) {
                }
                //endregion
            }
        } else if (response instanceof MenuMasterResponse) {
            if (response.getStatusNo() == 0) {
                prefManager.storeMenuDashboard((MenuMasterResponse) response);
                addDynamicMenu((MenuMasterResponse) response);
            }
        } else if (response instanceof UserCallingResponse) {
            if (response.getStatusNo() == 0) {

                CallingDetailsPopUp(((UserCallingResponse) response).getMasterData());
            }
        } else if (response instanceof MultiLangResponse) {

            if (response.getStatusNo() == 0) {

                showMultiLanguage();
            }
        } else if (response instanceof ProductURLShareResponse) {

            if (response.getStatusNo() == 0) {

                if (((ProductURLShareResponse) response).getMasterData() != null) {
                    ProductURLShareEntity shareEntity = ((ProductURLShareResponse) response).getMasterData();
                    if (dashboardShareEntity != null) {
                        datashareList(HomeActivity.this, dashboardShareEntity.getTitle(), shareEntity.getMsg(), shareEntity.getUrl());
                    }
                }
            }
        }

    }


    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        //openPopUp(toolbar, "Message", "" + t.getMessage(), "OK", true);
    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {
        //dialog.cancel();
        if (view.getId() == navigationView.getId()) {
            openAppMarketPlace();
        } else if (view.getId() == ivProfile.getId()) {
            redirectToActivity();
            dialog.cancel();
        } else if (view.getId() == drawerLayout.getId()) {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);

        }
    }

    private void redirectToActivity() {

        if (userConstantEntity != null && userConstantEntity.getMarketinghometransfertype() != null) {
            int id = Integer.parseInt(userConstantEntity.getMarketinghometransfertype());
            switch (id) {
                case 1:
                    startActivity(new Intent(this, InputQuoteBottmActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(this, BikeAddQuoteActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(this, HealthQuoteBottomTabsActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(this, CompareTermActivity.class));
                    break;
                case 5:
                 //   startActivity(new Intent(this, HLMainActivity.class));
                    break;
                case 6:
             //       startActivity(new Intent(this, PLMainActivity.class));
                    break;
                case 7:
           //         startActivity(new Intent(this, LAPMainActivity.class));
                    break;
                case 8:
          //          startActivity(new Intent(this, BLMainActivity.class));
                    break;
                case 9:
                    startActivity(new Intent(this, KnowledgeGuruActivity.class));
                    break;
                case 10:
                    startActivity(new Intent(this, SalesMaterialActivity.class));
                    break;
                case 11:
                    startActivity(new Intent(this, PendingCasesActivity.class));
                    break;
                case 12:
                    // startActivity(new Intent(this, RaiseTicketActivity.class));
                    if (userConstantEntity.getRaiseTickitEnabled().equals("0")) {
                        startActivity(new Intent(HomeActivity.this, RaiseTicketActivity.class));
                    } else {

                        startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                                .putExtra("URL", userConstantEntity.getRaiseTickitUrl() + "&mobile_no=" + userConstantEntity.getMangMobile()
                                        + "&UDID=" + userConstantEntity.getUserid())
                                .putExtra("NAME", "RAISE_TICKET")
                                .putExtra("TITLE", "RAISE TICKET"));
                    }
                    break;
                case 13:
                    startActivity(new Intent(this, PospEnrollment.class));
                    break;
                case 14:
                    startActivity(new Intent(this, HealthCheckUpPlansActivity.class));
                    break;
                case 1111:
                    if (userConstantEntity != null && userConstantEntity.getMarketinghomeurl() != null) {
                        startActivity(new Intent(this, CommonWebViewActivity.class)
                                .putExtra("URL", userConstantEntity.getMarketinghomeurl())
                                .putExtra("NAME", userConstantEntity.getMarketinghometitle())
                                .putExtra("TITLE", userConstantEntity.getMarketinghometitle()));
                    }
                    break;

            }
        }
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        if (view.getId() == navigationView.getId()) {
            if (forceUpdate == 1) {

            } else {
                dialog.cancel();
            }
        } else if (view.getId() == ivProfile.getId()) {
            dialog.cancel();
        } else {
            dialog.cancel();
        }

    }

    private void openAppMarketPlace() {
        final String appPackageName = this.getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
      //  new TrackingController(this).sendData(new TrackingRequestEntity(new TrackingData("Update : User open marketplace  "), "Update"), null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // will be upadte everytyime user comes on dashboard
        toolbar.setTitle("PolicyBoss Pro");

        if (loginResponseEntity != null) {
            new MasterController(this).getConstants(this);
            new MasterController(this).geUserConstant(1, this);
        }
        LocalBroadcastManager.getInstance(HomeActivity.this).registerReceiver(mHandleMessageReceiver, new IntentFilter(Utility.PUSH_BROADCAST_ACTION));

        LocalBroadcastManager.getInstance(HomeActivity.this)
                .registerReceiver(mHandleMessageReceiver, new IntentFilter(Utility.USER_PROFILE_ACTION));


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandleMessageReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT", "Activity");
        if (requestCode == Constants.REQUEST_CODE) {
            if (data != null) {
                int Counter = prefManager.getNotificationCounter();
                textNotifyItemCount.setText("" + Counter);
                textNotifyItemCount.setVisibility(View.GONE);

            }

        } else if (requestCode == Constants.SWITCH_USER_REQUEST_CODE) {
            if (data != null) {
                //switchUserBinding();
                db = new DBPersistanceController(this);
                loginResponseEntity = db.getUserData();
                // init_headers();
            }

        }
    }

    private String getAppendURL(UserConstatntResponse response, String productID) {

        Map<String, String> mLoadMap = loadMap();
        String parent_ssid = "";
        if (mLoadMap.size() > 0) {
            parent_ssid = mLoadMap.get("Parent_POSPNo");
        }

        String ipAddress = "0.0.0.0";
        try {
            ipAddress = Utility.getMacAddress(this);
        } catch (Exception io) {
            ipAddress = "0.0.0.0";
        }

        String append = "&ip_address=" + ipAddress + "&mac_address=" + ipAddress
                + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                + "&device_id=" + Utility.getDeviceId(this)
                + "&product_id=" + productID
                + "&login_ssid=" + parent_ssid;

        return append;
    }

    /*
    Car Insurance
    TW insurance
    Health Insurance
    Cv Insurance
    Life Insurance*/

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createLaunchAppIconMenu(UserConstatntResponse response) {

        UserConstantEntity userConstantEntity = response.getMasterData();

        String fourWheelerUrl = userConstantEntity.getFourWheelerUrl();
        fourWheelerUrl = fourWheelerUrl + getAppendURL(response, "1"); // four wheeler
        Intent fourWheelerIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, CommonWebViewActivity.class)
                .putExtra("URL", fourWheelerUrl)
                .putExtra("NAME", "Motor Insurance")
                .putExtra("TITLE", "Motor Insurance");

        String twoWheelerUrl = userConstantEntity.getTwoWheelerUrl();
        twoWheelerUrl = twoWheelerUrl + getAppendURL(response, "10"); // four wheeler
        Intent twoWheelerIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, CommonWebViewActivity.class)
                .putExtra("URL", twoWheelerUrl)
                .putExtra("NAME", "Two wheeler Insurance")
                .putExtra("TITLE", "Two wheeler Insurance");

        String healthWheelerURL = userConstantEntity.getHealthurl();
        healthWheelerURL = healthWheelerURL + getAppendURL(response, "2"); // four wheeler
        Intent healthWheelerIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, CommonWebViewActivity.class)
                .putExtra("URL", healthWheelerURL)
                .putExtra("NAME", "Health Insurance")
                .putExtra("TITLE", "Health Insurance");

        String cvURL = userConstantEntity.getCVUrl();
        cvURL = cvURL + getAppendURL(response, "12"); // cv wheeler
        Intent cvIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, CommonWebViewActivity.class)
                .putExtra("URL", cvURL)
                .putExtra("NAME", "CV Insurance")
                .putExtra("TITLE", "CV Insurance");

        Intent termIntent = new Intent(Intent.ACTION_VIEW, Uri.EMPTY, this, TermSelectionActivity.class);

        //18

        List<ShortcutInfo> listShortCutInfo = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
                shortcutManager = getSystemService(ShortcutManager.class);
            }

            ShortcutInfo siFourwheeler = null, siTwowheeler = null, siHealthInsurance = null, siCVInsurance = null, siTermInsurance = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                siFourwheeler = new ShortcutInfo.Builder(this, "motor")
                        .setShortLabel("Motor Insurance")
                        .setIcon(Icon.createWithResource(this, R.drawable.car_1))
                        .setIntent(fourWheelerIntent)
                        .build();

                siTwowheeler = new ShortcutInfo.Builder(this, "two_wheeler")
                        .setShortLabel("Two wheeler Insurance")
                        .setIcon(Icon.createWithResource(this, R.drawable.car_1))
                        .setIntent(twoWheelerIntent)
                        .build();

                siHealthInsurance = new ShortcutInfo.Builder(this, "health_insurance")
                        .setShortLabel("Health Insurance")
                        .setIcon(Icon.createWithResource(this, R.drawable.car_1))
                        .setIntent(healthWheelerIntent)
                        .build();

                siCVInsurance = new ShortcutInfo.Builder(this, "cv_insurance")
                        .setShortLabel("CV Insurance")
                        .setIcon(Icon.createWithResource(this, R.drawable.car_1))
                        .setIntent(cvIntent)
                        .build();

                siTermInsurance = new ShortcutInfo.Builder(this, "term_insurance")
                        .setShortLabel("Term Insurance")
                        .setIcon(Icon.createWithResource(this, R.drawable.car_1))
                        .setIntent(termIntent)
                        .build();
            }

            listShortCutInfo.add(siFourwheeler);
            listShortCutInfo.add(siTwowheeler);
            listShortCutInfo.add(siHealthInsurance);
            listShortCutInfo.add(siCVInsurance);
            // listShortCutInfo.add(siTermInsurance);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                shortcutManager.addDynamicShortcuts(listShortCutInfo);
            }


        }

    }

    public void hideNavigationItem() {
        Menu nav_Menu = navigationView.getMenu();

        if (db.isHideLoan()) {
            // nav_Menu.removeGroup(R.id.nav_loan);
            nav_Menu.findItem(R.id.nav_loan).setVisible(false);
        }


        //todo : check key from userconstant to hide add posp
        if (userConstantEntity != null && userConstantEntity.getAddPospVisible() != null && !userConstantEntity.getAddPospVisible().equals("")) {
            int visibility = Integer.parseInt(userConstantEntity.getAddPospVisible());
            MenuItem menuItem = nav_Menu.findItem(R.id.nav_addposp);
            if (menuItem != null) {
                if (visibility == 1)
                    menuItem.setVisible(true);
                else
                    menuItem.setVisible(false);
            }
        }


        if (prefManager.getFOSUser() != "") {
            String FOS_INFOMATION = prefManager.getFOSUser();

            if (FOS_INFOMATION.equals("Y")) {
                nav_Menu.findItem(R.id.nav_addposp).setVisible(false);
            }
        }


        //todo : check key from userconstant to hide my business
        if (userConstantEntity != null) {

            int visibility = Integer.parseInt(userConstantEntity.getShowmyinsurancebusiness());
            if (visibility > 0)
                nav_Menu.findItem(R.id.nav_mybusiness_insurance).setVisible(true);
            else
                nav_Menu.findItem(R.id.nav_mybusiness_insurance).setVisible(false);


            //todo : check key from userconstant to hide posp enrollment
            if (userConstantEntity.getEnableenrolasposp() != null && !userConstantEntity.getEnableenrolasposp().equals("")) {
                if (Integer.parseInt(userConstantEntity.getEnableenrolasposp()) == 1)
                    nav_Menu.findItem(R.id.nav_pospenrollment).setVisible(true);
                else
                    nav_Menu.findItem(R.id.nav_pospenrollment).setVisible(false);
            }

            if (userConstantEntity.getCobrowserisactive() != null
                    && !userConstantEntity.getCobrowserisactive().equals("")) {

                if (Integer.parseInt(userConstantEntity.getCobrowserisactive()) == 1)
                    nav_Menu.findItem(R.id.nav_cobrowser).setVisible(true);
                else
                    nav_Menu.findItem(R.id.nav_cobrowser).setVisible(false);

            }

            if (userConstantEntity.getEnablesynccontact() != null && !userConstantEntity.getEnablesynccontact().equals("")) {
                //int visibilitySync = userConstantEntity.getEnablesynccontact();
                if (userConstantEntity.getEnablesynccontact().equals("Y"))
                    nav_Menu.findItem(R.id.nav_contact).setVisible(true);
                else
                    nav_Menu.findItem(R.id.nav_contact).setVisible(false);
            }

            //Date 02/11/2020 by nilesh
            if (userConstantEntity.getGenerateMotorLeadsEnabled() != null && !userConstantEntity.getGenerateMotorLeadsEnabled().equals("")) {

                if (userConstantEntity.getGenerateMotorLeadsEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_generateLead).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_generateLead).setVisible(true);
                }
            }

          /*  if (userConstantEntity.getPolicyByCRNEnabled() != null && !userConstantEntity.getPolicyByCRNEnabled().equals("")) {

              if (userConstantEntity.getPolicyByCRNEnabled().equals("0")) {
                   nav_Menu.findItem(R.id.nav_crnpolicy).setVisible(false);
               } else {
                    nav_Menu.findItem(R.id.nav_crnpolicy).setVisible(true);
              }
            }*/

            //region comment policyBosspro finbox and finpiece
            /*
            if (userConstantEntity.getFinboxEnabled() != null && !userConstantEntity.getFinboxEnabled().equals("")) {

                if (userConstantEntity.getFinboxEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_finbox).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_finbox).setVisible(true);
                }
            }

            if (userConstantEntity.getFinperksEnabled() != null && !userConstantEntity.getFinperksEnabled().equals("")) {

                if (userConstantEntity.getFinperksEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_finperk).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_finperk).setVisible(true);
                }
            }
            */
            //endregion

            if (userConstantEntity.getPospletterEnabled() != null && !userConstantEntity.getPospletterEnabled().equals("")) {

                if (userConstantEntity.getPospletterEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_AppointmentLetter).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_AppointmentLetter).setVisible(true);
                }
            }

            if (userConstantEntity.getPospappformEnabled() != null && !userConstantEntity.getPospappformEnabled().equals("")) {

                if (userConstantEntity.getPospappformEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_Certificate).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_Certificate).setVisible(true);
                }
            }

            if (userConstantEntity.getMyTransactionsEnabled() != null && !userConstantEntity.getMyTransactionsEnabled().equals("")) {

                if (userConstantEntity.getMyTransactionsEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_transactionhistory).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_transactionhistory).setVisible(true);
                }
            }

            if (userConstantEntity.getMyMessagesEnabled() != null && !userConstantEntity.getMyMessagesEnabled().equals("")) {

                if (userConstantEntity.getMyTransactionsEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_MessageCentre).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_MessageCentre).setVisible(true);
                }
            }

            if (userConstantEntity.getSmsTemplatesEnabled() != null && !userConstantEntity.getSmsTemplatesEnabled().equals("")) {

                if (userConstantEntity.getSmsTemplatesEnabled().equals("0")) {
                    nav_Menu.findItem(R.id.nav_sendSmsTemplate).setVisible(false);
                } else {
                    nav_Menu.findItem(R.id.nav_sendSmsTemplate).setVisible(true);
                }
            }

        } else {
            nav_Menu.findItem(R.id.nav_mybusiness_insurance).setVisible(false);
            nav_Menu.findItem(R.id.nav_pospenrollment).setVisible(false);
            nav_Menu.findItem(R.id.nav_cobrowser).setVisible(false);
            nav_Menu.findItem(R.id.nav_contact).setVisible(false);
            nav_Menu.findItem(R.id.nav_generateLead).setVisible(false);
        }


        //Attendance
//        if (loginResponseEntity.getIsUidLogin().equals("Y")) {
//            //visible attendance
//            nav_Menu.findItem(R.id.nav_attendance).setVisible(true);
//        } else {
//            //hide attendance
//            nav_Menu.findItem(R.id.nav_attendance).setVisible(false);
//        }

        //init_headers();


    }


    public void ConfirmAlert(String Title, String strBody) {
        try {


//            Intent intentCalling = new Intent(Intent.ACTION_DIAL);
//            intentCalling.setData(Uri.parse("tel:" + userConstantEntity.getMangMobile()));
//            startActivity(intentCalling);


            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle(Title);

            builder.setMessage(strBody);
            String positiveText = "Call";
            String NegativeText = "Share";
            String NeutralText = "Cancel";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Intent intentCalling = new Intent(Intent.ACTION_DIAL);
                            intentCalling.setData(Uri.parse("tel:" + userConstantEntity.getMangMobile()));
                            startActivity(intentCalling);

                        }
                    });

            builder.setNegativeButton(NegativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (userConstantEntity.getMangEmail() != null) {
                                // composeEmail(userConstantEntity.getMangEmail(), "");
                                shareMailSmsList(HomeActivity.this, "", "Dear Sir/Madam,", userConstantEntity.getMangEmail().toString(), userConstantEntity.getMangMobile().toString());

                            }
                        }
                    });

            builder.setNeutralButton(NeutralText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            final AlertDialog dialog = builder.create();
            //  dialog.setCancelable(false);
            //  dialog.setCanceledOnTouchOutside(false);

            dialog.show();

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.uvv_green));
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.holo_red_dark));
            dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.button_color));

        } catch (Exception ex) {
            Toast.makeText(this, "Please try again..", Toast.LENGTH_SHORT).show();
        }
    }


    public void CallingDetailsPopUp(List<UserCallingEntity> lstCallingDetail) {
        if (callingDetailDialog != null && callingDetailDialog.isShowing()) {

            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        TextView txtHdr, txtMessage;
        ImageView ivCross;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.calling_user_detail_dialog, null);

        builder.setView(dialogView);
        callingDetailDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtHdr = dialogView.findViewById(R.id.txtHdr);
        txtMessage = dialogView.findViewById(R.id.txtMessage);
        RecyclerView rvCalling = dialogView.findViewById(R.id.rvCalling);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        rvCalling.setLayoutManager(new LinearLayoutManager(this));
        rvCalling.setHasFixedSize(true);
        rvCalling.setNestedScrollingEnabled(false);
        callingDetailAdapter = new CallingDetailAdapter(HomeActivity.this, lstCallingDetail);
        rvCalling.setAdapter(callingDetailAdapter);
        rvCalling.setVisibility(View.VISIBLE);
        txtMessage.setText(getResources().getString(R.string.RM_Calling));


        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingDetailDialog.dismiss();

            }
        });
        callingDetailDialog.setCancelable(false);
        callingDetailDialog.show();

    }

    //region mps dialog

    public void DialogMPS() {
        if (prefManager.getMps() != null) {
            AlertDialog.Builder mpsAlertBuilder;
            mpsAlertBuilder = new AlertDialog.Builder(this);
            mpsAlertBuilder.setCancelable(true);
            // builder.setTitle("PREMIUM DETAIL");
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_dialog_mps, null);
            mpsAlertBuilder.setView(view);

            if (mpsDialog == null) {
                mpsDialog = mpsAlertBuilder.create();
            }

            TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);
            TextView txtKnowMore = (TextView) view.findViewById(R.id.txtKnowMore);
            TextView txtGetMPS = (TextView) view.findViewById(R.id.txtGetMPS);
            TextView txtLater = (TextView) view.findViewById(R.id.txtLater);

            txtDesc.setText("");
            txtDesc.append(getResources().getString(R.string.mps_popup_text));

            String amount = " \u20B9" + prefManager.getMps().getTotalAmt() + "/- ";
            SpannableString ss1 = new SpannableString(amount);
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            String normalText = getResources().getString(R.string.mps_popup_text);
            txtDesc.setText("");
            txtDesc.append(normalText);
            txtDesc.append(ss1);
            txtDesc.append("(Incl. GST) only");

            txtLater.setTag(R.id.txtLater, mpsDialog);
            txtGetMPS.setTag(R.id.txtGetMPS, mpsDialog);
            txtKnowMore.setTag(R.id.txtKnowMore, mpsDialog);

            txtKnowMore.setOnClickListener(onClickListener);
            txtGetMPS.setOnClickListener(onClickListener);
            txtLater.setOnClickListener(onClickListener);

            if (!mpsDialog.isShowing())
                mpsDialog.show();
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txtKnowMore:
                    ((AlertDialog) v.getTag(R.id.txtKnowMore)).dismiss();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, new KnowMoreMPSFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.txtGetMPS:
                    ((AlertDialog) v.getTag(R.id.txtGetMPS)).dismiss();
                    redirectMPS();

                    break;
                case R.id.txtLater:
                    ((AlertDialog) v.getTag(R.id.txtLater)).dismiss();
                    break;

            }
        }
    };

    public void redirectMPS() {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new MPSFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onGrantButtonClick(View view) {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);

    }
    //endregion


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("COUNTER", "new intent");
    }

    public void shareMailSmsList(Context context, String prdSubject, String prdDetail, String mailTo, String mobileNo) {

        //  String Deeplink = "https://nykaa.ly/P_" + Sharedata_product_id;

        //  String prdSubject = "Look what I found on Nykaa!";
        //  String prdDetail = "Check out " + Sharedata_product_name + " on Nykaa" + "\n" + Deeplink;
        try {


            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);

            shareIntent.setType("text/plain");

            PackageManager pm = context.getPackageManager();


            List<ResolveInfo> resInfo = pm.queryIntentActivities(shareIntent, 0);
            List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();

            for (int i = 0; i < resInfo.size(); i++) {
                // Extract the label, append it, and repackage it in a LabeledIntent
                ResolveInfo ri = resInfo.get(i);
                String packageName = ri.activityInfo.packageName;
                String processName = ri.activityInfo.processName;
                String AppName = ri.activityInfo.name;

                if ((packageName.contains("android.email") || packageName.contains("mms") || packageName.contains("messaging") || packageName.contains("android.gm") || packageName.contains("com.google.android.apps.plus"))) {

                    shareIntent.setComponent(new ComponentName(packageName, ri.activityInfo.name));

                    if (packageName.contains("android.email")) {
                        shareIntent.setType("image/*");
                        shareIntent.setData(Uri.parse("mailto:"));
                        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo});
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, prdSubject);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.setPackage(packageName);

                    } else if (packageName.contains("mms")) {
                        shareIntent.setType("vnd.android-dir/mms-sms");
                        shareIntent.setData(Uri.parse("sms:" + mobileNo));
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.setPackage(packageName);

                    } else if (packageName.contains("whatsapp")) {
                        String toNumber = mobileNo.replace("+", "").replace(" ", "");
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setPackage(packageName);


                    } else if (packageName.contains("messaging")) {
                        shareIntent.setType("vnd.android-dir/mms-sms");
                        shareIntent.setData(Uri.parse("sms:" + mobileNo));
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.setPackage(packageName);

                    } else if (packageName.contains("com.google.android.apps.plus")) {
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);

                        shareIntent.setPackage(packageName);

                    } else if (packageName.contains("android.gm")) {
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, prdSubject);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);

                        shareIntent.setPackage(packageName);

                    } else {
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);

                    }

                    intentList.add(new LabeledIntent(shareIntent, packageName, ri.loadLabel(pm), ri.icon));

                }
            }


            if (intentList.size() > 1) {
                intentList.remove(intentList.size() - 1);
            }

            Intent openInChooser = Intent.createChooser(shareIntent, "Share Via");
            // convert intentList to array
            LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
            openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);

            startActivity(openInChooser);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void ConfirmInsertContactAlert(String Title, String strBody, final String strMobile) {

        if (finmartContacttDialog != null && finmartContacttDialog.isShowing()) {

            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);


            Button btnAllow, btnReject;
            TextView txtTile, txtBody, txtMob;
            ImageView ivCross;

            LayoutInflater inflater = this.getLayoutInflater();

            final View dialogView = inflater.inflate(R.layout.layout_insert_contact_popup, null);

            builder.setView(dialogView);
            finmartContacttDialog = builder.create();
            // set the custom dialog components - text, image and button
            txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
            txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
            txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
            ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

            btnAllow = (Button) dialogView.findViewById(R.id.btnAllow);
            btnReject = (Button) dialogView.findViewById(R.id.btnReject);
            txtTile.setText(Title);
            txtBody.setText(strBody);
            txtMob.setText(strMobile);

            btnAllow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finmartContacttDialog.dismiss();
                    Utility.WritePhoneContact(getResources().getString(R.string.Finmart), userConstantEntity.getFinmartwhatsappno(), HomeActivity.this);
                    prefManager.updateContactMsgFirst("" + 1);
                    //  Toast.makeText(HomeActivity.this,"Contact Saved Successfully..",Toast.LENGTH_SHORT).show();
                }
            });

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finmartContacttDialog.dismiss();
                    prefManager.updateContactMsgFirst("" + 1);
                }
            });

            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finmartContacttDialog.dismiss();

                }
            });
            finmartContacttDialog.setCancelable(false);
            finmartContacttDialog.show();
        }

    }


    @Override
    public void onCancelClick(Dialog dialog, View view) {
        dialog.cancel();
    }

    //region permission

//    private boolean checkPermission() {
//
//        int readContact = ContextCompat.checkSelfPermission(getApplicationContext(), perms[0]);
//        int writeContact = ContextCompat.checkSelfPermission(getApplicationContext(), perms[1]);
//
//        return readContact == PackageManager.PERMISSION_GRANTED
//                && writeContact == PackageManager.PERMISSION_GRANTED;
//
//    }
//
//    private boolean checkRationalePermission() {
//
//        boolean readContact = ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, perms[0]);
//
//        boolean writeContact = ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, perms[1]);
//
//
//        return readContact || writeContact;
//    }
//
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, perms, Constants.REQUEST_CODE_ASK_PERMISSIONS);
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//
//        switch (requestCode) {
//            case Constants.PERMISSION_CALLBACK_CONSTANT:
//                if (grantResults.length > 0) {
//
//                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean call_phone = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    if (call_phone) {
//
//                        if (userConstantEntity.getMangMobile() != null && userConstantEntity.getManagName() != null) {
//                            ConfirmAlert("Calling", getResources().getString(R.string.RM_Calling) + " " + userConstantEntity.getManagName());
//                        }
//
//                    }
//
//                }
//                break;
//
//            case Constants.REQUEST_CODE_ASK_PERMISSIONS:
//                if (grantResults.length > 0) {
//
//                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean readContact = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean writeContact = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//
//                    if (readContact && writeContact) {
//
//                        addFinmartContact();
//                    }
//                }
//                break;
//
//        }
//    }
//
// endregion

    //popup
    public void ConfirmOtherLoanProductsAlert() {

        if (LoanDialog != null && LoanDialog.isShowing()) {

            return;
        } else {

            AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HomeActivity.this, R.style.CustomDialog);


            Button btnone, btntwo;
            TextView txtTile, txtBody, txtMob;
            ImageView ivCross;
            CardView cvBalanceTransfer, cvFreeCreditReport, cvLoanOnMessanger,
                    cvLeadSubmission, cvCashLoan, cvBusinessLoan, cvRectifyCredit;

            LayoutInflater inflater = this.getLayoutInflater();

            final View dialogView = inflater.inflate(R.layout.layout_menu_dashboard1, null);

            builder.setView(dialogView);
            LoanDialog = builder.create();
            // set the custom dialog components - text, image and button
            txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
            //   txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
            //   txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
            ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

            cvBalanceTransfer = (CardView) dialogView.findViewById(R.id.cvBalanceTransfer);
            cvFreeCreditReport = (CardView) dialogView.findViewById(R.id.cvFreeCreditReport);
            cvLoanOnMessanger = (CardView) dialogView.findViewById(R.id.cvLoanOnMessanger);
            cvLeadSubmission = (CardView) dialogView.findViewById(R.id.cvLeadSubmission);
            cvCashLoan = (CardView) dialogView.findViewById(R.id.cvCashLoan);
            cvBusinessLoan = (CardView) dialogView.findViewById(R.id.cvBusinessLoan);
            cvRectifyCredit = (CardView) dialogView.findViewById(R.id.cvRectifyCredit);

            cvBalanceTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();

                //    startActivity(new Intent(HomeActivity.this, BalanceTransferDetailActivity.class));
                   // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Balance Transfer tab on home page"), Constants.BALANCE_TRANSFER), null);
                  //  MyApplication.getInstance().trackEvent(Constants.BALANCE_TRANSFER, "Clicked", "Balance Transfer tab on home page");

                }
            });

            cvFreeCreditReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();
                    Utility.loadWebViewUrlInBrowser(HomeActivity.this,
                            "http://www.rupeeboss.com/equifax-finmart?fbaid="
                                    + String.valueOf(loginResponseEntity.getFBAId()));
                }
            });

            cvLoanOnMessanger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();
                    Utility.loadWebViewUrlInBrowser(HomeActivity.this,
                            "https://yesbankbot.buildquickbots.com/chat/rupeeboss/staff/?userid=" + String.valueOf(loginResponseEntity.getFBAId()) + "&usertype=FBA&vkey=b34f02e9-8f1c");

                }
            });

            cvLeadSubmission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, QuickLeadActivity.class));
                   // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Quick Lead tab on home page"), Constants.QUICK_LEAD), null);
                    MyApplication.getInstance().trackEvent(Constants.QUICK_LEAD, "Clicked", "Quick Lead tab on home page");
                }
            });
//pending
            cvCashLoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                            .putExtra("URL", "http://www.rupeeboss.com/gopaysense?fbaid=" + String.valueOf(loginResponseEntity.getFBAId()) + "&type=finmart&loan_id=" + String.valueOf(loginResponseEntity.getLoanId()))
                            .putExtra("NAME", "" + "Cash Loan")
                            .putExtra("TITLE", "" + "Cash Loan"));
                }
            });

            cvBusinessLoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();
               //     startActivity(new Intent(HomeActivity.this, LapLoanDetailActivity.class));
                   // new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("LAP tab on home page"), Constants.LAP), null);
             //       MyApplication.getInstance().trackEvent(Constants.LAP, "Clicked", "LAP tab on home page");

                    //http://www.rupeeboss.com/lendingkart?fbaid=37292&type=finmart&loan_id=38054
//                    startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
//                            .putExtra("URL", "http://www.rupeeboss.com/lendingkart?fbaid=" + String.valueOf(loginResponseEntity.getFBAId()) + "&type=finmart&loan_id=" + String.valueOf(loginResponseEntity.getLoanId()))
//                            .putExtra("NAME", "" + "Business Loan")
//                            .putExtra("TITLE", "" + "Business Loan"));
                }
            });


            cvRectifyCredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();
                    //    http://www.rupeeboss.com/rectifycredit?fbaid=37292&type=finmart&loan_id=38054
                    startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                            .putExtra("URL", "https://www.rupeeboss.com/rectifycredit?fbaid=" + String.valueOf(loginResponseEntity.getFBAId()) + "&type=finmart&loan_id=" + String.valueOf(loginResponseEntity.getLoanId()))
                            .putExtra("NAME", "" + "Rectify Credit")
                            .putExtra("TITLE", "" + "Rectify Credit"));

                }
            });
            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDialog.dismiss();

                }
            });

            LoanDialog.setCancelable(false);
            LoanDialog.show();
        }

    }

    public void ConfirmMoreServiceAlert() {

        if (MoreServiceDialog != null && MoreServiceDialog.isShowing()) {

            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.CustomDialog);


            Button btnone, btntwo;
            TextView txtTile, txtBody, txtMob;
            ImageView ivCross;
            CardView cvFinpeace, cvHealthAssure;

            LayoutInflater inflater = this.getLayoutInflater();

            final View dialogView = inflater.inflate(R.layout.layout_menu_dashboard2, null);

            builder.setView(dialogView);
            MoreServiceDialog = builder.create();
            // set the custom dialog components - text, image and button
            txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
            //   txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
            //   txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
            ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

            cvFinpeace = (CardView) dialogView.findViewById(R.id.cvFinpeace);
            cvHealthAssure = (CardView) dialogView.findViewById(R.id.cvHealthAssure);

            cvFinpeace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreServiceDialog.dismiss();

                    startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                            .putExtra("URL", "https://10oqcnw.finpeace.ind.in/app#/"
                                    + new DBPersistanceController(HomeActivity.this).getUserData().getFBAId())
                            .putExtra("NAME", "FIN-PEACE")
                            .putExtra("TITLE", "FIN-PEACE"));
                  //  new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Fin Peace tab on home page"), Constants.FIN_PEACE), null);
                    MyApplication.getInstance().trackEvent(Constants.FIN_PEACE, "Clicked", "Fin Peace tab on home page");

                }
            });

            cvHealthAssure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreServiceDialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, HealthCheckUpListActivity.class));
                    //new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("Health CheckUp"), Constants.HEALTH_CHECKUP), null);
                    MyApplication.getInstance().trackEvent(Constants.HEALTH_CHECKUP, "Clicked", "Health CheckUp tab on home page");

                }
            });


            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreServiceDialog.dismiss();

                }
            });
            MoreServiceDialog.setCancelable(false);
            MoreServiceDialog.show();
        }

    }

    public void ConfirmnMyUtilitiesAlert() {

        if (MyUtilitiesDialog != null && MyUtilitiesDialog.isShowing()) {

            return;
        } else {
            AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HomeActivity.this, R.style.CustomDialog);


            Button btnone, btntwo;
            TextView txtTile, txtBody, txtMob;
            ImageView ivCross;
            CardView cvMPS, cvIncomeCalculator,
                    cvMyTrainingCalender, cvHelpFeedback;

            LayoutInflater inflater = this.getLayoutInflater();

            final View dialogView = inflater.inflate(R.layout.layout_menu_dashboard3, null);

            builder.setView(dialogView);
            MyUtilitiesDialog = builder.create();
            // set the custom dialog components - text, image and button
            txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
            //   txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
            //   txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
            ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

            cvMPS = (CardView) dialogView.findViewById(R.id.cvMPS);
            cvIncomeCalculator = (CardView) dialogView.findViewById(R.id.cvIncomeCalculator);
            cvMyTrainingCalender = (CardView) dialogView.findViewById(R.id.cvMyTrainingCalender);
            cvHelpFeedback = (CardView) dialogView.findViewById(R.id.cvHelpFeedback);


            cvMPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtilitiesDialog.dismiss();

                    new MasterController(HomeActivity.this).getMpsData(HomeActivity.this);
                  //  new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("MPS : MPS button in menu "), Constants.MPS), null);
                    //  startActivity(new Intent(HomeActivity.this, UnderConstructionActivity.class));
                }
            });

            cvIncomeCalculator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtilitiesDialog.dismiss();
                    //      startActivity(new Intent(HomeActivity.this, IncomeCalculatorActivity.class));
                    startActivity(new Intent(HomeActivity.this, IncomePotentialActivity.class));
                }
            });

            cvMyTrainingCalender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtilitiesDialog.dismiss();

                    startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                            .putExtra("URL", " http://bo.magicfinmart.com/training-schedule-calendar/" + String.valueOf(loginResponseEntity.getFBAId()))
                            .putExtra("NAME", "" + "My Training Calender")
                            .putExtra("TITLE", "" + "My Training Calender"));

                }
            });

            cvHelpFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtilitiesDialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, HelpFeedBackActivity.class));
                  //  new TrackingController(HomeActivity.this).sendData(new TrackingRequestEntity(new TrackingData("HELP & FEEDBACK : HELP & FEEDBACK button in menu "), Constants.HELP), null);

                }
            });
//pending


            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtilitiesDialog.dismiss();

                }
            });
            MyUtilitiesDialog.setCancelable(false);
            MyUtilitiesDialog.show();
        }


    }

    public void shareCallingData(UserCallingEntity userCallingEntity) {
        Intent intentCalling = new Intent(Intent.ACTION_DIAL);
        intentCalling.setData(Uri.parse("tel:" + userCallingEntity.getMobileNo()));
        startActivity(intentCalling);


    }

    public void shareEmailData(UserCallingEntity userCallingEntity) {
        shareMailSmsList(HomeActivity.this, "", "Dear Sir/Madam,", userCallingEntity.getEmailId(), userCallingEntity.getMobileNo());

    }

    public void dialogCoBrowser() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Share Screen..!");
        builder.setMessage("Do you want to share your screen?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "SHARE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        Intent intent = new Intent(HomeActivity.this, CobrowseActivity.class);
//                        startActivity(intent);
                    }
                });

        builder.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        androidx.appcompat.app.AlertDialog exitdialog = builder.create();
        exitdialog.show();

        Button negative = exitdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button positive = exitdialog.getButton(DialogInterface.BUTTON_POSITIVE);
        negative.setTextColor(getResources().getColor(R.color.header_light_text));
        positive.setTextColor(getResources().getColor(R.color.black));
    }


    private void showMultiLanguage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language...");// add a radio button list
        String[] animals = {"English", "Hindi", "Marathi", "Gujrati"};
        int checkedItem = -1; // Nothing Selected
        selectedLang = -1;
        LANGUAGE = "";
        builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int lang) {

                selectedLang = lang;
                //Toast.makeText(HomeActivity.this, " Language Selected " + lang, Toast.LENGTH_LONG).show();
            }
        });// add OK and Cancel buttons
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int lang) {
                // user clicked OK
                if (selectedLang != -1) {


                    if (selectedLang == 0) {
                        LANGUAGE = "English";
                    } else if (selectedLang == 1) {
                        LANGUAGE = "Hindi";
                    } else if (selectedLang == 2) {
                        LANGUAGE = "Marathi";
                    } else if (selectedLang == 3) {
                        LANGUAGE = "Gujrathi";
                    }

                    prefManager.setLanguage(LANGUAGE);

//                    setTextViewForLang(LANGUAGE);

                    setNavigationMenu(LANGUAGE);
                    Intent dashboardIntent = new Intent(Utility.USER_DASHBOARD);
                    LocalBroadcastManager.getInstance(HomeActivity.this).sendBroadcast(dashboardIntent);

                }

            }
        });
        builder.setNegativeButton("Cancel", null);// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setLocal(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
        ///////////////////////////////

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

    }

    private String getDeviceID() {
        return new ReadDeviceID(this).getAndroidID();
    }

    public void shareDashbordProduct(DashboardMultiLangEntity dashboardMultiLangEntity) {

        dashboardShareEntity = dashboardMultiLangEntity;
        showDialog();
        //loginResponseEntity.getFBAId()
        new RegisterController(this).getProductShareUrl(
                loginResponseEntity.getFBAId(),
                Integer.valueOf(loginResponseEntity.getPOSPNo()),
                dashboardMultiLangEntity.getProductId(),
                0,
                this);
    }

    public void shareProductPopUp(DashboardMultiLangEntity shareEntity) {

        if (shareProdDialog != null && shareProdDialog.isShowing()) {

            return;
        }

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this);
        TextView txtTitle, txtMessage;
        Button btnShare;
        ImageView ivCross;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_share_popup, null);

        builder.setView(dialogView);
        shareProdDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtTitle = (TextView) dialogView.findViewById(R.id.txtTitle);
        txtMessage = (TextView) dialogView.findViewById(R.id.txtMessage);
        btnShare = (Button) dialogView.findViewById(R.id.btnShare);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        txtTitle.setText("" + shareEntity.getTitle());
        txtMessage.setText("" + shareEntity.getPopupmsg());
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDashbordProduct(shareEntity);
                shareProdDialog.dismiss();

            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareProdDialog.dismiss();

            }
        });

        shareProdDialog.setCancelable(true);
        shareProdDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }


    public void verifyPospNo() {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this, R.style.CustomDialog);
        TextView txtTitle, txtMessage;
        Button btnClose;
        ImageView ivCross;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_failure_popup, null);

        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog verifyDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtTitle = (TextView) dialogView.findViewById(R.id.txtTitle);
        txtMessage = (TextView) dialogView.findViewById(R.id.txtMessage);
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        txtTitle.setText("Authorization");
        txtMessage.setText(getResources().getString(R.string.verify_SSID));
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyDialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyDialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        verifyDialog.setCancelable(false);
        verifyDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }


    public void infoProductPopUp(DashboardMultiLangEntity shareEntity) {
        openWebViewPopUp(txtFbaID, shareEntity.getInfo(), true, "");
    }

    private void navigateViaNotification(String prdID, String WebURL, String Title) {

        if (prdID.equals("18")) {

            startActivity(new Intent(HomeActivity.this, TermSelectionActivity.class));

        } else if (prdID.equals("WB")) {

            startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                    .putExtra("URL", WebURL)
                    .putExtra("NAME", Title)
                    .putExtra("TITLE", Title));

        } else {

            if (WebURL.trim().equals("") || Title.trim().equals("")) {

                return;
            }
            String ipaddress = "0.0.0.0";
            try {
                ipaddress = Utility.getMacAddress(HomeActivity.this);
            } catch (Exception io) {
                ipaddress = "0.0.0.0";
            }


            //&ip_address=10.0.3.64&mac_address=10.0.3.64&app_version=2.2.0&product_id=1
            String append = "&ss_id=" + userConstantEntity.getPOSPNo() + "&fba_id=" + userConstantEntity.getFBAId() + "&sub_fba_id=" +
                    "&ip_address=" + ipaddress + "&mac_address=" + ipaddress
                    + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                    + "&device_id=" + Utility.getDeviceId(HomeActivity.this)
                    + "&product_id=" + prdID
                    + "&login_ssid=";
            WebURL = WebURL + append;

            startActivity(new Intent(HomeActivity.this, CommonWebViewActivity.class)
                    .putExtra("URL", WebURL)
                    .putExtra("NAME", Title)
                    .putExtra("TITLE", Title));
        }

    }
}
