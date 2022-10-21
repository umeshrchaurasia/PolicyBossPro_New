package com.policyboss.policybosspro.motor.twowheeler.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.policyboss.policybosspro.BaseFragment;
import com.policyboss.policybosspro.MyApplication;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.home.HomeActivity;
import com.policyboss.policybosspro.motor.privatecar.activity.ModifyQuoteActivity;
import com.policyboss.policybosspro.motor.privatecar.activity.PremiumBreakUpActivity;
import com.policyboss.policybosspro.motor.privatecar.activity.SortbyInsurerMotor;
import com.policyboss.policybosspro.motor.privatecar.adapter.AddonPopUpAdapter;
import com.policyboss.policybosspro.motor.twowheeler.activity.BikeAddQuoteActivity;
import com.policyboss.policybosspro.motor.twowheeler.adapter.BikeQuoteAdapter;
import com.policyboss.policybosspro.utility.Constants;
import com.policyboss.policybosspro.webviews.CommonWebViewActivity;
import com.policyboss.policybosspro.webviews.ShareQuoteActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.quoteapplication.QuoteApplicationController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.BikeMasterEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.SaveMotorRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.QuoteAppUpdateDeleteResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.SaveQuoteResponse;
import magicfinmart.datacomp.com.finmartserviceapi.motor.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.motor.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.motor.controller.MotorController;
import magicfinmart.datacomp.com.finmartserviceapi.motor.model.AppliedAddonsPremiumBreakup;
import magicfinmart.datacomp.com.finmartserviceapi.motor.model.CommonAddonEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.model.MobileAddOn;
import magicfinmart.datacomp.com.finmartserviceapi.motor.model.ResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.model.SummaryEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.requestentity.MotorRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.requestentity.SaveAddOnRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.response.BikePremiumResponse;
import magicfinmart.datacomp.com.finmartserviceapi.motor.response.SaveAddOnResponse;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */


public class BikeQuoteFragment extends BaseFragment implements IResponseSubcriber, BaseFragment.PopUpListener, View.OnClickListener, magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber {
    String status;
    BikePremiumResponse bikePremiumResponse, sharePremiumResponse;
    List<ResponseEntity> shareResponseEntityList;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;

    RecyclerView bikeQuoteRecycler;
    BikeQuoteAdapter mAdapter;
    MotorRequestEntity motorRequestEntity;
    Menu menuAddon;
    DBPersistanceController databaseController;
    ImageView webViewLoader;
    List<MobileAddOn> listMobileAddOn;
    TextView tvMakeModel, tvCrn, tvCount, tvRtoName;
    TextView filter;
    ImageView ivEdit;
    BikeMasterEntity carMasterEntity;
    Realm realm;
    SaveQuoteResponse.SaveQuoteEntity saveQuoteEntity;

    CheckBox chkAddon;
    NestedScrollView scrollView;
    FloatingActionButton fabrefresh;
    boolean isSync = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bike_fragment_quote, container, false);
        registerPopUp(this);
        initView(view);
        shareResponseEntityList = new ArrayList<>();
        actionModeCallback = new ActionModeCallback();
        realm = Realm.getDefaultInstance();
        databaseController = new DBPersistanceController(getActivity());
        saveQuoteEntity = new SaveQuoteResponse.SaveQuoteEntity();

        if (getArguments() != null) {
            if (getArguments().getParcelable(BikeAddQuoteActivity.BIKE_QUOTE_REQUEST) != null) {
                motorRequestEntity = getArguments().getParcelable(BikeAddQuoteActivity.BIKE_QUOTE_REQUEST);
                if (motorRequestEntity.getVehicleRequestID() != 0)
                    saveQuoteEntity.setVehicleRequestID(motorRequestEntity.getVehicleRequestID());
                initializeAdapters();
                setListener();
                updateHeader();
                fetchQuotes();
            }
        } else {
            Toast.makeText(getActivity(), "Please fill inputs", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void setListener() {
        fabrefresh.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        filter.setOnClickListener(this);
        chkAddon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (webViewLoader.getVisibility() == View.GONE) {
                    if (b) {
                        applyAllAddon();
                    } else {
                        removeAllAddon();
                    }
                } else {
                    chkAddon.setChecked(false);
                    Toast.makeText(getActivity(), "Please Wait.. Fetching all quotes", Toast.LENGTH_SHORT).show();
                }
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    fabrefresh.hide();
                } else {
                    fabrefresh.show();
                }
            }
        });
    }

    private void initView(View view) {
        scrollView = view.findViewById(R.id.scrollView);
        fabrefresh = view.findViewById(R.id.fabrefresh);
        //mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        bikeQuoteRecycler = (RecyclerView) view.findViewById(R.id.bikeQuoteRecycler);
        webViewLoader = (ImageView) view.findViewById(R.id.webViewLoader);
        Glide.with(this.getActivity()).load(R.drawable.preloader).into(webViewLoader);
        //tvPolicyExp = (TextView) view.findViewById(R.id.tvPolicyExp);
        tvRtoName = (TextView) view.findViewById(R.id.tvRtoName);
        tvMakeModel = (TextView) view.findViewById(R.id.tvMakeModel);
        //tvFuel = (TextView) view.findViewById(R.id.tvFuel);
        tvCrn = (TextView) view.findViewById(R.id.txtCrn);
        tvCount = (TextView) view.findViewById(R.id.tvCount);
        chkAddon = (CheckBox) view.findViewById(R.id.chkAddon);
        ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        filter = (TextView) view.findViewById(R.id.filter);

    }

    private void initializeAdapters() {
        listMobileAddOn = new ArrayList<MobileAddOn>();
        bikePremiumResponse = new BikePremiumResponse();

        bikeQuoteRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        bikeQuoteRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new BikeQuoteAdapter(BikeQuoteFragment.this, bikePremiumResponse);
        bikeQuoteRecycler.setAdapter(mAdapter);

    }

    private void updateHeader() {
        if (motorRequestEntity != null) {

            String fuelType = "";

            int vehicleID = 0;
            if (motorRequestEntity.getVehicle_id() != 0) {
                vehicleID = motorRequestEntity.getVehicle_id();
            } else if (motorRequestEntity.getVarid() != 0) {
                vehicleID = motorRequestEntity.getVarid();
            }

            carMasterEntity = databaseController.getBikeVarientDetails(
                    "" + vehicleID);

            if (carMasterEntity != null) {
                fuelType = carMasterEntity.getFuel_Name();
                String rtoName = fuelType + " | " + carMasterEntity.getCubic_Capacity() + "cc";
                rtoName = fuelType + " | " + carMasterEntity.getCubic_Capacity() + " cc";

                if (motorRequestEntity.getRegistration_no().contains("-AA-1234")) {
                    rtoName = rtoName + " | RTO : " + new DBPersistanceController(getActivity())
                            .getBikeRTOName(String.valueOf(motorRequestEntity.getRto_id()));
                } else {
                    //rtoName = rtoName + new DBPersistanceController(getActivity())
                    //        .getRTOCityName(String.valueOf(motorRequestEntity.getRto_id()));

                    rtoName = rtoName + " | " + motorRequestEntity.getRegistration_no();

                }
                tvRtoName.setText(rtoName);
            }
            tvCrn.setText("CRN :" + motorRequestEntity.getCrn());
        }


        if (carMasterEntity != null) {
            //tvPolicyExp.setText("" + carMasterEntity.getVariant_Name());
            //tvFuel.setText(carMasterEntity.getFuel_Name());
            tvMakeModel.setText(carMasterEntity.getMake_Name()
                    + " , " + carMasterEntity.getModel_Name()
                    + "(" + carMasterEntity.getVariant_Name() + ")");
        }
    }

    private void updateCrn() {
        if (bikePremiumResponse != null) {


            if (bikePremiumResponse.getSummary().getPB_CRN() != null) {


                String crn = "";

                if (!bikePremiumResponse.getSummary().getPB_CRN().equals("")) {
                    motorRequestEntity.setCrn(bikePremiumResponse.getSummary().getPB_CRN());
                    crn = bikePremiumResponse.getSummary().getPB_CRN();
                } else if (bikePremiumResponse.getSummary().getRequest_Core() != null
                        && !bikePremiumResponse.getSummary().getRequest_Core().getCrn().equalsIgnoreCase("")) {
                    motorRequestEntity.setCrn(bikePremiumResponse.getSummary().getRequest_Core().getCrn());
                    crn = bikePremiumResponse.getSummary().getRequest_Core().getCrn();
                }

                tvCrn.setText("CRN :" + crn);

                boolean isQuoteFetch = false;
                if (webViewLoader.getVisibility() == View.GONE) {
                    isQuoteFetch = true;
                }

                if (getActivity() != null) {
                    ((BikeAddQuoteActivity) getActivity()).updateRequest(motorRequestEntity, isQuoteFetch);
                }
            }
            if (bikePremiumResponse.getResponse() != null)
                tvCount.setText("" + bikePremiumResponse.getResponse().size() + " Results from www.policyboss.com");
            else
                tvCount.setText("0 results from policyboss.com");
        }
    }

    public void fetchQuotes() {
        showDialog();
        new MotorController(getActivity()).getMotorQuote(10, this);
    }

    public void fetchQuotesOneTime() {

        isSync = true;
        showDialog();
        new MotorController(getActivity()).getMotorQuoteOneTime(10, this);

    }

    public void rebindAdapter(BikePremiumResponse bikePremiumResponse) {
        mAdapter.setQuoteResponse(bikePremiumResponse);
        mAdapter.notifyDataSetChanged();
    }

    private void saveQuoteToServer(BikePremiumResponse response) {
        //store request and SRN to mySql
        SaveMotorRequestEntity entity = new SaveMotorRequestEntity();
        if (response.getSummary().getPB_CRN() != null && !response.getSummary().getPB_CRN().equals(""))
            motorRequestEntity.setCrn(response.getSummary().getPB_CRN());

        entity.setVehicleRequestID(String.valueOf(motorRequestEntity.getVehicleRequestID()));
        entity.setMotorRequestEntity(motorRequestEntity);
        entity.setSRN(response.getSummary().getRequest_Unique_Id());
        entity.setIsActive(1);


        DBPersistanceController db = new DBPersistanceController(getActivity());
        //entity.setFba_id(String.valueOf(new DBPersistanceController(getActivity()).getUserData().getFBAId()));
        if (motorRequestEntity.isBehalfOf() == 1) {
            entity.setCreatedByUserFbaId("0");
            if (db.getUserConstantsData().getParentid() != null && !db.getUserConstantsData().getParentid().equals("")
                    && !db.getUserConstantsData().getParentid().equals("0")) {
                entity.setFba_id("" + Integer.parseInt(db.getUserConstantsData().getParentid()));
            } else {
                entity.setFba_id("" + db.getUserData().getFBAId());
            }
        } else {
            entity.setCreatedByUserFbaId(String.valueOf(db.getUserData().getFBAId()));
            entity.setFba_id(String.valueOf(motorRequestEntity.getFba_id()));

        }

        if (saveQuoteEntity != null) {
            if (saveQuoteEntity.getVehicleRequestID() != 0)
                entity.setVehicleRequestID(String.valueOf(saveQuoteEntity.getVehicleRequestID()));
        }
        new QuoteApplicationController(getActivity()).saveQuote(entity, this);
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof BikePremiumResponse) {


            bikePremiumResponse = (BikePremiumResponse) response;

            if (!motorRequestEntity.getCrn().equalsIgnoreCase("")) {
                bikePremiumResponse.getSummary().setPB_CRN(motorRequestEntity.getCrn());
            }

            rebindAdapter(bikePremiumResponse);
            updateCrn();

            //save quote to our server.

            if (getActivity() != null) {
               /* if (bikePremiumResponse.getSummary().getStatusX().equals("complete")
                        || Constants.getSharedPreference(getActivity()).getInt(Utility.QUOTE_COUNTER, 0) >= MotorController.NO_OF_SERVER_HITS) {
*/
                if (Constants.getSharedPreference(getActivity())
                        .getInt(Utility.QUOTE_COUNTER, 0) > MotorController.NO_OF_SERVER_HITS) {
                    Collections.sort(bikePremiumResponse.getResponse(), new SortbyInsurerMotor());
                    webViewLoader.setVisibility(View.GONE);
                    updateCrn();
                    saveQuoteToServer(bikePremiumResponse);
                    new AsyncAddon().execute();
                    clearActionMode();

                } else {
                    webViewLoader.setVisibility(View.VISIBLE);

                }
            }
            if (isSync == true) {
                webViewLoader.setVisibility(View.GONE);
                isSync = false;
                Collections.sort(bikePremiumResponse.getResponse(), new SortbyInsurerMotor());
                rebindAdapter(bikePremiumResponse);
            }
        } else if (response instanceof SaveAddOnResponse) {

        }
    }

    @Override
    public void OnSuccess(magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse response, String message) {
        if (response instanceof QuoteAppUpdateDeleteResponse) {

        } else if (response instanceof SaveQuoteResponse) {
            if (response.getStatusNo() == 0) {
                saveQuoteEntity = ((SaveQuoteResponse) response).getMasterData().get(0);
                motorRequestEntity.setVehicleRequestID(saveQuoteEntity.getVehicleRequestID());
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        webViewLoader.setVisibility(View.GONE);
        if (getActivity() != null)
            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.add_on_menu, menu);
        menuAddon = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void sharePDF() {
        if (Utility.checkShareStatus(getActivity()) == 1) {
            if (webViewLoader.getVisibility() != View.VISIBLE) {
                Intent intent = new Intent(getActivity(), ShareQuoteActivity.class);
                intent.putExtra(Constants.SHARE_ACTIVITY_NAME, "BIKE_ALL_QUOTE");
                intent.putExtra("RESPONSE", applyAddonsForShare(bikePremiumResponse));
                intent.putExtra("BIKENAME", carMasterEntity);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Please wait.., Fetching all quotes", Toast.LENGTH_SHORT).show();
            }
        } else {
            openPopUp(ivEdit, "Message", "Your POSP status is INACTIVE", "OK", true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;

            case R.id.share:
                sharePDF();
                return true;

            case R.id.action_home:
                if (webViewLoader.getVisibility() != View.VISIBLE) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("MarkTYPE", "FROM_HOME");
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                } else {
                    Toast.makeText(getActivity(), "Please Wait.. Fetching all quotes", Toast.LENGTH_SHORT).show();
                    return false;
                }


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //endregion

    private void openPopUp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Add-on :");

        RecyclerView rvAddOne;
        Button btnOk, btnCancel;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_addon_popup, null);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        rvAddOne = (RecyclerView) dialogView.findViewById(R.id.rvAddOne);
        rvAddOne.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvAddOne.setLayoutManager(layoutManager);

        final AddonPopUpAdapter popUpAdapter = new AddonPopUpAdapter(getActivity(), listMobileAddOn);
        rvAddOne.setAdapter(popUpAdapter);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMobileAddOn = popUpAdapter.getUpdateMobileAddonList();
                // applyAddons();
                applyPositiveAddons(listMobileAddOn);
                updateAddonToserver(listMobileAddOn);
                alertDialog.dismiss();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void updateAddonToserver(List<MobileAddOn> addOnList) {
        SaveAddOnRequestEntity entity = new SaveAddOnRequestEntity();
        for (int i = 0; i < addOnList.size(); i++) {
            MobileAddOn mobileAddOn = addOnList.get(i);

            if (mobileAddOn.getAddonKey().matches("addon_zero_dep_cover") && mobileAddOn.isSelected) {
                entity.setAddon_zero_dep_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_road_assist_cover") && mobileAddOn.isSelected) {
                entity.setAddon_road_assist_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_ncb_protection_cover") && mobileAddOn.isSelected) {
                entity.setAddon_ncb_protection_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_engine_protector_cover") && mobileAddOn.isSelected) {
                entity.setAddon_engine_protector_cover("yes");
            }

            if (mobileAddOn.getAddonKey().matches("addon_invoice_price_cover") && mobileAddOn.isSelected) {
                entity.setAddon_invoice_price_cover("yes");
            }

            if (mobileAddOn.getAddonKey().matches("addon_key_lock_cover") && mobileAddOn.isSelected) {
                entity.setAddon_key_lock_cover("yes");
            }

            if (mobileAddOn.getAddonKey().matches("addon_consumable_cover") && mobileAddOn.isSelected) {
                entity.setAddon_consumable_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_daily_allowance_cover") && mobileAddOn.isSelected) {
                entity.setAddon_daily_allowance_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_windshield_cover") && mobileAddOn.isSelected) {
                entity.setAddon_windshield_cover("yes");
            }

            if (mobileAddOn.getAddonKey().matches("addon_passenger_assistance_cover") && mobileAddOn.isSelected) {
                entity.setAddon_passenger_assistance_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_tyre_coverage_cover") && mobileAddOn.isSelected) {
                entity.setAddon_tyre_coverage_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_personal_belonging_loss_cover") && mobileAddOn.isSelected) {
                entity.setAddon_personal_belonging_loss_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_inconvenience_allowance_cover") && mobileAddOn.isSelected) {
                entity.setAddon_inconvenience_allowance_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_medical_expense_cover") && mobileAddOn.isSelected) {
                entity.setAddon_medical_expense_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_hospital_cash_cover") && mobileAddOn.isSelected) {
                entity.setAddon_hospital_cash_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_ambulance_charge_cover") && mobileAddOn.isSelected) {
                entity.setAddon_ambulance_charge_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_rodent_bite_cover") && mobileAddOn.isSelected) {
                entity.setAddon_rodent_bite_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_losstime_protection_cover") && mobileAddOn.isSelected) {
                entity.setAddon_losstime_protection_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_hydrostatic_lock_cover") && mobileAddOn.isSelected) {
                entity.setAddon_hydrostatic_lock_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_guaranteed_auto_protection_cover") && mobileAddOn.isSelected) {
                entity.setAddon_guaranteed_auto_protection_cover("yes");
            }
            if (mobileAddOn.getAddonKey().matches("addon_final_premium") && mobileAddOn.isSelected) {
                entity.setAddon_final_premium("yes");
            }
            /*if (mobileAddOn.getAddonKey().matches("search_reference_number") && mobileAddOn.isSelected) {
                entity.setSearch_reference_number("yes");
            }*/
        }

        entity.setSearch_reference_number(Constants.getSharedPreference(getActivity()).getString(Utility.BIKEQUOTE_UNIQUEID, ""));


        new MotorController(getActivity()).saveAddOn(entity, this);
    }

    private void applyPositiveAddons(List<MobileAddOn> addOnList) {

        for (ResponseEntity entity : bikePremiumResponse.getResponse()) { // itrate for each quote
            double addonValue = 0;
            entity.setAddonApplied(false);
            entity.setListAppliedAddons(null);

            if (entity.getAddon_List() != null) {
                List<AppliedAddonsPremiumBreakup> listAppliedAddonPremium =
                        new ArrayList<AppliedAddonsPremiumBreakup>();// list of applied addon

                //region list of available addons
                for (int i = 0; i < addOnList.size(); i++) {

                    MobileAddOn mobileAddOn = addOnList.get(i);
                    // check if addon is selected
                    if (!mobileAddOn.isSelected()) {
                        continue;
                    }

                    //region addon_zero_dep_cover
                    if (mobileAddOn.getAddonKey().matches("addon_zero_dep_cover")) {

                        if (entity.getAddon_List().getAddon_zero_dep_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_zero_dep_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_zero_dep_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_zero_dep_cover();
                        }

                    }
                    //endregion

                    //region addon_road_assist_cover

                    if (mobileAddOn.getAddonKey().matches("addon_road_assist_cover")) {

                        if (entity.getAddon_List().getAddon_road_assist_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_road_assist_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_road_assist_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_road_assist_cover();
                        }

                    }
                    //endregion

                    //region addon_ncb_protection_cover

                    if (mobileAddOn.getAddonKey().matches("addon_ncb_protection_cover")) {

                        if (entity.getAddon_List().getAddon_ncb_protection_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_ncb_protection_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_ncb_protection_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_ncb_protection_cover();
                        }

                    }
                    //endregion

                    //region addon_engine_protector_cover

                    if (mobileAddOn.getAddonKey().matches("addon_engine_protector_cover")) {

                        if (entity.getAddon_List().getAddon_engine_protector_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_engine_protector_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_engine_protector_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_engine_protector_cover();
                        }

                    }
                    //endregion

                    //region addon_invoice_price_cover

                    if (mobileAddOn.getAddonKey().matches("addon_invoice_price_cover")) {

                        if (entity.getAddon_List().getAddon_invoice_price_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_invoice_price_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_invoice_price_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_invoice_price_cover();
                        }

                    }
                    //endregion

                    //region addon_key_lock_cover

                    if (mobileAddOn.getAddonKey().matches("addon_key_lock_cover")) {

                        if (entity.getAddon_List().getAddon_key_lock_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_key_lock_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_key_lock_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_key_lock_cover();
                        }

                    }
                    //endregion

                    //region addon_consumable_cover

                    if (mobileAddOn.getAddonKey().matches("addon_consumable_cover")) {

                        if (entity.getAddon_List().getAddon_consumable_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_consumable_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_consumable_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_consumable_cover();
                        }

                    }
                    //endregion

                    //region addon_daily_allowance_cover

                    if (mobileAddOn.getAddonKey().matches("addon_daily_allowance_cover")) {

                        if (entity.getAddon_List().getAddon_daily_allowance_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_daily_allowance_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_daily_allowance_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_daily_allowance_cover();
                        }

                    }
                    //endregion

                    //region addon_windshield_cover

                    if (mobileAddOn.getAddonKey().matches("addon_windshield_cover")) {

                        if (entity.getAddon_List().getAddon_windshield_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_windshield_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_windshield_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_windshield_cover();
                        }

                    }
                    //endregion

                    //region addon_passenger_assistance_cover

                    if (mobileAddOn.getAddonKey().matches("addon_passenger_assistance_cover")) {

                        if (entity.getAddon_List().getAddon_passenger_assistance_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_passenger_assistance_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_passenger_assistance_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_passenger_assistance_cover();
                        }

                    }
                    //endregion

                    //region addon_tyre_coverage_cover

                    if (mobileAddOn.getAddonKey().matches("addon_tyre_coverage_cover")) {

                        if (entity.getAddon_List().getAddon_tyre_coverage_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_tyre_coverage_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_tyre_coverage_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_tyre_coverage_cover();
                        }

                    }
                    //endregion

                    //region addon_personal_belonging_loss_cover

                    if (mobileAddOn.getAddonKey().matches("addon_personal_belonging_loss_cover")) {

                        if (entity.getAddon_List().getAddon_personal_belonging_loss_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_personal_belonging_loss_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_personal_belonging_loss_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_personal_belonging_loss_cover();
                        }

                    }
                    //endregion

                    //region addon_inconvenience_allowance_cover

                    if (mobileAddOn.getAddonKey().matches("addon_inconvenience_allowance_cover")) {

                        if (entity.getAddon_List().getAddon_inconvenience_allowance_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_inconvenience_allowance_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_inconvenience_allowance_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_inconvenience_allowance_cover();
                        }

                    }
                    //endregion

                    //region addon_medical_expense_cover

                    if (mobileAddOn.getAddonKey().matches("addon_medical_expense_cover")) {

                        if (entity.getAddon_List().getAddon_medical_expense_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_medical_expense_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_medical_expense_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_medical_expense_cover();
                        }

                    }
                    //endregion

                    //region addon_hospital_cash_cover

                    if (mobileAddOn.getAddonKey().matches("addon_hospital_cash_cover")) {

                        if (entity.getAddon_List().getAddon_hospital_cash_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_hospital_cash_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_hospital_cash_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_hospital_cash_cover();
                        }

                    }
                    //endregion

                    //region addon_ambulance_charge_cover

                    if (mobileAddOn.getAddonKey().matches("addon_ambulance_charge_cover")) {

                        if (entity.getAddon_List().getAddon_ambulance_charge_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_ambulance_charge_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_ambulance_charge_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_ambulance_charge_cover();
                        }

                    }
                    //endregion

                    //region addon_rodent_bite_cover

                    if (mobileAddOn.getAddonKey().matches("addon_rodent_bite_cover")) {

                        if (entity.getAddon_List().getAddon_rodent_bite_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_rodent_bite_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_rodent_bite_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_rodent_bite_cover();
                        }

                    }
                    //endregion

                    //region addon_losstime_protection_cover

                    if (mobileAddOn.getAddonKey().matches("addon_losstime_protection_cover")) {

                        if (entity.getAddon_List().getAddon_losstime_protection_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_losstime_protection_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_losstime_protection_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_losstime_protection_cover();
                        }

                    }
                    //endregion

                    //region addon_hydrostatic_lock_cover

                    if (mobileAddOn.getAddonKey().matches("addon_hydrostatic_lock_cover")) {

                        if (entity.getAddon_List().getAddon_hydrostatic_lock_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_hydrostatic_lock_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_hydrostatic_lock_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_hydrostatic_lock_cover();
                        }

                    }
                    //endregion

                    //region addon_guaranteed_auto_protection_cover

                    if (mobileAddOn.getAddonKey().matches("addon_guaranteed_auto_protection_cover")) {

                        if (entity.getAddon_List().getAddon_guaranteed_auto_protection_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_guaranteed_auto_protection_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_guaranteed_auto_protection_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_guaranteed_auto_protection_cover();
                        }

                    }
                    //endregion

                    //region addon_final_premium

                    if (mobileAddOn.getAddonKey().matches("addon_final_premium")) {

                        if (entity.getAddon_List().getAddon_final_premium() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_final_premium"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_final_premium());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_final_premium();
                        }

                    }
                    //endregion

                }
                //endregion

                //region update response entity
                try {
                    double finalPremWithoutGST = addonValue + Double.parseDouble(entity.getPremium_Breakup().getNet_premium());
                    entity.setFinal_premium_without_addon("" + finalPremWithoutGST);
                    entity.setTotalGST("" + finalPremWithoutGST * Constants.GST);
                    entity.setTotalAddonAplied("" + addonValue);
                    double finalPremWithGST = finalPremWithoutGST + (finalPremWithoutGST * Constants.GST);
                    entity.setFinal_premium_with_addon("" + finalPremWithGST);
                    entity.setListAppliedAddons(listAppliedAddonPremium);
                } catch (Exception e) {

                }
                //endregion
            }
        }
        Collections.sort(bikePremiumResponse.getResponse(), new SortbyInsurerMotor());
        rebindAdapter(bikePremiumResponse);
    }

    private void applyAllAddon() {
        List<MobileAddOn> mobileAddOnAll = listMobileAddOn;
        for (int i = 0; i < mobileAddOnAll.size(); i++) {
            mobileAddOnAll.get(i).setSelected(true);
        }
        applyPositiveAddons(mobileAddOnAll);
        updateAddonToserver(mobileAddOnAll);
    }

    private void removeAllAddon() {
        List<MobileAddOn> mobileAddOnAll = listMobileAddOn;
        for (int i = 0; i < mobileAddOnAll.size(); i++) {
            mobileAddOnAll.get(i).setSelected(false);
        }
        applyPositiveAddons(mobileAddOnAll);
        updateAddonToserver(mobileAddOnAll);
    }

    public void redirectToBuy(ResponseEntity entity) {
        MyApplication.getInstance().trackEvent(Constants.TWO_WHEELER, "BUY QUOTE TWO WHEELER", "BUY QUOTE TWO WHEELER");
        if (Utility.checkShareStatus(getActivity()) == 1) {
            if (webViewLoader.getVisibility() == View.GONE) {


                int fbaID = new DBPersistanceController(getActivity()).getUserData().getFBAId();

                String url = "http://qa.policyboss.com/";
                //String url = "http://policyboss.com/";
                String title = "";
                String name = "";
                url = url + "buynowTwoWheeler/4/" + entity.getService_Log_Unique_Id() + "/nonposp/" + fbaID;
                title = "Two Wheeler";

                //convert quote to application server
                String imgPath = "http://qa.policyboss.com/Images/insurer_logo/" + entity.getInsurer().getInsurer_Logo_Name();

                new QuoteApplicationController(getActivity()).convertQuoteToApp(
                        "" + saveQuoteEntity.getVehicleRequestID(),
                        entity.getInsurer_Id(), imgPath,
                        this);

                startActivity(new Intent(getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", Utility.getTwoWheelerUrl(getActivity(), entity.getService_Log_Unique_Id()))
                        .putExtra("NAME", name)
                        .putExtra("TITLE", title));
            } else {

                Toast.makeText(getActivity(), "Please wait.., Fetching all quotes", Toast.LENGTH_SHORT).show();
            }
        } else {
            openPopUp(ivEdit, "Message", "Your POSP status is INACTIVE", "OK", true);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEdit:
                //finish();
                clearActionMode();
                ((BikeAddQuoteActivity) getActivity()).redirectInput(motorRequestEntity);
                break;
            case R.id.filter:
                clearActionMode();
                if (bikePremiumResponse.getResponse() != null && bikePremiumResponse.getResponse().size() != 0) {
                    if (webViewLoader.getVisibility() != View.VISIBLE) {
                        chkAddon.setChecked(false);
                        startActivityForResult(new Intent(getActivity(), ModifyQuoteActivity.class)
                                .putExtra("SUMMARY", bikePremiumResponse.getSummary())
                                .putExtra("BIKE_REQUEST", motorRequestEntity), 1000);
                    } else {
                        Toast.makeText(getActivity(), "Please wait.., Fetching all quotes", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No quotes found..", Toast.LENGTH_SHORT).show();
                    webViewLoader.setVisibility(View.GONE);
                }
                break;
            case R.id.fabrefresh:
                clearActionMode();
                if (webViewLoader.getVisibility() != View.VISIBLE) {
                    chkAddon.setChecked(false);
                    removeAllAddon();
                    fetchQuotesOneTime();
                }
                break;

        }
    }


    public static boolean isShowing = false;

    public void redirectToPopUpPremium(ResponseEntity entity, SummaryEntity summaryEntity, String IDV) {
        if (webViewLoader.getVisibility() == View.GONE) {
            //if (!isShowing) {
            Intent intent = new Intent(getActivity(), PremiumBreakUpActivity.class);
            intent.putExtra("VEHICLE_REQUEST_ID", "" + saveQuoteEntity.getVehicleRequestID());
            intent.putExtra("RESPONSE_BIKE", entity);
            intent.putParcelableArrayListExtra("MOBILE_ADDON", (ArrayList<? extends Parcelable>) listMobileAddOn);
            intent.putExtra("SUMMARY", summaryEntity);
            startActivityForResult(intent, 00000);
            isShowing = true;
            //}
        } else {
            Toast.makeText(getActivity(), "Please wait.., Fetching all quotes", Toast.LENGTH_SHORT).show();
        }
        /*

        startActivity(new Intent(getActivity(), PremiumBreakUpActivity.class)
                .putExtra("VEHICLE_REQUEST_ID", "" + saveQuoteEntity.getVehicleRequestID())
                .putExtra("RESPONSE_BIKE", entity)
                .putParcelableArrayListExtra("MOBILE_ADDON", (ArrayList<? extends Parcelable>) listMobileAddOn)
                .putExtra("SUMMARY", summaryEntity));*/

    }

    class AsyncAddon extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            listMobileAddOn.clear();
            CommonAddonEntity entity = bikePremiumResponse.getSummary().getCommon_Addon();
            if (entity.getAddon_ambulance_charge_cover() != null) {
                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_ambulance_charge_cover"));
                mobileAddOn.setMin(entity.getAddon_ambulance_charge_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_ambulance_charge_cover().getMax());
                mobileAddOn.setAddonKey("addon_ambulance_charge_cover");
                // item.add(dbController.getAddonName("addon_ambulance_charge_cover"));
                listMobileAddOn.add(mobileAddOn);
            }
            if (entity.getAddon_consumable_cover() != null) {
                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_consumable_cover"));
                mobileAddOn.setMin(entity.getAddon_consumable_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_consumable_cover().getMax());
                mobileAddOn.setAddonKey("addon_consumable_cover");
                listMobileAddOn.add(mobileAddOn);
            }
            if (entity.getAddon_daily_allowance_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_daily_allowance_cover"));
                mobileAddOn.setMin(entity.getAddon_daily_allowance_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_daily_allowance_cover().getMax());
                mobileAddOn.setAddonKey("addon_daily_allowance_cover");
                listMobileAddOn.add(mobileAddOn);

            }

            if (entity.getAddon_engine_protector_cover() != null) {
                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_engine_protector_cover"));
                mobileAddOn.setMin(entity.getAddon_engine_protector_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_engine_protector_cover().getMax());
                mobileAddOn.setAddonKey("addon_engine_protector_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_hospital_cash_cover() != null) {
                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_hospital_cash_cover"));
                mobileAddOn.setMin(entity.getAddon_hospital_cash_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_hospital_cash_cover().getMax());
                mobileAddOn.setAddonKey("addon_hospital_cash_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_hydrostatic_lock_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_hydrostatic_lock_cover"));
                mobileAddOn.setMin(entity.getAddon_hydrostatic_lock_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_hydrostatic_lock_cover().getMax());
                mobileAddOn.setAddonKey("addon_hydrostatic_lock_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_inconvenience_allowance_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_inconvenience_allowance_cover"));
                mobileAddOn.setMin(entity.getAddon_inconvenience_allowance_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_inconvenience_allowance_cover().getMax());
                mobileAddOn.setAddonKey("addon_inconvenience_allowance_cover");
                listMobileAddOn.add(mobileAddOn);

            }


            if (entity.getAddon_invoice_price_cover() != null) {
                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_invoice_price_cover"));
                mobileAddOn.setMin(entity.getAddon_invoice_price_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_invoice_price_cover().getMax());
                mobileAddOn.setAddonKey("addon_invoice_price_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_key_lock_cover() != null) {
                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_key_lock_cover"));
                mobileAddOn.setMin(entity.getAddon_key_lock_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_key_lock_cover().getMax());
                mobileAddOn.setAddonKey("addon_key_lock_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_losstime_protection_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_losstime_protection_cover"));
                mobileAddOn.setMin(entity.getAddon_losstime_protection_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_losstime_protection_cover().getMax());
                mobileAddOn.setAddonKey("addon_losstime_protection_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_medical_expense_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_medical_expense_cover"));
                mobileAddOn.setMin(entity.getAddon_medical_expense_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_medical_expense_cover().getMax());
                mobileAddOn.setAddonKey("addon_medical_expense_cover");
                listMobileAddOn.add(mobileAddOn);

            }

            if (entity.getAddon_ncb_protection_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_ncb_protection_cover"));
                mobileAddOn.setMin(entity.getAddon_ncb_protection_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_ncb_protection_cover().getMax());
                mobileAddOn.setAddonKey("addon_ncb_protection_cover");
                listMobileAddOn.add(mobileAddOn);


            }

            if (entity.getAddon_passenger_assistance_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_passenger_assistance_cover"));
                mobileAddOn.setMin(entity.getAddon_passenger_assistance_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_passenger_assistance_cover().getMax());
                mobileAddOn.setAddonKey("addon_passenger_assistance_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_personal_belonging_loss_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_personal_belonging_loss_cover"));
                mobileAddOn.setMin(entity.getAddon_personal_belonging_loss_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_personal_belonging_loss_cover().getMax());
                mobileAddOn.setAddonKey("addon_personal_belonging_loss_cover");
                listMobileAddOn.add(mobileAddOn);
            }

            if (entity.getAddon_road_assist_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_road_assist_cover"));
                mobileAddOn.setMin(entity.getAddon_road_assist_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_road_assist_cover().getMax());
                mobileAddOn.setAddonKey("addon_road_assist_cover");
                listMobileAddOn.add(mobileAddOn);
            }
            if (entity.getAddon_rodent_bite_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_rodent_bite_cover"));
                mobileAddOn.setMin(entity.getAddon_rodent_bite_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_rodent_bite_cover().getMax());
                mobileAddOn.setAddonKey("addon_rodent_bite_cover");
                listMobileAddOn.add(mobileAddOn);

            }

            if (entity.getAddon_tyre_coverage_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_tyre_coverage_cover"));
                mobileAddOn.setMin(entity.getAddon_tyre_coverage_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_tyre_coverage_cover().getMax());
                mobileAddOn.setAddonKey("addon_tyre_coverage_cover");
                listMobileAddOn.add(mobileAddOn);

            }

            if (entity.getAddon_windshield_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_windshield_cover"));
                mobileAddOn.setMin(entity.getAddon_windshield_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_windshield_cover().getMax());
                mobileAddOn.setAddonKey("addon_windshield_cover");
                listMobileAddOn.add(mobileAddOn);

            }

            if (entity.getAddon_zero_dep_cover() != null) {

                MobileAddOn mobileAddOn = new MobileAddOn();
                mobileAddOn.setAddonName(databaseController.getAddonName("addon_zero_dep_cover"));
                mobileAddOn.setMin(entity.getAddon_zero_dep_cover().getMin());
                mobileAddOn.setMax(entity.getAddon_zero_dep_cover().getMax());
                mobileAddOn.setAddonKey("addon_zero_dep_cover");
                listMobileAddOn.add(mobileAddOn);

                //item.add(dbController.getAddonName("addon_zero_dep_cover"));
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (1000): {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getParcelableExtra("MODIFY") != null) {
                        //getQuoteParameterBundle((MotorRequestEntity) data.getParcelableExtra("MODIFY"));
                        motorRequestEntity = (MotorRequestEntity) data.getParcelableExtra("MODIFY");
                        ((BikeAddQuoteActivity) getActivity()).modifyQuote(motorRequestEntity);
                        updateHeader();
                        fetchQuotes();
                    }
                }
                break;
            }
            case 00000:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getParcelableExtra("PREMIUM") != null) {
                        ResponseEntity entity = (ResponseEntity) data.getParcelableExtra("PREMIUM");

                        //1 apply final values
                        //2 change add on list
                        //3 rebind list
                        for (int i = 0; i < bikePremiumResponse.getResponse().size(); i++) {
                            ResponseEntity responseEntity = bikePremiumResponse.getResponse().get(i);
                            if (responseEntity.getInsurer_Id().equalsIgnoreCase(entity.getInsurer_Id())) {
                                responseEntity.setFinal_premium_with_addon(entity.getFinal_premium_with_addon());
                                responseEntity.setFinal_premium_without_addon(entity.getFinal_premium_without_addon());
                                responseEntity.setTotalAddonAplied(entity.getTotalAddonAplied());
                                responseEntity.setTotalGST(entity.getTotalGST());
                                responseEntity.setPremiumBreakUpAddonEntities(entity.getPremiumBreakUpAddonEntities());
                                bikePremiumResponse.getResponse().set(i, responseEntity);
                                break;
                            }
                        }

                        rebindAdapter(bikePremiumResponse);
                        isShowing = false;
                    }
                }
                break;
        }
    }

    private BikePremiumResponse applyAddonsForShare(BikePremiumResponse bikePremiumResponse) {
        List<MobileAddOn> addOnList = listMobileAddOn;
        for (int i = 0; i < addOnList.size(); i++) {
            addOnList.get(i).setSelected(true);
        }
        for (ResponseEntity entity : bikePremiumResponse.getResponse()) { // itrate for each quote
            double addonValue = 0;
            entity.setAddonApplied(false);
            entity.setListAppliedAddons(null);

            if (entity.getAddon_List() != null) {
                List<AppliedAddonsPremiumBreakup> listAppliedAddonPremium =
                        new ArrayList<AppliedAddonsPremiumBreakup>();// list of applied addon

                //region list of available addons
                for (int i = 0; i < addOnList.size(); i++) {

                    MobileAddOn mobileAddOn = addOnList.get(i);
                    // check if addon is selected
                    if (!mobileAddOn.isSelected()) {
                        continue;
                    }

                    //region addon_zero_dep_cover
                    if (mobileAddOn.getAddonKey().matches("addon_zero_dep_cover")) {

                        if (entity.getAddon_List().getAddon_zero_dep_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_zero_dep_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_zero_dep_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_zero_dep_cover();
                        }

                    }
                    //endregion

                    //region addon_road_assist_cover

                    if (mobileAddOn.getAddonKey().matches("addon_road_assist_cover")) {

                        if (entity.getAddon_List().getAddon_road_assist_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_road_assist_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_road_assist_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_road_assist_cover();
                        }

                    }
                    //endregion

                    //region addon_ncb_protection_cover

                    if (mobileAddOn.getAddonKey().matches("addon_ncb_protection_cover")) {

                        if (entity.getAddon_List().getAddon_ncb_protection_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_ncb_protection_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_ncb_protection_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_ncb_protection_cover();
                        }

                    }
                    //endregion

                    //region addon_engine_protector_cover

                    if (mobileAddOn.getAddonKey().matches("addon_engine_protector_cover")) {

                        if (entity.getAddon_List().getAddon_engine_protector_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_engine_protector_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_engine_protector_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_engine_protector_cover();
                        }

                    }
                    //endregion

                    //region addon_invoice_price_cover

                    if (mobileAddOn.getAddonKey().matches("addon_invoice_price_cover")) {

                        if (entity.getAddon_List().getAddon_invoice_price_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_invoice_price_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_invoice_price_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_invoice_price_cover();
                        }

                    }
                    //endregion

                    //region addon_key_lock_cover

                    if (mobileAddOn.getAddonKey().matches("addon_key_lock_cover")) {

                        if (entity.getAddon_List().getAddon_key_lock_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_key_lock_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_key_lock_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_key_lock_cover();
                        }

                    }
                    //endregion

                    //region addon_consumable_cover

                    if (mobileAddOn.getAddonKey().matches("addon_consumable_cover")) {

                        if (entity.getAddon_List().getAddon_consumable_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_consumable_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_consumable_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_consumable_cover();
                        }

                    }
                    //endregion

                    //region addon_daily_allowance_cover

                    if (mobileAddOn.getAddonKey().matches("addon_daily_allowance_cover")) {

                        if (entity.getAddon_List().getAddon_daily_allowance_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_daily_allowance_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_daily_allowance_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_daily_allowance_cover();
                        }

                    }
                    //endregion

                    //region addon_windshield_cover

                    if (mobileAddOn.getAddonKey().matches("addon_windshield_cover")) {

                        if (entity.getAddon_List().getAddon_windshield_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_windshield_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_windshield_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_windshield_cover();
                        }

                    }
                    //endregion

                    //region addon_passenger_assistance_cover

                    if (mobileAddOn.getAddonKey().matches("addon_passenger_assistance_cover")) {

                        if (entity.getAddon_List().getAddon_passenger_assistance_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_passenger_assistance_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_passenger_assistance_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_passenger_assistance_cover();
                        }

                    }
                    //endregion

                    //region addon_tyre_coverage_cover

                    if (mobileAddOn.getAddonKey().matches("addon_tyre_coverage_cover")) {

                        if (entity.getAddon_List().getAddon_tyre_coverage_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_tyre_coverage_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_tyre_coverage_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_tyre_coverage_cover();
                        }

                    }
                    //endregion

                    //region addon_personal_belonging_loss_cover

                    if (mobileAddOn.getAddonKey().matches("addon_personal_belonging_loss_cover")) {

                        if (entity.getAddon_List().getAddon_personal_belonging_loss_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_personal_belonging_loss_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_personal_belonging_loss_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_personal_belonging_loss_cover();
                        }

                    }
                    //endregion

                    //region addon_inconvenience_allowance_cover

                    if (mobileAddOn.getAddonKey().matches("addon_inconvenience_allowance_cover")) {

                        if (entity.getAddon_List().getAddon_inconvenience_allowance_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_inconvenience_allowance_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_inconvenience_allowance_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_inconvenience_allowance_cover();
                        }

                    }
                    //endregion

                    //region addon_medical_expense_cover

                    if (mobileAddOn.getAddonKey().matches("addon_medical_expense_cover")) {

                        if (entity.getAddon_List().getAddon_medical_expense_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_medical_expense_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_medical_expense_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_medical_expense_cover();
                        }

                    }
                    //endregion

                    //region addon_hospital_cash_cover

                    if (mobileAddOn.getAddonKey().matches("addon_hospital_cash_cover")) {

                        if (entity.getAddon_List().getAddon_hospital_cash_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_hospital_cash_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_hospital_cash_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_hospital_cash_cover();
                        }

                    }
                    //endregion

                    //region addon_ambulance_charge_cover

                    if (mobileAddOn.getAddonKey().matches("addon_ambulance_charge_cover")) {

                        if (entity.getAddon_List().getAddon_ambulance_charge_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_ambulance_charge_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_ambulance_charge_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_ambulance_charge_cover();
                        }

                    }
                    //endregion

                    //region addon_rodent_bite_cover

                    if (mobileAddOn.getAddonKey().matches("addon_rodent_bite_cover")) {

                        if (entity.getAddon_List().getAddon_rodent_bite_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_rodent_bite_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_rodent_bite_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_rodent_bite_cover();
                        }

                    }
                    //endregion

                    //region addon_losstime_protection_cover

                    if (mobileAddOn.getAddonKey().matches("addon_losstime_protection_cover")) {

                        if (entity.getAddon_List().getAddon_losstime_protection_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_losstime_protection_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_losstime_protection_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_losstime_protection_cover();
                        }

                    }
                    //endregion

                    //region addon_hydrostatic_lock_cover

                    if (mobileAddOn.getAddonKey().matches("addon_hydrostatic_lock_cover")) {

                        if (entity.getAddon_List().getAddon_hydrostatic_lock_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_hydrostatic_lock_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_hydrostatic_lock_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_hydrostatic_lock_cover();
                        }

                    }
                    //endregion

                    //region addon_guaranteed_auto_protection_cover

                    if (mobileAddOn.getAddonKey().matches("addon_guaranteed_auto_protection_cover")) {

                        if (entity.getAddon_List().getAddon_guaranteed_auto_protection_cover() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_guaranteed_auto_protection_cover"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_guaranteed_auto_protection_cover());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_guaranteed_auto_protection_cover();
                        }

                    }
                    //endregion

                    //region addon_final_premium

                    if (mobileAddOn.getAddonKey().matches("addon_final_premium")) {

                        if (entity.getAddon_List().getAddon_final_premium() != 0) {
                            entity.setAddonApplied(true);
                            AppliedAddonsPremiumBreakup appliedAddonsPremiumBreakup = new AppliedAddonsPremiumBreakup();
                            appliedAddonsPremiumBreakup.setAddonName(databaseController.getAddonName("addon_final_premium"));
                            appliedAddonsPremiumBreakup.setPriceAddon(entity.getAddon_List().getAddon_final_premium());
                            listAppliedAddonPremium.add(appliedAddonsPremiumBreakup);
                            addonValue = addonValue + entity.getAddon_List().getAddon_final_premium();
                        }

                    }
                    //endregion

                }
                //endregion

                //region update response entity
                double finalPremWithoutGST = addonValue + Double.parseDouble(entity.getPremium_Breakup().getNet_premium());
                entity.setFinal_premium_without_addon("" + finalPremWithoutGST);
                entity.setTotalGST("" + finalPremWithoutGST * Constants.GST);
                entity.setTotalAddonAplied("" + addonValue);
                double finalPremWithGST = finalPremWithoutGST + (finalPremWithoutGST * Constants.GST);
                entity.setFinal_premium_with_addon("" + finalPremWithGST);
                entity.setListAppliedAddons(listAppliedAddonPremium);
                //endregion
            }
        }

        return bikePremiumResponse;
    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {
        if (view.getId() == R.id.ivShare) {
            dialog.cancel();
        } else if (view.getId() == R.id.ivEdit) {
            dialog.cancel();
        }
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        if (view.getId() == R.id.ivShare) {
            dialog.cancel();
        } else if (view.getId() == R.id.ivEdit) {
            dialog.cancel();
        }
    }


    public void addRemoveShare(ResponseEntity responseEntity, boolean isSelected) {
        if (isSelected) {

            shareResponseEntityList.add(responseEntity);

            if (actionMode == null) {
                actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
            }
        } else {
            //remove item from list
            for (Iterator<ResponseEntity> iter = shareResponseEntityList.listIterator(); iter.hasNext(); ) {
                ResponseEntity a = iter.next();
                if (a.getInsurer().getInsurer_ID() == responseEntity.getInsurer().getInsurer_ID()) {
                    iter.remove();
                }
            }
        }
        actionModeRefresh();
    }

    public void actionModeRefresh() {
        if (actionMode != null) {
            if (shareResponseEntityList.size() == 0) {

                actionMode.finish();
                //after share comes back action mode set to null
                //to initialise again and assign existing selection references
                //actionMode = null;
            } else {
                actionMode.setTitle("" + shareResponseEntityList.size());
                actionMode.invalidate();
            }
        }

    }

    //region Action Mode CallBack Interface
    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.health_menu_actionmode, menu);

            MenuItem compare = menu.findItem(R.id.health_compare);
            compare.setVisible(false);

            // disable Parent layout if action mode is enabled
            //lvParent.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.health_share:

                    if (webViewLoader.getVisibility() != View.VISIBLE) {
                        new AsyncCustomShare().execute();
                    } else {
                        Toast.makeText(getActivity(), "Please wait.., Fetching all quotes", Toast.LENGTH_SHORT).show();
                    }
                    // delete all the selected messages
                    //Toast.makeText(getActivity(), "Share", Toast.LENGTH_SHORT).show();
                    // new AsyncShareJson().execute();
                    // mode.finish();
                    return true;
                case R.id.health_compare:
                    //redirectToHealthCompare();
                    return true;

                default:
                    return false;
            }
        }


        @Override
        public void onDestroyActionMode(ActionMode mode) {

            actionMode = null;

            for (Iterator<ResponseEntity> iter = bikePremiumResponse.getResponse().listIterator(); iter.hasNext(); ) {
                ResponseEntity a = iter.next();
                a.setSelected(false);
            }
            mAdapter.notifyDataSetChanged();

            /*for (int i = 0; i < listDataHeader.size(); i++) {
                //reset all selected quotes
                listDataHeader.get(i).setCompare(false);
            }
            updateMainQuoteAdapter(listDataHeader);*/
            //clear selected compare list to reset
            shareResponseEntityList.clear();
        }
    }
    //endregion

    class AsyncCustomShare extends AsyncTask<Void, Void, BikePremiumResponse> {

        @Override
        protected BikePremiumResponse doInBackground(Void... voids) {
            if (bikePremiumResponse != null) {
                sharePremiumResponse = new BikePremiumResponse();
                sharePremiumResponse.setSummary(bikePremiumResponse.getSummary());
                sharePremiumResponse.setResponse(shareResponseEntityList);
                sharePremiumResponse = applyAddonsForShare(sharePremiumResponse);
            }
            return sharePremiumResponse;
        }

        @Override
        protected void onPostExecute(BikePremiumResponse premiumResponse) {

            if (Utility.checkShareStatus(getActivity()) == 1) {
                if (webViewLoader.getVisibility() != View.VISIBLE) {
                    Intent intent = new Intent(getActivity(), ShareQuoteActivity.class);
                    intent.putExtra(Constants.SHARE_ACTIVITY_NAME, "BIKE_ALL_QUOTE");
                    intent.putExtra("RESPONSE", premiumResponse);
                    intent.putExtra("BIKENAME", carMasterEntity);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Please wait.., Fetching all quotes", Toast.LENGTH_SHORT).show();
                }
            } else {
                openPopUp(ivEdit, "Message", "Your POSP status is INACTIVE", "OK", true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        clearActionMode();
    }

    public void clearActionMode() {
        if (actionMode != null) {
            actionMode.finish();
            shareResponseEntityList.clear();
        }
    }
}
