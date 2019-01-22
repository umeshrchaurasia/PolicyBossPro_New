package com.datacomp.magicfinmart.sendTemplateSms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.datacomp.magicfinmart.BaseActivity;
import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.notification.NotificationActivity;
import com.datacomp.magicfinmart.notification.NotificationAdapter;
import com.datacomp.magicfinmart.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register.RegisterController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.NotificationEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.SmsTemplateEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.NotificationResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.SmsTemplateResponse;

public class SendTemplateSmsActivity extends BaseActivity implements IResponseSubcriber {

    RecyclerView rvSendSms;
    List<SmsTemplateEntity> smsTemplateEntityList;
    SendTemplateAdapter mAdapter;
    DBPersistanceController dbPersistanceController;
    LoginResponseEntity loginEntity;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbPersistanceController = new DBPersistanceController(this);
        loginEntity = dbPersistanceController.getUserData();

        initialize();

        // getNotificationData
        showDialog("Fetching Data...");
        new RegisterController(SendTemplateSmsActivity.this).getSmsTemplate( SendTemplateSmsActivity.this);


    }

    private void initialize() {

        prefManager = new PrefManager(SendTemplateSmsActivity.this);
        smsTemplateEntityList = new ArrayList<SmsTemplateEntity>();

        prefManager.setNotificationCounter(0);

        rvSendSms = (RecyclerView) findViewById(R.id.rvSendSms);
        rvSendSms.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SendTemplateSmsActivity.this);
        rvSendSms.setLayoutManager(layoutManager);



    }

    public void redirectToTempate(SmsTemplateEntity smsTemplateEntity) {

        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "", null));
            intent.putExtra("sms_body", ""+smsTemplateEntity.getTemplete().toString());
            startActivity(intent);
            //    startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", mobNumber, null)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof SmsTemplateResponse) {

            if (response.getStatusNo() == 0) {
                if ( ((SmsTemplateResponse) response).getMasterData() != null) {

                    smsTemplateEntityList = ((SmsTemplateResponse) response).getMasterData();

                    mAdapter = new SendTemplateAdapter(SendTemplateSmsActivity.this, smsTemplateEntityList);
                    rvSendSms.setAdapter(mAdapter);
                } else {
                    rvSendSms.setAdapter(null);
                    Snackbar.make(rvSendSms, "No Data Available", Snackbar.LENGTH_SHORT).show();
                }
            }else {
                rvSendSms.setAdapter(null);
                Snackbar.make(rvSendSms, "No Data Available", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
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
        Intent intent = new Intent();

        // intent.putExtra("COUNTER", "0");
        setResult(Constants.REQUEST_CODE, intent);
        finish();
        super.onBackPressed();
    }


}