package com.policyboss.policybosspro.utility;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.Utility;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rajeev Ranjan on 10/01/2018.
 */

public class Constants {

    //region Tracking Types
    public static float SPINNER_FONT_SIZE = 14f;
    public static String LOGIN = "FBA LOGIN";
    public static String REGISTER = "FBA REGISTER";


    public static String BOTTOM_TYPE = "BOTTOM_HOME.";
  ;
    public static int BOTTOM_REQUEST_CODE = 25;


    public static String SALES_MATERIAL = "CUSTOMER COMM.";
    public static String PENDING_CASES = "PENDING CASES";
    public static String KNOWLEDGE_GURU = "KNOWLEDGE GURU";

    //navigation
    public static String MY_ACCOUNT = "MY ACCOUNT";
    public static String POSP = "POSP ENROLLMENT";
    public static String OFFLINE_QUOTES = "OFFLINE QUOTES";
    public static String MY_BUSINESS = "MY BUSINESS";
    public static String REFER = "REFER A FRIEND";
    public static String MPS = "MPS";
    public static String INSPECTION = "INSPECTION";
    public static String POSP_TRAINING = "POPS TRAINING";
    public static String HELP = "HELP_FEEDBACK";
    public static String WHATSNEW = "WHATS NEW";
    public static String LOGOUT = "LOGOUT";
    //endregion
    public static String CAMPAIGN = "CAMPAIGN";
    public static String OFFLINE = "OFFLINE";
    public static String ULTRA_LAKSHA = "ULTRA_LAKSHA";

    public static String PRIVATE_CAR = "MOTOR INSURANCE";
    public static String PRIVATE_CAR_REQUEST= "MOTOR  REQUEST";
    public static String PRIVATE_CAR_RESPONSE = "MOTOR  RESPONSE";
    public static String PRIVATE_CAR_FASTLANE_RESPONSE = "MOTOR  FASTLANE RESPONSE";
    public static String TWO_WHEELER = "TWO WHEELER INSURANCE";
    public static String TWO_WHEELER_REQUEST = "TWO WHEELER  REQUEST";
    public static String TWO_WHEELER_RESPONSE = "TWO WHEELER  RESPONSE";
    public static String TWO_WHEELER_FASTLANE_RESPONSE = "TWO WHEELER FASTLANE RESPONSE";
    public static String FASTLANE = "FASTLANE";
    public static String HEALTH_INS = "HEALTH INSURANCE";
    public static String HEALTH_INS_OFF = "HEALTH INSURANCE_OFFLINE";
    public static String LIFE_INS = "TERM INSURANCE";
    public static String ULTRALAKSHA_INS = "ULTRA LAKSHA INSURANCE";
    public static String HOME_LOAN = "HOME LOAN";
    public static String PERSONA_LOAN = "PERSONAL LOAN";
    public static String BUSINESS_LOAN = "BUSINESS LOAN";
    public static String CAR_TOP_LOAN = "CAR LOAN TOP UP LOAN";

    public static String LAP = "LAP";
    public static String CREDIT_CARD = "CREDIT CARD";
    public static String BALANCE_TRANSFER = "BALANCE TRANSFER";
    public static String QUICK_LEAD = "QUICK LEAD";
    public static String FIN_PEACE = "FIN PEACE";
    public static String HEALTH_CHECKUP = "HEALTH CHECKUP PLANS";
    public static String CV = "COMMERSIAL VEHICLE";
    public static String ULTRALAKSHA_COMBO =  "ULTRA LAKSHYA COMBO";


    public static final Double GST = 0.18;
    public static final String SHARED_PREFERENCE_FINMART = "shared_finmart";
    public static final String SWITCh_ParentDeatils_FINMART = "switch_ParentDeatils_finmart";
   /* public static final String EXTERNAL_LPG = "External LPG";
    public static final String EXTERNAL_CNG = "External CNG";*/
    public static String PERSONAL_LOAN_QUOTES = "personalloanquotes";
    public static String BL_LOAN_QUOTES = "balanceTransferloanquotes";
    public static String BL_LOAN_SERVICE = "balanceTransferloanservice";
    public static String BL_REQUEST = "balanceTransferRequest";
    public static String HOME_LOAN_QUOTES = "homeloanquotes";
    public static String HL_REQUEST = "homeloanRequest";
    public static String PL_REQUEST = "personalloanRequest";
    public static String LAP_REQUEST = "loanagainstpropertyRequest";
    public static String CITY_FACADE = "citylist";
    public static String HOMELOAN_REQUEST_FACADE = "homeloanrequest";
    public static String PRODUCT_FACADE = "productlist";
    public static String PROPERTY_FACADE = "propertylist";
    public static String LOGIN_FACADE = "logindata";
    public static String PROFILE_URL = "profileurl";
    public static String QUOTES = "quotes";
    public static String DEVICE_ID = "deviceid";
    public static String DEVICE_TOKEN = "devicetoken";
    public static String PAN_NUMBER = "pannumber";
    public static String PASSWORD = "password";
    public static String WEB_URL = "WEBURL";
    public static int REQUEST_CODE = 22;
    public static int SWITCH_USER_REQUEST_CODE = 10;
    public static String PRODUCT_ID = "salesProductID";
    public static String DOC_DATA = "docData";
    public static String FESTIVAL_DATA = "festivalData";
    public static String SHARE_ACTIVITY_NAME = "shareactivityname";
    public static String SHARE_WHATSAPP = "sharewhatsapp";
    public static final int PERMISSION_CALLBACK_CONSTANT = 100;
    public static final int REQUEST_PERMISSION_SETTING = 101;
    public static final int PERMISSION_CAMERA_STORACGE_CONSTANT = 103;
    public static final int PERMISSION_CALLBACK_SUPPORT = 104;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 105;

    public static String OFFLINE_DOC_DATA = "offline_docData";
    public static String OFFLINE_HEALTH_EDIT = "offline_health_edit";

    public static String TERM_INPUT_FRAGMENT = "input_term_fragment_bottom";

    public static String[] permissionsRequired ={Manifest.permission.CALL_PHONE};

    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCE_FINMART, MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferenceEditor(Context context) {
        return getSharedPreference(context).edit();
    }

    public static List<String> getPastFifteenYear(String selectedDate) {

        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        ArrayList<String> arrayListYear = new ArrayList<>();
        int year, startYear, endYear;

        Calendar calendar = Calendar.getInstance();
        if (selectedDate != "") {
            try {
                calendar.setTime(df.parse(selectedDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        year = calendar.get(Calendar.YEAR);
        startYear = year;
        endYear = startYear - 15;
        arrayListYear.add("Select Manufacture year");
        for (int i = startYear; i >= endYear; i--) {
            arrayListYear.add("" + i);
        }

        return arrayListYear;
    }

    public static SpannableStringBuilder setStarToLabel(String text) {
        String simple = text;
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private void datashareList(Context context, Bitmap bitmap, String strSubject, String strDetail) {

        String prdSubject = strSubject;
        String prdDetail = strDetail;
        try {

            //  File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Screenshots" + System.currentTimeMillis() + ".png");

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots", Utility.getCurrentMobileDateTime() + ".png");
            // Utility.getCurrentMobileDateTime()
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();

            Uri screenshotUri = Uri.fromFile(file);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);

            shareIntent.setType("text/plain");

            PackageManager pm = context.getPackageManager();


            List<ResolveInfo> resInfo = pm.queryIntentActivities(shareIntent, 0);
            List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
            ///////////
            for (int i = 0; i < resInfo.size(); i++) {
                // Extract the label, append it, and repackage it in a LabeledIntent
                ResolveInfo ri = resInfo.get(i);
                String packageName = ri.activityInfo.packageName;
                String processName = ri.activityInfo.processName;
                String AppName = ri.activityInfo.name;

                if ((packageName.contains("android.email") || packageName.contains("twitter") || (packageName.contains("whatsapp")) || packageName.contains("android.gm") || packageName.contains("com.google.android.apps.plus")) || (packageName.contains("apps.docs")) && processName.contains("android.apps.docs:Clipboard") || (packageName.contains("android.talk")) && AppName.contains("hangouts")) {

                    shareIntent.setComponent(new ComponentName(packageName, ri.activityInfo.name));

                    if (packageName.contains("android.email")) {
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, prdSubject);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.setPackage(packageName);

                    } else if (packageName.contains("twitter")) {

                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, prdSubject);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        shareIntent.setPackage(packageName);

                    }
//                    else if (packageName.contains("facebook.katana")) {
//                        shareIntent.setType("text/plain");
//                        shareIntent.putExtra(Intent.EXTRA_TEXT, product.getImageUrl());
//                        shareIntent.setPackage("com.facebook.katana");
//                        //shareIntent.putExtra(Intent.EXTRA_STREAM, Deeplink);
//                    }
//                    else if (packageName.contains("facebook.orca")) {
//                        shareIntent.setType("image/*");
//                        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
//                        shareIntent.setPackage("com.facebook.orca");
//
//                    }

                    else if (packageName.contains("whatsapp")) {
                        shareIntent.setType("image/*");

                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        shareIntent.setPackage(packageName);


                    } else if (packageName.contains("com.google.android.apps.plus")) {
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        shareIntent.setPackage(packageName);

                    } else if (packageName.contains("android.talk")) {
                        if (AppName.contains("hangouts")) {
                            shareIntent.setType("image/*");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                            shareIntent.setPackage(packageName);
                        }

                    } else if (packageName.contains("apps.docs")) {
                        if (processName.contains("android.apps.docs:Clipboard")) {
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                            shareIntent.setPackage(packageName);
                        }

                    } else if (packageName.contains("android.gm")) {
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, prdSubject);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, prdDetail);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
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

            context.startActivity(openInChooser);
        } catch (Exception e) {

            // Toast.makeText(getActivity(), "Please check your permissions settings.Permission issue.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public static void shareToWhatsApp(Context context, String strDetail) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, strDetail);
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Whatsapp Not Installed", Toast.LENGTH_SHORT).show();
        }
    }
    public static void changeFontMedium_TextView(TextView textView, Context c) {
        /*Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/Roboto-Medium.ttf");
        textView.setTypeface(tf);*/
    }
    //inspection

    public static final String FRONTREAR = "frontrear";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String GLASS = "glass";
   // public static final String REGISTER = "register";

    public static boolean FRONT_CLICK=false;
    public static boolean REAR_CLICK=false;
    public static boolean LEFT_CLICK=false;
    public static boolean RIGHT_CLICK=false;
    public static boolean GLASS_CLICK=false;


    public static boolean CHECKED_FRONT_CLICK=false;
    public static boolean CHECKED_REAR_CLICK=false;
    public static boolean CHECKED_LEFT_CLICK=false;
    public static boolean CHECKED_RIGHT_CLICK=false;
    public static boolean CHECKED_GLASS_CLICK=false;
}
