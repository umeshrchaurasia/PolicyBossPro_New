package com.policyboss.policybosspro;

import android.app.Application;
import android.os.Bundle;

//import com.crashlytics.android.Crashlytics;
import com.policyboss.policybosspro.analytics.AnalyticsTrackers;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();
    public static MyApplication mInstance;



    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        //   Fabric.with(this, new Crashlytics());       // temp 05 commented

        //region Realm Initialization
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("magicfinmart.realm")                // user defined name
                .schemaVersion(Utility.getVersionCode(this))

                //.migration(new RealmMigrationClass(this))
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        //endregion

        //region google analytics Initialization

        mInstance = this;
        // 05 below two line  commented
        // AnalyticsTrackers.initialize(this);
        // AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //endregion


    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }



    public void trackEvent(String category, String action, String label) {
        int FBA_ID = 0;
        DBPersistanceController dbPersistanceController = new DBPersistanceController(this);

        if (dbPersistanceController.getUserData() != null) {
            FBA_ID = dbPersistanceController.getUserData().getFBAId();
        }


        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        bundle.putString("action", action);
        bundle.putString("label", label);
        bundle.putString("User", ""+FBA_ID);

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


    }


    
}