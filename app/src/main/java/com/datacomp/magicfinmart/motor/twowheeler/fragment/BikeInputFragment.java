package com.datacomp.magicfinmart.motor.twowheeler.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.datacomp.magicfinmart.BaseFragment;
import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.motor.twowheeler.activity.BikeAddQuoteActivity;
import com.datacomp.magicfinmart.utility.Constants;
import com.datacomp.magicfinmart.utility.DateTimePicker;
import com.datacomp.magicfinmart.utility.GenericTextWatcher;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.fastlane.FastLaneController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.BikeMasterEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.CityMasterEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.FastLaneDataEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.FastLaneDataResponse;
import magicfinmart.datacomp.com.finmartserviceapi.motor.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.motor.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.motor.controller.MotorController;
import magicfinmart.datacomp.com.finmartserviceapi.motor.requestentity.MotorRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.response.BikeUniqueResponse;

import static com.datacomp.magicfinmart.utility.DateTimePicker.getDiffDays;
import static com.datacomp.magicfinmart.utility.DateTimePicker.getDiffYears;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class BikeInputFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, GenericTextWatcher.iVehicle, IResponseSubcriber, magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber {
    private static final String TAG = "AddNewQuoteActivity";
    TextView tvNew, tvRenew;
    CardView cvNcb;
    LinearLayout llNoClaim, llVerifyCarDetails;
    DiscreteSeekBar sbNoClaimBonus;
    CardView cvNewRenew, cvRegNo;
    View cvInput;
    Button btnGetQuote;
    TextView tvDontKnow;
    EditText etreg1, etreg2, etreg3, etreg4;
    String regNo = "";
    Switch switchNewRenew;

    MotorRequestEntity motorRequestEntity;
    FastLaneDataEntity fastLaneResponseEntity;

    //region inputs
    Spinner spFuel, spVarient, spPrevIns;
    EditText etExtValue, etRegDate, etMfgDate, etExpDate, etCustomerName, etMobile, etCC;
    AutoCompleteTextView acMakeModel, acRto;
    TextView tvCarNo, tvProgress, tvClaimYes, tvClaimNo;
    Switch swIndividual, swClaim;
    Spinner spNcbPercent;
    //endregion

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DBPersistanceController dbController;
    Realm realm;
    List<String> makeModelList, fuelList, variantList, cityList, prevInsurerList;
    ArrayAdapter<String> makeModelAdapter, varientAdapter, fuelAdapter, cityAdapter, prevInsAdapter, ncbPerctAdapter;
    String modelId, varientId;
    String regplace, makeModel = "";
    boolean isClaimExist = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bike_fragment_input, container, false);
        dbController = new DBPersistanceController(getActivity());
        motorRequestEntity = new MotorRequestEntity();

        init_view(view);

        setListener();

        initialize_views();

        bind_init_binders();

        if (getArguments() != null) {
            if (getArguments().getParcelable(BikeAddQuoteActivity.BIKE_INPUT_REQUEST) != null) {
                motorRequestEntity = getArguments().getParcelable(BikeAddQuoteActivity.BIKE_INPUT_REQUEST);
                tvDontKnow.performClick();
                bindInputsQuotes();
            }
        }

        adapter_listeners();

        return view;
    }


    //region binding parameter

    private void bind_init_binders() {

        //fetching initial data

        cityList = dbController.getRTOListNames();
        makeModelList = dbController.getBikeMakeModel();
        prevInsurerList = dbController.getInsurerList();
        //fuelList = dbController.getFuelTypeByModelId("0");
        variantList = dbController.getVariantbyModelID("0");
        //region Autocomplete Make Model
        makeModelAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, makeModelList);
        acMakeModel.setAdapter(makeModelAdapter);
        acMakeModel.setThreshold(2);
        //endregion

        //region Autocomplete RTO
        cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cityList);
        acRto.setAdapter(cityAdapter);
        //endregion

        //region spinner Fuel

        /*//fuelList = new ArrayList<String>();
        fuelAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, fuelList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spFuel.setAdapter(fuelAdapter);*/

        //endregion

        //region spinner Varient

        // variantList = new ArrayList<String>();
        varientAdapter = new
                ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, variantList) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
        spVarient.setAdapter(varientAdapter);

        //endregion

        // region prev insurer adapter
        prevInsAdapter = new
                ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, prevInsurerList) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
        spPrevIns.setAdapter(prevInsAdapter);

        //endregion

        // region ncb adapter
        ncbPerctAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.ncb_percent));
        spNcbPercent.setAdapter(ncbPerctAdapter);
        //endregion
    }

    private void bindInputsQuotes() {

        int vehicleID = motorRequestEntity.getVehicle_id();
        BikeMasterEntity carMasterEntity = dbController.getBikeVarientDetails(String.valueOf(vehicleID));
        makeModel = carMasterEntity.getMake_Name() + " , " + carMasterEntity.getModel_Name();

        //region make model
        acMakeModel.setText(makeModel);
        acMakeModel.performCompletion();

        //endregion

        //region varient list

        variantList.clear();
        /*List<String> varList = dbController.getVariant(carMasterEntity.getMake_Name(),
                carMasterEntity.getModel_Name(),
                carMasterEntity.getFuel_Name());*/
        variantList.addAll(dbController.getBikeVariantbyModelID(carMasterEntity.getModel_ID()));
        varientAdapter.notifyDataSetChanged();


        //endregion

        //region fuel list
       /* fuelList.clear();
        fuelList.addAll(dbController.getFuelTypeByModelId(carMasterEntity.getModel_ID()));
        fuelAdapter.notifyDataSetChanged();*/

        //endregion

        //region spinner selection

        int varientIndex = 0;
        for (int i = 0; i < variantList.size(); i++) {
            if (variantList.get(i).matches(carMasterEntity.getVariant_Name())) {
                varientIndex = i;
                break;
            }
        }
        spVarient.setSelection(varientIndex);

        /*int fuelIndex = 0;
        for (int i = 0; i < fuelList.size(); i++) {
            if (fuelList.get(i).matches(carMasterEntity.getFuel_Name())) {
                fuelIndex = i;
                break;
            }
        }
        spFuel.setSelection(fuelIndex);*/

        int prevInsurerIndex = 0;
        String insName = dbController.getInsurername(Integer.parseInt(motorRequestEntity.getPrev_insurer_id()));
        for (int i = 0; i < prevInsurerList.size(); i++) {
            if (prevInsurerList.get(i).matches(insName)) {
                prevInsurerIndex = i;
                break;
            }
        }
        spPrevIns.setSelection(prevInsurerIndex);


        //endregion

        //region Rto binding

        acRto.setText(dbController.getRTOCityName(String.valueOf(motorRequestEntity.getRto_id())));
        acRto.performCompletion();
        regplace = acRto.getText().toString();

        //endregion

        if (motorRequestEntity.getExternal_bifuel_value() != 0)
            etExtValue.setText(motorRequestEntity.getExternal_bifuel_value());

        etCustomerName.setText(motorRequestEntity.getFirst_name() + " " + motorRequestEntity.getLast_name());

        etMobile.setText(motorRequestEntity.getMobile());

        try {
            etRegDate.setText(simpleDateFormat.format(simpleDateFormat.parse(motorRequestEntity.getVehicle_registration_date())));

            etMfgDate.setText(simpleDateFormat.format(simpleDateFormat.parse(motorRequestEntity.getVehicle_manf_date())));

            etExpDate.setText(simpleDateFormat.format(simpleDateFormat.parse(motorRequestEntity.getPolicy_expiry_date())));
            if (motorRequestEntity.getIs_claim_exists().equals("no")) {
                setSeekbarProgress(getYearDiffForNCB(etRegDate.getText().toString(), etExpDate.getText().toString()));
            } else {
                tvClaimYes.performClick();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void bindFastLaneData(FastLaneDataEntity masterData) {

        String vehicleID = masterData.getVariant_Id();
        BikeMasterEntity carMasterEntity = dbController.getBikeVarientDetails(vehicleID);
        makeModel = carMasterEntity.getMake_Name() + " , " + carMasterEntity.getModel_Name();

        //region make model

        acMakeModel.setText(makeModel);
        acMakeModel.performCompletion();

        //endregion

        //region varient list

        variantList.clear();
       /* List<String> varList = dbController.getVariant(carMasterEntity.getMake_Name(),
                carMasterEntity.getModel_Name(),
                carMasterEntity.getFuel_Name());*/
        variantList.addAll(dbController.getBikeVariantbyModelID(carMasterEntity.getModel_ID()));
        varientAdapter.notifyDataSetChanged();


        //endregion

        //region fuel list
       /* fuelList.clear();
        fuelList.addAll(dbController.getFuelTypeByModelId(carMasterEntity.getModel_ID()));
        fuelAdapter.notifyDataSetChanged();*/

        //endregion

        //region spinner selection

        int varientIndex = 0;
        for (int i = 0; i < variantList.size(); i++) {
            if (variantList.get(i).matches(carMasterEntity.getVariant_Name())) {
                varientIndex = i;
                break;
            }
        }
        spVarient.setSelection(varientIndex);

        /*int fuelIndex = 0;
        for (int i = 0; i < fuelList.size(); i++) {
            if (fuelList.get(i).matches(carMasterEntity.getFuel_Name())) {
                fuelIndex = i;
                break;
            }
        }
        spFuel.setSelection(fuelIndex);*/

        //endregion

        //region Rto binding

        acRto.setText(dbController.getRTOCityName(String.valueOf(masterData.getRTO_Code())));
        acRto.performCompletion();
        regplace = acRto.getText().toString();

        //endregion

        try {

            etRegDate.setText(changeDateFormat(masterData.getRegistration_Date()));

            etMfgDate.setText(changeDateFormat(masterData.getPurchase_Date()));

            etCC.setText("" + masterData.getCubic_Capacity() + "CC");
            //  setSeekbarProgress(getYearDiffForNCB(etRegDate.getText().toString(), etExpDate.getText().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //endregion

    private void adapter_listeners() {

        //region make model

        acMakeModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Constants.hideKeyBoard(acMakeModel, getActivity());
                makeModel = makeModelAdapter.getItem(position).toString();

                modelId = dbController.getBikeModelID(getModel(acMakeModel.getText().toString()));

                if (modelId != "") {
                   /* fuelList.clear();
                    fuelList.addAll(dbController.getFuelTypeByModelId(modelId));
                    fuelAdapter.notifyDataSetChanged();*/

                    variantList.clear();
                    variantList.addAll(dbController.getBikeVariantbyModelID(modelId));
                    varientAdapter.notifyDataSetChanged();
                    spVarient.setSelection(0);
                    etCC.setText("");
                } else {
                    acMakeModel.requestFocus();
                    acMakeModel.setError("Enter Make,Model");
                    return;
                }

            }
        });
        //endregion

        //region fuel Listener
       /* spFuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (fuelList.get(position).equals(Constants.EXTERNAL_LPG)
                        || fuelList.get(position).equals(Constants.EXTERNAL_CNG)) {
                    etExtValue.setEnabled(true);
                } else {
                    etExtValue.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                etExtValue.setEnabled(false);
            }
        });*/
        //endregion

        //region cubic capacity
        spVarient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (fastLaneResponseEntity == null && spVarient.getSelectedItemPosition() != 0) {
                    etCC.setText("" + dbController.getBikeVarientCC(getMake(acMakeModel.getText().toString()), getModel(acMakeModel.getText().toString()), spVarient.getSelectedItem().toString()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //endregion

        // region city adapter

        acRto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                regplace = cityAdapter.getItem(position).toString();
                Constants.hideKeyBoard(acRto, getActivity());
            }
        });

        //endregion

        //region fuel adapter

       /* spFuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                variantList.clear();
                List<String> varList = dbController.getVariant(getMake(acMakeModel.getText().toString()),
                        getModel(acMakeModel.getText().toString()),
                        spFuel.getSelectedItem().toString());
                variantList.addAll(varList);
                varientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
        //endregion
    }

    private void initialize_views() {
        cvInput.setVisibility(View.GONE);
        switchNewRenew.setChecked(true);
        tvClaimYes.performClick();
        llVerifyCarDetails.setVisibility(View.GONE);
    }

    public int getPercentFromProgress(int value) {
        switch (value) {
            case 0:
                return 0;
            case 1:
                return 20;
            case 2:
                return 25;
            case 3:
                return 35;
            case 4:
                return 45;
            case 5:
                return 50;
        }
        return 0;
    }

    private void setListener() {
        switchNewRenew.setOnCheckedChangeListener(this);
        tvClaimYes.setOnClickListener(this);
        tvClaimNo.setOnClickListener(this);
        btnGetQuote.setOnClickListener(this);
        tvDontKnow.setOnClickListener(this);
        etreg1.addTextChangedListener(new GenericTextWatcher(etreg1, this));
        etreg2.addTextChangedListener(new GenericTextWatcher(etreg2, this));
        etreg3.addTextChangedListener(new GenericTextWatcher(etreg3, this));
        etreg4.addTextChangedListener(new GenericTextWatcher(etreg4, this));
//        acMakeModel.addTextChangedListener(new GenericTextWatcher(acMakeModel, this));
//        acRto.addTextChangedListener(new GenericTextWatcher(acRto, this));
        etRegDate.setOnClickListener(datePickerDialog);
        etMfgDate.setOnClickListener(datePickerDialog);
        etExpDate.setOnClickListener(datePickerDialog);

        sbNoClaimBonus.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return getPercentFromProgress(value);
            }
        });
        sbNoClaimBonus.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                if (fromUser) {
                    tvProgress.setText("" + getPercentFromProgress(value));
                }
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
            }
        });
    }

    private void init_view(View view) {
        tvNew = (TextView) view.findViewById(R.id.tvNew);
        tvRenew = (TextView) view.findViewById(R.id.tvRenew);
        llVerifyCarDetails = (LinearLayout) view.findViewById(R.id.llVerifyCarDetails);
        cvNcb = (CardView) view.findViewById(R.id.cvNcb);
        llNoClaim = (LinearLayout) view.findViewById(R.id.llNoClaim);
        cvNewRenew = (CardView) view.findViewById(R.id.cvNewRenew);
        cvRegNo = (CardView) view.findViewById(R.id.cvRegNo);
        cvInput = (View) view.findViewById(R.id.cvInput);
        btnGetQuote = (Button) view.findViewById(R.id.btnGetQuote);
        tvDontKnow = (TextView) view.findViewById(R.id.tvDontKnow);
        tvProgress = (TextView) view.findViewById(R.id.tvProgress);
        tvClaimNo = (TextView) view.findViewById(R.id.tvClaimNo);
        tvClaimYes = (TextView) view.findViewById(R.id.tvClaimYes);
        etCC = (EditText) view.findViewById(R.id.etCC);


        etreg1 = (EditText) view.findViewById(R.id.etreg1);
        etreg1.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(2)});
        etreg2 = (EditText) view.findViewById(R.id.etreg2);
        etreg2.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(2)});
        etreg3 = (EditText) view.findViewById(R.id.etreg3);
        etreg3.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(2)});
        etreg4 = (EditText) view.findViewById(R.id.etreg4);
        etreg4.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(4)});

        switchNewRenew = (Switch) view.findViewById(R.id.switchNewRenew);

        //region init views
        spFuel = (Spinner) view.findViewById(R.id.spFuel);
        spVarient = (Spinner) view.findViewById(R.id.spVarient);
        spPrevIns = (Spinner) view.findViewById(R.id.spPrevIns);
        etExtValue = (EditText) view.findViewById(R.id.etExtValue);
        etRegDate = (EditText) view.findViewById(R.id.etRegDate);
        etMfgDate = (EditText) view.findViewById(R.id.etMfgDate);
        etExpDate = (EditText) view.findViewById(R.id.etExpDate);
        etCustomerName = (EditText) view.findViewById(R.id.etCustomerName);
        etMobile = (EditText) view.findViewById(R.id.etMobile);
        acMakeModel = (AutoCompleteTextView) view.findViewById(R.id.acMakeModel);
        acRto = (AutoCompleteTextView) view.findViewById(R.id.acRto);
        tvCarNo = (TextView) view.findViewById(R.id.tvCarNo);
        swIndividual = (Switch) view.findViewById(R.id.swIndividual);
        swClaim = (Switch) view.findViewById(R.id.switchNcb);
        spNcbPercent = (Spinner) view.findViewById(R.id.spNcbPercent);
        //endregion

        sbNoClaimBonus = (DiscreteSeekBar) view.findViewById(R.id.sbNoClaimBonus);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvClaimNo:
                isClaimExist = false;
                tvClaimNo.setBackgroundResource(R.drawable.customeborder_blue);
                tvClaimYes.setBackgroundResource(R.drawable.customeborder);
                sbNoClaimBonus.setEnabled(true);
                break;
            case R.id.tvClaimYes:
                isClaimExist = true;
                tvClaimNo.setBackgroundResource(R.drawable.customeborder);
                tvClaimYes.setBackgroundResource(R.drawable.customeborder_blue);
                sbNoClaimBonus.setEnabled(false);
                break;
            case R.id.btnGetQuote:

                //region validations
                if (makeModel == null || makeModel.equals("")) {
                    acMakeModel.requestFocus();
                    acMakeModel.setError("Enter Make,Model");
                    return;
                }
                if (!isEmpty(etRegDate)) {
                    etRegDate.requestFocus();
                    etRegDate.setError("Enter Reg Date");
                    return;
                }
                if (!isEmpty(etMfgDate)) {
                    etMfgDate.requestFocus();
                    etMfgDate.setError("Enter Mfg Date");
                    return;
                }
                if (regplace == null || regplace.equals("")) {
                    acRto.requestFocus();
                    acRto.setError("Enter Rto");
                    return;
                }
                if (switchNewRenew.isChecked()) {
                    if (!isEmpty(etExpDate)) {
                        etExpDate.requestFocus();
                        etExpDate.setError("Enter Expiry Date");
                        return;
                    }
                    if (spPrevIns.getSelectedItemPosition() == 0) {
                        spPrevIns.requestFocus();
                        Toast.makeText(getActivity(), "Select Present Insurer", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!isEmpty(etCustomerName)) {
                    etCustomerName.requestFocus();
                    etCustomerName.setError("Enter Name");
                    return;
                }
                /*if (!isValidePhoneNumber(etMobile)) {
                    etMobile.requestFocus();
                    etMobile.setError("Enter Mobile");
                    return;
                }*/


                if (dbController.getBikeVarient(spVarient.getSelectedItem().toString(),
                        getModel(acMakeModel.getText().toString()),
                        getMake(acMakeModel.getText().toString())) == "") {
                    acMakeModel.requestFocus();
                    acMakeModel.setError("Enter Make,Model");
                    return;
                }

                if (dbController.getCityID(getRtoCity(acRto.getText().toString())) == "") {
                    acRto.requestFocus();
                    acRto.setError("Enter Rto");
                    return;
                }


                /*if (spFuel.getSelectedItem().toString().equals(Constants.EXTERNAL_LPG)
                        || spFuel.getSelectedItem().toString().equals(Constants.EXTERNAL_CNG)) {
                    if (etExtValue.getText().toString().equals("")) {
                        etExtValue.requestFocus();
                        etExtValue.setError("Enter Amount");
                        return;
                    } else {
                        int extval = Integer.parseInt(etExtValue.getText().toString());
                        if (extval < 10000 || extval > 50000) {
                            etExtValue.requestFocus();
                            etExtValue.setError("Enter Amount between 10000 & 60000");
                            return;
                        }
                    }
                }*/

                //endregion

                //TODO uncomment this
                if (switchNewRenew.isChecked()) {  //renew
                    setInputParametersReNewCar();
                } else {
                    setInputParametersNewCAR();
                }
                showDialog("Please Wait. Fetching Quotes!!!");
                new MotorController(getActivity()).getMotorPremiumInitiate(motorRequestEntity, this);

                break;
            case R.id.tvDontKnow:
                cvInput.setVisibility(View.VISIBLE);
                cvNewRenew.setVisibility(View.GONE);
                cvRegNo.setVisibility(View.GONE);
                btnGetQuote.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void getVehicleNumber(View view, String vehicleNo) {
        switch (view.getId()) {
            case R.id.etreg1:
                etreg2.requestFocus();
                break;
            case R.id.etreg2:
                etreg3.requestFocus();
                break;
            case R.id.etreg3:
                etreg4.requestFocus();
                break;
            case R.id.etreg4:

                regNo = etreg1.getText().toString() + etreg2.getText().toString()
                        + etreg3.getText().toString() + etreg4.getText().toString();
                llVerifyCarDetails.setVisibility(View.VISIBLE);
                tvCarNo.setText(etreg1.getText().toString() + " " + etreg2.getText().toString()
                        + " " + etreg3.getText().toString() + " " + etreg4.getText().toString());
                Constants.hideKeyBoard(etreg4, getActivity());
                tvDontKnow.performClick();
                btnGetQuote.setVisibility(View.VISIBLE);
                showDialog("Fetching Car Details...");
                new FastLaneController(getActivity()).getVechileDetails(regNo, this);
                break;
        }
    }

    @Override
    public void cancelVehicleNumber(View view) {
        switch (view.getId()) {

            case R.id.etreg1:
                break;
            case R.id.etreg2:
                etreg1.requestFocus();
                break;
            case R.id.etreg3:
                etreg2.requestFocus();
                break;
            case R.id.etreg4:
                etreg3.requestFocus();
                break;
            case R.id.acRto:
                //regplace = null;
                break;
            case R.id.acMakeModel:
                // makeModel = null;
                break;

        }
    }

    //region imp function

    public String getModel(String makeModel) {
        String[] parts = makeModel.split(",");
        if (parts.length == 2)
            return parts[1];
        else return "";
    }

    public String getMake(String makeModel) {
        String[] parts = makeModel.split(",");
        if (parts.length == 2)
            return parts[0];
        else return "";
    }

    public String getVarient(String varientWithCC) {
        String[] parts = varientWithCC.split(",");
        return parts[0];
    }

    private int getMonth(String date) {
        String mon = "" + date.charAt(5) + date.charAt(6);
        return Integer.parseInt(mon);
    }

    private int getYear(String date) {
        String year = "" + date.charAt(0) + date.charAt(1) + date.charAt(2) + date.charAt(3);
        return Integer.parseInt(year);
    }

    private int getDate(String date) {
        String dat = "" + date.charAt(8) + date.charAt(9);
        return Integer.parseInt(dat);
    }

    private int getYearDiffForNCB(String firstDay, String lastDay) {
        try {
            return getDiffYears(simpleDateFormat.parse(firstDay), simpleDateFormat.parse(lastDay));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private long getDaysDiff(String firstDay, String lastDay) {
        try {
            return getDiffDays(simpleDateFormat.parse(firstDay), simpleDateFormat.parse(lastDay));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getRegistrationNo(String city) {
        CityMasterEntity cityMasterEntity = dbController.getVehicleCity_Id(city);
        return formatRegistrationNo(cityMasterEntity.getVehicleCity_RTOCode() + "AA1234");
    }

    private String formatRegistrationNo(String regNo) {
        return "" + regNo.charAt(0) + regNo.charAt(1) + "-" + regNo.charAt(2) + regNo.charAt(3) + "-" + regNo.charAt(4) + regNo.charAt(5) + "-" + regNo.charAt(6) + regNo.charAt(7) + regNo.charAt(8) + regNo.charAt(9);
    }

    private String getManufacturingDate(String manufac) {
        //final Calendar calendar = Calendar.getInstance();
        return "" + manufac.charAt(0) + manufac.charAt(1) + manufac.charAt(2) + manufac.charAt(3) + manufac.charAt(4) + manufac.charAt(5) + manufac.charAt(6) + manufac.charAt(7) + "01";
        //return manufac + "-" + calendar.getTime().getMonth() + "-" + calendar.getTime().getDate();

    }

    private String getRtoCity(String city) {
        String[] parts = city.split("-");
        if (parts.length > 2) {
            String s = parts[1].trim();
            for (int i = 2; i < parts.length; i++) {
                s = s + "-" + parts[i].trim();
            }
            return s;
        } else {
            return parts[1].trim();
        }
    }
    //endregion

    //region date picker

    protected View.OnClickListener datePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Constants.hideKeyBoard(view, getActivity());

            //region regdate
            if (view.getId() == R.id.etRegDate) {
                if (switchNewRenew.isChecked()) {
                    //region  regdate renew
                    DateTimePicker.invoiceReNewValidation(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                            if (view1.isShown()) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                String currentDay = simpleDateFormat.format(calendar.getTime());
                                etRegDate.setText(currentDay);
                                calendar.set(year, monthOfYear, 01);
                                String currentDay1 = simpleDateFormat.format(calendar.getTime());
                                etMfgDate.setText(currentDay1);

                                /*Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(calendar1.get(Calendar.YEAR), monthOfYear, dayOfMonth);
                                String expDate = simpleDateFormat.format(calendar1.getTime());
                                etExpDate.setText(expDate);*/
                            }
                        }
                    });

                    //endregion
                } else {
                    //region  new regdate

                    DateTimePicker.invoiceNewValidation(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                            if (view1.isShown()) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                String currentDay = simpleDateFormat.format(calendar.getTime());
                                etRegDate.setText(currentDay);
                                etMfgDate.setText(currentDay);
                            }
                        }
                    });

                    //endregion
                }
            }
            //endregion

            //region policy expirydate
            else if (view.getId() == R.id.etExpDate) {

                Date regDate = new Date();
                if (etRegDate.getText().toString().isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    regDate = calendar.getTime();
                } else {
                    try {
                        regDate = simpleDateFormat.parse(etRegDate.getText().toString());
                    } catch (ParseException e) {
                        Calendar calendar = Calendar.getInstance();
                        regDate = calendar.getTime();
                        e.printStackTrace();
                    }
                }

                DateTimePicker.BikepolicyExpValidation(view.getContext(), regDate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                        if (view1.isShown()) {
                            Calendar calendar = Calendar.getInstance();
                            String currDate = simpleDateFormat.format(calendar.getTime());
                            calendar.set(year, monthOfYear, dayOfMonth);
                            String expDate = simpleDateFormat.format(calendar.getTime());
                            etExpDate.setText(expDate);
                            if (getDaysDiff(expDate, currDate) < 90) {
                                cvNcb.setVisibility(View.VISIBLE);
                            } else {
                                cvNcb.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
            //endregion

            //region manufacture date
            else if (view.getId() == R.id.etMfgDate) {

                Date regDate = new Date();
                if (etRegDate.getText().toString().isEmpty()) {
                    Calendar calendar = Calendar.getInstance();
                    regDate = calendar.getTime();
                } else {
                    try {
                        regDate = simpleDateFormat.parse(etRegDate.getText().toString());
                    } catch (ParseException e) {
                        Calendar calendar = Calendar.getInstance();
                        regDate = calendar.getTime();
                        e.printStackTrace();
                    }
                }
                DateTimePicker.mfgYearMonthValidation(view.getContext(), regDate,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {
                                if (view1.isShown()) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, 01);
                                    String currentDay = simpleDateFormat.format(calendar.getTime());
                                    etMfgDate.setText(currentDay);
                                }
                            }
                        });
            }
            //endregion

        }
    };
    //endregion

    //region set parameter

    private void setInputParametersNewCAR() {
        // motorRequestEntity.setBirth_date("1992-01-01");
        motorRequestEntity.setProduct_id(10);
        varientId = dbController.getBikeVarient(spVarient.getSelectedItem().toString(), getModel(acMakeModel.getText().toString()), getMake(acMakeModel.getText().toString()));
        motorRequestEntity.setVehicle_id(Integer.parseInt(varientId));
        motorRequestEntity.setRto_id(Integer.parseInt(dbController.getCityID(getRtoCity(regplace))));
        //motorRequestEntity.setSecret_key(Constants.SECRET_KEY);
        //motorRequestEntity.setClient_key(Constants.CLIENT_KEY);
        motorRequestEntity.setExecution_async("yes");
        motorRequestEntity.setVehicle_insurance_type("new");
        motorRequestEntity.setVehicle_manf_date(getManufacturingDate(etMfgDate.getText().toString()));
        motorRequestEntity.setVehicle_registration_date(etRegDate.getText().toString());
        motorRequestEntity.setPolicy_expiry_date("");
        motorRequestEntity.setPrev_insurer_id("");
        motorRequestEntity.setVehicle_registration_type("individual");
        motorRequestEntity.setVehicle_ncb_current("0");
        motorRequestEntity.setIs_claim_exists("yes");
        motorRequestEntity.setMethod_type("Premium");
        motorRequestEntity.setElectrical_accessory("0");
        motorRequestEntity.setNon_electrical_accessory("0");
        if (regNo.equals(""))
            motorRequestEntity.setRegistration_no(getRegistrationNo(getRtoCity(acRto.getText().toString())));
        else
            motorRequestEntity.setRegistration_no(formatRegistrationNo(regNo));
        motorRequestEntity.setIs_llpd("no");
        motorRequestEntity.setIs_antitheft_fit("no");
        motorRequestEntity.setVoluntary_deductible(0);
        motorRequestEntity.setIs_external_bifuel("no");
        motorRequestEntity.setPa_owner_driver_si("100000");
        motorRequestEntity.setPa_named_passenger_si("0");
        motorRequestEntity.setPa_unnamed_passenger_si("0");
        motorRequestEntity.setPa_paid_driver_si("0");
        motorRequestEntity.setVehicle_expected_idv(0);
        motorRequestEntity.setFirst_name("");
        motorRequestEntity.setMiddle_name("");
        motorRequestEntity.setLast_name("");
        motorRequestEntity.setMobile("");
        motorRequestEntity.setEmail("");
        //motorRequestEntity.setCrn(0);
        motorRequestEntity.setIp_address("");

        if (spFuel.getSelectedItem().toString().equals(Constants.EXTERNAL_LPG)) {
            motorRequestEntity.setExternal_bifuel_type("lpg");
            motorRequestEntity.setIs_external_bifuel("yes");
            if (!etExtValue.getText().toString().equals(""))
                motorRequestEntity.setExternal_bifuel_value(Integer.parseInt(etExtValue.getText().toString()));
        } else if (spFuel.getSelectedItem().toString().equals(Constants.EXTERNAL_CNG)) {
            motorRequestEntity.setExternal_bifuel_type("cng");
            motorRequestEntity.setIs_external_bifuel("yes");
            if (!etExtValue.getText().toString().equals(""))
                motorRequestEntity.setExternal_bifuel_value(Integer.parseInt(etExtValue.getText().toString()));

        } else {
            motorRequestEntity.setExternal_bifuel_type("");
            motorRequestEntity.setIs_external_bifuel("no");
            motorRequestEntity.setExternal_bifuel_value(0);
        }

        setCustomerDetails();
    }

    private void setInputParametersReNewCar() {
        if (fastLaneResponseEntity != null) {
            try {
                motorRequestEntity.setVehicle_id(Integer.parseInt(fastLaneResponseEntity.getVariant_Id()));
                motorRequestEntity.setRto_id(Integer.parseInt(fastLaneResponseEntity.getVehicleCity_Id()));
                motorRequestEntity.setVehicle_manf_date(changeDateFormat(fastLaneResponseEntity.getRegistration_Date()));
                motorRequestEntity.setRegistration_no(formatRegistrationNo(fastLaneResponseEntity.getRegistration_Number()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            varientId = dbController.getBikeVarient(spVarient.getSelectedItem().toString(), getModel(acMakeModel.getText().toString()), getMake(acMakeModel.getText().toString()));
            motorRequestEntity.setVehicle_id(Integer.parseInt(varientId));
            motorRequestEntity.setRto_id(Integer.parseInt(dbController.getCityID(getRtoCity(acRto.getText().toString()))));
            motorRequestEntity.setVehicle_manf_date(getManufacturingDate(etMfgDate.getText().toString()));
            if (regNo.equals(""))
                motorRequestEntity.setRegistration_no(getRegistrationNo(getRtoCity(acRto.getText().toString())));
            else
                motorRequestEntity.setRegistration_no(formatRegistrationNo(regNo));
        }

        motorRequestEntity.setVehicle_registration_date(etRegDate.getText().toString());
        motorRequestEntity.setPolicy_expiry_date(etExpDate.getText().toString());
        motorRequestEntity.setPrev_insurer_id("" + dbController.getInsurenceID(spPrevIns.getSelectedItem().toString()));

        // motorRequestEntity.setBirth_date("1992-01-01");
        motorRequestEntity.setProduct_id(10);
        //motorRequestEntity.setSecret_key(Constants.SECRET_KEY);
        //motorRequestEntity.setClient_key(Constants.CLIENT_KEY);
        motorRequestEntity.setExecution_async("yes");
        motorRequestEntity.setVehicle_insurance_type("renew");


        motorRequestEntity.setVehicle_registration_type("individual");
        motorRequestEntity.setMethod_type("Premium");

        if (isClaimExist) {
            motorRequestEntity.setIs_claim_exists("yes");
            motorRequestEntity.setVehicle_ncb_current("0");
        } else {
            motorRequestEntity.setIs_claim_exists("no");
            motorRequestEntity.setVehicle_ncb_current("" + getPercentFromProgress(sbNoClaimBonus.getProgress()));
        }

        motorRequestEntity.setElectrical_accessory("0");
        motorRequestEntity.setNon_electrical_accessory("0");

        motorRequestEntity.setIs_llpd("no");
        motorRequestEntity.setIs_antitheft_fit("no");
        motorRequestEntity.setVoluntary_deductible(0);
        motorRequestEntity.setIs_external_bifuel("no");
        motorRequestEntity.setPa_owner_driver_si("100000");
        motorRequestEntity.setPa_named_passenger_si("0");
        motorRequestEntity.setPa_unnamed_passenger_si("0");
        motorRequestEntity.setPa_paid_driver_si("0");
        motorRequestEntity.setVehicle_expected_idv(0);
        motorRequestEntity.setFirst_name("");
        motorRequestEntity.setMiddle_name("");
        motorRequestEntity.setLast_name("");
        motorRequestEntity.setMobile("");
        motorRequestEntity.setEmail("");
        //motorRequestEntity.setCrn(0);
        motorRequestEntity.setIp_address("");


        /*if (spFuel.getSelectedItem().toString().equals(Constants.EXTERNAL_LPG)) {
            motorRequestEntity.setExternal_bifuel_type("lpg");
            motorRequestEntity.setIs_external_bifuel("yes");
            if (!etExtValue.getText().toString().equals(""))
                motorRequestEntity.setExternal_bifuel_value(Integer.parseInt(etExtValue.getText().toString()));
        } else if (spFuel.getSelectedItem().toString().equals(Constants.EXTERNAL_CNG)) {
            motorRequestEntity.setExternal_bifuel_type("cng");
            motorRequestEntity.setIs_external_bifuel("yes");
            if (!etExtValue.getText().toString().equals(""))
                motorRequestEntity.setExternal_bifuel_value(Integer.parseInt(etExtValue.getText().toString()));

        } else {
            motorRequestEntity.setExternal_bifuel_type("");
            motorRequestEntity.setIs_external_bifuel("no");
            motorRequestEntity.setExternal_bifuel_value(0);
        }*/

        motorRequestEntity.setExternal_bifuel_type("");
        motorRequestEntity.setIs_external_bifuel("no");
        motorRequestEntity.setExternal_bifuel_value(0);
        setCustomerDetails();

    }

    //endregion

    void setCustomerDetails() {
        String[] fullName = etCustomerName.getText().toString().split(" ");

        if (fullName.length == 1) {
            motorRequestEntity.setFirst_name(fullName[0]);
        } else if (fullName.length == 2) {
            motorRequestEntity.setFirst_name(fullName[0]);
            motorRequestEntity.setLast_name(fullName[1]);

        } else if (fullName.length == 3) {
            motorRequestEntity.setFirst_name(fullName[0]);
            motorRequestEntity.setMiddle_name(fullName[1]);
            motorRequestEntity.setLast_name(fullName[2]);
        }
        motorRequestEntity.setMobile(etMobile.getText().toString());
        motorRequestEntity.setEmail("test@test.com");
    }

    //region api response

    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof BikeUniqueResponse) {
            ((BikeAddQuoteActivity) getActivity()).getQuoteParameterBundle(motorRequestEntity);
        }

    }

    @Override
    public void OnSuccess(magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse response, String message) {

        if (response instanceof FastLaneDataResponse) {

            cancelDialog();

            if (response.getStatusNo() == 0) {
                if (!((FastLaneDataResponse) response).getMasterData().getVariant_Id().equals("0")) {
                    this.fastLaneResponseEntity = ((FastLaneDataResponse) response).getMasterData();
                    bindFastLaneData(((FastLaneDataResponse) response).getMasterData());
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    //endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public String changeDateFormat(String date) {

        SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy"); // 30/10/2010
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return simpleDateFormat.format(newDate);
    }


    private void setNcbAdapter(int yearDiff) {
        if (yearDiff >= 5) {
            spNcbPercent.setSelection(5);
        } else {
            spNcbPercent.setSelection(yearDiff);
        }
    }

    private void setSeekbarProgress(int yearDiff) {
        if (yearDiff >= 5) {
            sbNoClaimBonus.setProgress(5);
        } else {
            sbNoClaimBonus.setProgress(yearDiff);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (R.id.switchNewRenew == compoundButton.getId()) {
            if (b) {
                tvRenew.setTextColor(getResources().getColor(R.color.colorAccent));
                tvNew.setTextColor(getResources().getColor(R.color.header_dark_text));
                etExpDate.setEnabled(true);
                spPrevIns.setEnabled(true);
                cvNcb.setVisibility(View.VISIBLE);
            } else {
                tvRenew.setTextColor(getResources().getColor(R.color.header_dark_text));
                tvNew.setTextColor(getResources().getColor(R.color.colorAccent));
                etExpDate.setEnabled(false);
                spPrevIns.setEnabled(false);
                cvNcb.setVisibility(View.GONE);
            }
        }
    }
}