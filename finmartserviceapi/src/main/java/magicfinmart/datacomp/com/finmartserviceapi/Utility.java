package magicfinmart.datacomp.com.finmartserviceapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Nilesh Birhade on 11-01-2018.
 */

public class Utility {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static String LOGIN_IP = "";
   /* public static final String HORIZON_URL = "http://qa-horizon.policyboss.com:3000";
    public static final String QUOTE_BASE_URL = "http://qa.policyboss.com/";
    public static final String SECRET_KEY = "SECRET-ODARQ6JP-9V2Q-7BIM-0NNM-DNRTXRWMRTAL";
    public static final String CLIENT_KEY = "CLIENT-GLF2SRA5-CFIF-4X2T-HC1Z-CXV4ZWQTFQ3T";
    public static final int CLIENT_ID = 4;*/

    //public static final String HORIZON_URL = "http://horizon.policyboss.com:5000";
    //public static final String QUOTE_BASE_URL = "http://www.policyboss.com/";
    public static final String HORIZON_URL = "http://qa-horizon.policyboss.com:3000";
    public static final String QUOTE_BASE_URL = "http://qa.policyboss.com/";
    public static final String SECRET_KEY = "SECRET-VG9N6EVV-MIK3-1GFC-ZRBV-PE7XIQ8DV4GY";
    public static final String CLIENT_KEY = "CLIENT-WF4GWODI-HMEB-Q7M6-CLES-DEJCRF7XLRVI";
    public static final int CLIENT_ID = 3;

    public static final String VERSION_CODE = "2.0";
    public static final String BIKEQUOTE_UNIQUEID = "bike_quote_uniqueid";
    public static final String CARQUOTE_UNIQUEID = "car_quote_uniqueid";
    public static final String QUOTE_COUNTER = "quote_counter";
    public static final String SHARED_PREFERENCE_POLICYBOSS = "shared_finmart";
    public static final String HMLOAN_APPLICATION = "hmLoan_Application_LoanApply";
    public static final String PLLOAN_APPLICATION = "plLoan_Application_LoanApply";
    public static final String BTLOAN_APPLICATION = "btLoan_Application_LoanApply";


    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCE_POLICYBOSS, MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferenceEditor(Context context) {
        return getSharedPreference(context).edit();
    }

    public static String getCurrentMobileDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static MultipartBody.Part getMultipartImage(File file) {
        RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imgFile = MultipartBody.Part.createFormData("DocFile", file.getName(), imgBody);
        return imgFile;
    }

    public static MultipartBody.Part getMultipartVideo(File file) {
        RequestBody imgBody = RequestBody.create(MediaType.parse("video/*"), file);
        MultipartBody.Part imgFile = MultipartBody.Part.createFormData("video", file.getName(), imgBody);
        return imgFile;
    }

//    public static HashMap<String, String> getBody(Context context,int FbaID,String DocTyp, String docName, String DocExt) {
//        HashMap<String, String> body = new HashMap<String, String>();
//
//
//        body.put("FBAID", String.valueOf(FbaID) );
//        body.put("DocType",DocTyp);
//        body.put("DocName", docName);
//        body.put("DocExt",DocExt);
//
//        return body;
//    }


    public static HashMap<String, String> getBody(Context context, int FbaID, int DocTyp, String DocName) {
        HashMap<String, String> body = new HashMap<String, String>();


        body.put("FBAID", String.valueOf(FbaID));
        body.put("DocType", String.valueOf(DocTyp));
        body.put("DocName", DocName);


        return body;
    }


    public static File createDirIfNotExists() {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), "/FINMART");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return file;
    }

    public static File createShareDirIfNotExists() {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), "/FINMART/QUOTES");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Quotes folder");
                ret = false;
            }
        }
        return file;
    }

    public static int checkShareStatus() {
        int temp = 1;
        return temp;
    }

    public static String getLocalIpAddress(Context context) {
        String IPaddress;

        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    WIFI = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    MOBILE = true;
        }

        if (WIFI == true) {
            LOGIN_IP = GetDeviceipWiFiData(context);
            return LOGIN_IP;
        }

        if (MOBILE == true) {
            LOGIN_IP = GetDeviceipMobileData();
            return LOGIN_IP;
        }


       /* WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());*//*
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }*/
        return "";
    }

    public static String GetDeviceipMobileData() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return Formatter.formatIpAddress(inetAddress.hashCode());
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return "";
    }

    public static String GetDeviceipWiFiData(Context context) {

        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);

        @SuppressWarnings("deprecation")

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }


    public static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo pinfo;
        try {
            pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo pinfo;
        try {
            pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getDeviceId(Context context) {
        String deviceId = "";
        if (context != null)
            return Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static String getTokenId(Context context) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager != null)
            return prefManager.getToken();
        return "";
    }

    public static String getMotorUrl(Context context, String Service_Log_Unique_Id) {
        String ssid = "";
        if (new DBPersistanceController(context).getUserData().getPOSPNo() != null)
            ssid = new DBPersistanceController(context).getUserData().getPOSPNo();
        String url = Utility.QUOTE_BASE_URL;
        url = url + "buynowprivatecar/" + Utility.CLIENT_ID + "/" + Service_Log_Unique_Id + "/posp/" + ssid;
        return url;
    }

    public static String getTwoWheelerUrl(Context context, String Service_Log_Unique_Id) {

        String ssid = "";
        if (new DBPersistanceController(context).getUserData().getPOSPNo() != null)
            ssid = new DBPersistanceController(context).getUserData().getPOSPNo();
        String url = Utility.QUOTE_BASE_URL;
        url = url + "buynowTwoWheeler/" + Utility.CLIENT_ID + "/" + Service_Log_Unique_Id + "/posp/" + ssid;
        return url;
    }
}
