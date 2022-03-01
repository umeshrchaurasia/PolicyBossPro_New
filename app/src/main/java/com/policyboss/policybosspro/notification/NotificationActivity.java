package com.policyboss.policybosspro.notification;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.policyboss.policybosspro.BuildConfig;
import com.policyboss.policybosspro.IncomeCalculator.IncomePotentialActivity;
import com.policyboss.policybosspro.helpfeedback.HelpFeedBackActivity;
import com.policyboss.policybosspro.home.HomeActivity;
import com.policyboss.policybosspro.homeMainKotlin.BottomSheetDialogMenuFragment;
import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity;
import com.policyboss.policybosspro.login.LoginActivity;
import com.policyboss.policybosspro.myaccount.MyAccountActivity;
import com.policyboss.policybosspro.switchuser.SwitchUserActivity;
import com.policyboss.policybosspro.term.termselection.TermSelectionActivity;
import com.policyboss.policybosspro.webviews.CommonWebViewActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import io.ak1.BubbleTabBar;
import io.ak1.OnBubbleClickListener;
import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.MasterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.NotificationEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserConstantEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.NotificationResponse;

public class NotificationActivity extends BaseActivity implements IResponseSubcriber , BottomSheetDialogMenuFragment.IBottomMenuCallback{

    RecyclerView rvNotify;
    List<NotificationEntity>  NotificationLst;
    NotificationAdapter mAdapter;
    DBPersistanceController dbPersistanceController;
    UserConstantEntity userConstantEntity;
    LoginResponseEntity loginEntity;
    PrefManager prefManager;

    BubbleTabBar bubbleTabBar;
    ShimmerFrameLayout shimmerNotify;

   AlertDialog MyUtilitiesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbPersistanceController = new DBPersistanceController(this);
        loginEntity = dbPersistanceController.getUserData();

        userConstantEntity = dbPersistanceController.getUserConstantsData();

        initialize();

      //  bubbleTabBar.setSelectedWithId(R.id.nav_notification,true);
        // region Handling Bottom bar selection

        if (getIntent().getExtras() != null) {

            // For getting User Click Action
            if (getIntent().getExtras().getString(Constants.BOTTOM_TYPE) != null) {

                String type = getIntent().getExtras().getString(Constants.BOTTOM_TYPE);

                switch(type) {



                    case "nav_notification" :

                        bubbleTabBar.setSelectedWithId(R.id.nav_notification, true);


                        break;





                }

            }

        }

        //endregion
       // showDialog("Fetching Data...");
        shimmerNotify.startShimmerAnimation();
        new RegisterController(NotificationActivity.this).getNotificationData(String.valueOf(loginEntity.getFBAId()), NotificationActivity.this);




        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int id) {




                switch (id){
                    case R.id.nav_home:

                        Intent intent = new Intent(NotificationActivity.this, HomeMainActivity.class);
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

                    case R.id.nav_profile:

                        Intent intent3 = new Intent(NotificationActivity.this, MyAccountActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent3.putExtra(Constants.BOTTOM_TYPE,"nav_profile");
                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0,0);

                        break;


                    }



                }

        });


    }

    private void initialize() {

        prefManager = new PrefManager(NotificationActivity.this);
        NotificationLst = new ArrayList<NotificationEntity>();

        prefManager.setNotificationCounter(0);

        shimmerNotify = (ShimmerFrameLayout) findViewById(R.id.shimmerNotify);
        bubbleTabBar =  (BubbleTabBar) findViewById(R.id.bubbleTabBar);
        rvNotify = (RecyclerView) findViewById(R.id.rvNotify);
        rvNotify.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
        rvNotify.setLayoutManager(layoutManager);



    }


    public void redirectToApplyLoan(NotificationEntity notifyEntity) {

        if(notifyEntity.getNotifyFlag() != null && notifyEntity.getWeb_url() != null){

            navigateViaNotification(notifyEntity.getNotifyFlag(),notifyEntity.getWeb_url(),notifyEntity.getWeb_title());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.SWITCH_USER_REQUEST_CODE) {
            if (data != null) {
                //switchUserBinding();
//                dbPersistanceController = new DBPersistanceController(this);
//                loginEntity = dbPersistanceController.getUserData();

                Intent intent = new Intent(this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


                // init_headers();
            }

        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();


        shimmerNotify.stopShimmerAnimation();
        shimmerNotify.setVisibility(View.GONE);

        if (response instanceof NotificationResponse) {

            if (response.getStatusNo() == 0) {
                if ( ((NotificationResponse) response).getMasterData() != null) {

                    NotificationLst = ((NotificationResponse) response).getMasterData();


                    mAdapter = new NotificationAdapter(NotificationActivity.this, NotificationLst);
                    rvNotify.setAdapter(mAdapter);
                    rvNotify.setVisibility(View.VISIBLE);
                } else {
                    rvNotify.setAdapter(null);
                    Snackbar.make(rvNotify, "No Notification  Data Available", Snackbar.LENGTH_SHORT).show();
                }
            }else {
                rvNotify.setAdapter(null);
                Snackbar.make(rvNotify, "No Notification  Data Available", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        shimmerNotify.stopShimmerAnimation();
        shimmerNotify.setVisibility(View.GONE);
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(NotificationActivity.this, HomeMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

        overridePendingTransition(0,0);

    }

    private void navigateViaNotification(String prdID, String WebURL, String Title) {

        if (prdID.equals("18")) {

            startActivity(new Intent(NotificationActivity.this, TermSelectionActivity.class));
            this.finish();

        } else {

            if( WebURL.trim().equals("") || Title.trim().equals("") )
            {

                return;
            }
            String ipaddress = "0.0.0.0";
            try {
                ipaddress = Utility.getMacAddress(NotificationActivity.this);
            } catch (Exception io) {
                ipaddress = "0.0.0.0";
            }


            //&ip_address=10.0.3.64&mac_address=10.0.3.64&app_version=2.2.0&product_id=1
            String append = "&ss_id=" + userConstantEntity.getPOSPNo() + "&fba_id=" + userConstantEntity.getFBAId() + "&sub_fba_id=" +
                    "&ip_address=" + ipaddress + "&mac_address=" + ipaddress
                    + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                    + "&device_id=" + Utility.getDeviceId(NotificationActivity.this)
                    + "&product_id=" + prdID
                    + "&login_ssid=";
            WebURL = WebURL + append;

            this.finish();
            startActivity(new Intent(NotificationActivity.this, CommonWebViewActivity.class)
                    .putExtra("URL", WebURL)
                    .putExtra("NAME", Title.toUpperCase())
                    .putExtra("TITLE",Title.toUpperCase()));


        }

    }


    @Override
    public void onClickLogout() {

        SharedPreferences preferences = getSharedPreferences(Constants.SWITCh_ParentDeatils_FINMART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            //  shortcutManager.removeAllDynamicShortcuts();
        }
        dialogLogout(NotificationActivity.this);
    }

    @Override
    public void onSwitchUser() {

        startActivityForResult(new Intent(NotificationActivity.this, SwitchUserActivity.class), Constants.SWITCH_USER_REQUEST_CODE);


    }



    @Override
    public void ConfirmnMyUtilitiesAlert() {

        if(loginEntity != null){

            ConfirmnUtilitiesAlert(loginEntity);

        }

       }

}
