package com.policyboss.policybosspro.IncomeCalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.DecimalFormat;
import androidx.appcompat.widget.Toolbar;

import com.policyboss.policybosspro.BaseActivity;
import com.policyboss.policybosspro.R;

public class IncomeCalculatorActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {


    LinearLayout llIncome_Cal;
    LinearLayout llPrivateCar, llTwoWheeler, llHealthIns, llTravelIns,
            llOtherIns, llLifeins, llHomeLoan, llPersonalLoan, llVechileLoan;
    ImageView imgDynamic;
    TextView spnPrivateCar, spnTwoWheeler, spnHealth, spnTravel, spnOther, spnLife, spnHome, spnPersonal, spnVehicel, textView12, textView12fvd, spnIncomeEarned, spnPointsEarned, txtMinProgress, txtMaxProgress;
    EditText edittotalloan;
    String findvalue = "";
    SeekBar sbCalc;
    int seekBarProgress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_income_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("INCOME CALCULATOR");

        init_widgets();
        setListener();




        edittotalloan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    SetIncomePremium(s.toString());
                } else {
                    if (findvalue.toString().matches("PRIVATE CAR")) {
                        spnPrivateCar.setText("0");
                    } else if (findvalue.toString().matches("TWO WHEELER")) {
                        spnTwoWheeler.setText("0");
                    } else if (findvalue.toString().matches("HEALTH INS")) {
                        spnHealth.setText("0");
                    } else if (findvalue.toString().matches("TRAVEL INS")) {
                        spnTravel.setText("0");
                    } else if (findvalue.toString().matches("HOME LOAN")) {
                        spnHome.setText("0");
                    } else if (findvalue.toString().matches("PERSONAL LOAN")) {
                        spnPersonal.setText("0");
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setListener() {
        llPrivateCar.setOnClickListener(this);
        llPrivateCar.performClick();
        llTwoWheeler.setOnClickListener(this);
        llHealthIns.setOnClickListener(this);
        llTravelIns.setOnClickListener(this);
        llOtherIns.setOnClickListener(this);
        llLifeins.setOnClickListener(this);
        llHomeLoan.setOnClickListener(this);
        llPersonalLoan.setOnClickListener(this);
        llVechileLoan.setOnClickListener(this);
        imgDynamic.setOnClickListener(this);
        sbCalc.setOnSeekBarChangeListener(this);
        llIncome_Cal.setOnClickListener(this);
    }

    private void init_widgets() {
        llIncome_Cal = (LinearLayout) findViewById(R.id.llIncome_Cal);
        llPrivateCar = (LinearLayout) findViewById(R.id.llPrivateCar);
        llTwoWheeler = (LinearLayout)  findViewById(R.id.llTwoWheeler);
        llHealthIns = (LinearLayout)  findViewById(R.id.llHealthIns);
        llTravelIns = (LinearLayout)  findViewById(R.id.llTravelIns);
        llOtherIns = (LinearLayout)  findViewById(R.id.llOtherIns);
        llLifeins = (LinearLayout)  findViewById(R.id.llLifeins);
        llHomeLoan = (LinearLayout)  findViewById(R.id.llHomeLoan);
        llPersonalLoan = (LinearLayout)  findViewById(R.id.llPersonalLoan);
        llVechileLoan = (LinearLayout)  findViewById(R.id.llVechileLoan);
        imgDynamic = (ImageView)  findViewById(R.id.imgDynamic);

        spnPrivateCar = (TextView)  findViewById(R.id.spnPrivateCar);
        spnTwoWheeler = (TextView)  findViewById(R.id.spnTwoWheeler);
        spnHealth = (TextView)  findViewById(R.id.spnHealth);

        spnTravel = (TextView)  findViewById(R.id.spnTravel);
        spnOther = (TextView)  findViewById(R.id.spnOther);
        spnLife = (TextView)  findViewById(R.id.spnLife);
        spnHome = (TextView)  findViewById(R.id.spnHome);
        spnPersonal = (TextView)  findViewById(R.id.spnPersonal);
        spnVehicel = (TextView)  findViewById(R.id.spnVehicel);
        edittotalloan = (EditText)  findViewById(R.id.edittotalloan);

        textView12 = (TextView)  findViewById(R.id.textView12);
        textView12fvd = (TextView)  findViewById(R.id.textView12fvd);

        txtMinProgress = (TextView)  findViewById(R.id.txtMinProgress);
        txtMaxProgress = (TextView)  findViewById(R.id.txtMaxProgress);

        spnIncomeEarned = (TextView)  findViewById(R.id.spnIncomeEarned);
        spnPointsEarned = (TextView)  findViewById(R.id.spnPointsEarned);

        sbCalc = (SeekBar)  findViewById(R.id.sbCalc);
        sbCalc.setMax(500000);
        sbCalc.setProgress(0);
        sbCalc.incrementProgressBy(5000);


        spnIncomeEarned.setText("0");
        spnPointsEarned.setText("0");

        spnPrivateCar.setText("0");
        spnTwoWheeler.setText("0");
        spnHealth.setText("0");

        spnTravel.setText("0");
        spnOther.setText("0");
        spnLife.setText("0");
        spnHome.setText("0");
        spnPersonal.setText("0");
        spnVehicel.setText("0");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llIncome_Cal:
                break;
            case R.id.llPrivateCar:
                imgDynamic.setImageResource(R.drawable.car_img);

                setAllBacgroundGrey();
                setBacground(llPrivateCar);
                setProgressMax(llPrivateCar);
                SetIncomePremium("PRIVATE CAR", "PREMIUM IN '000", "tdPrivate");
                edittotalloan.setText(spnPrivateCar.getText().toString());

                break;
            case R.id.llTwoWheeler:
                imgDynamic.setImageResource(R.drawable.two_wheeler);

                setAllBacgroundGrey();
                setBacground(llTwoWheeler);
                setProgressMax(llTwoWheeler);
                SetIncomePremium("TWO WHEELER", "PREMIUM IN '000", "tdTwoWheeler");
                edittotalloan.setText(spnTwoWheeler.getText().toString());
                break;
            case R.id.llHealthIns:
                imgDynamic.setImageResource(R.drawable.health_insurance_new_img);

                setAllBacgroundGrey();
                setBacground(llHealthIns);
                setProgressMax(llHealthIns);
                SetIncomePremium("HEALTH INS", "PREMIUM IN '000", "tdHealth");
                edittotalloan.setText(spnHealth.getText().toString());
                break;
            case R.id.llTravelIns:
                imgDynamic.setImageResource(R.drawable.travel_insurance_img);

                setAllBacgroundGrey();
                setBacground(llTravelIns);
                setProgressMax(llTravelIns);
                SetIncomePremium("TRAVEL INS", "PREMIUM IN '000", "tdTravel");
                edittotalloan.setText(spnTravel.getText().toString());
                break;
            case R.id.llOtherIns:
                imgDynamic.setImageResource(R.drawable.life_insurance);

                setAllBacgroundGrey();
                setBacground(llOtherIns);
                setProgressMax(llOtherIns);
                SetIncomePremium("OTHER INS", "PREMIUM IN '000", "tdOther");
                edittotalloan.setText(spnOther.getText().toString());
                break;
            case R.id.llLifeins:
                imgDynamic.setImageResource(R.drawable.professional_indemnity);

                setAllBacgroundGrey();
                setBacground(llLifeins);
                setProgressMax(llLifeins);
                SetIncomePremium("LIFE INS", "PREMIUM IN '000", "tdLife");
                edittotalloan.setText(spnLife.getText().toString());
                break;
            case R.id.llHomeLoan:
                imgDynamic.setImageResource(R.drawable.loan_against_img);

                setAllBacgroundGrey();
                setBacground(llHomeLoan);
                setProgressMax(llHomeLoan);
                SetIncomePremium("HOME LOAN", "AMOUNT IN LACS", "tdHome");
                edittotalloan.setText(spnHome.getText().toString());
                break;
            case R.id.llPersonalLoan:
                imgDynamic.setImageResource(R.drawable.personal_loan_img);

                setAllBacgroundGrey();
                setBacground(llPersonalLoan);
                setProgressMax(llPersonalLoan);
                SetIncomePremium("PERSONAL LOAN", "AMOUNT IN LACS", "tdPersonal");
                edittotalloan.setText(spnPersonal.getText().toString());
                break;
            case R.id.llVechileLoan:
                imgDynamic.setImageResource(R.drawable.vehicle_loan);

                setAllBacgroundGrey();
                setBacground(llVechileLoan);
                setProgressMax(llVechileLoan);
                SetIncomePremium("VEHICLE LOAN", "AMOUNT IN LACS", "tdVehicle");
                edittotalloan.setText(spnVehicel.getText().toString());
                break;
        }

    }



    public String getRemovedDecimal(double number) {
        DecimalFormat formatter = new DecimalFormat("#");
        return formatter.format(number);
    }

    private void setAllBacgroundGrey() {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            llPrivateCar.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llTwoWheeler.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llHealthIns.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llTravelIns.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llOtherIns.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llLifeins.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llHomeLoan.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llPersonalLoan.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
            llVechileLoan.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_border));
        } else {
            llPrivateCar.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llTwoWheeler.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llHealthIns.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llTravelIns.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llOtherIns.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llLifeins.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llHomeLoan.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llPersonalLoan.setBackground(getResources().getDrawable(R.drawable.grey_border));
            llVechileLoan.setBackground(getResources().getDrawable(R.drawable.grey_border));

        }
    }

    private void setBacground(View view) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_border));
        } else {
            view.setBackground(getResources().getDrawable(R.drawable.red_border));
        }
    }

    private void setProgressMax(View view) {

        //edittotalloan.setText("0");
        sbCalc.setProgress(0);
        if ((view.getId() == R.id.llHomeLoan) || (view.getId() == R.id.llPersonalLoan) || (view.getId() == R.id.llVechileLoan)) {
            txtMaxProgress.setText("50 000 000");
            seekBarProgress = 500000;
            sbCalc.setMax(50000000);
            sbCalc.incrementProgressBy(500000);
        } else {
            txtMaxProgress.setText("500 000");
            seekBarProgress = 5000;
            sbCalc.setMax(500000);
            sbCalc.incrementProgressBy(5000);

        }

    }


    public void SetIncomePremium(String Type, String spnPremIn, String tdID) {
        findvalue = Type;
        //edittotalloan.setText("0");
        textView12.setText(Type);
        textView12fvd.setText(spnPremIn);

    }

    public void SetIncomePremium(String value) {
        double hdnCRPrivateCar = 0.15;  //0.17
        double hdnCRTwoWheeler = 0.15; //0.17
        double hdnCRHealth = 0.20;
        double hdnCRTravel = 0.20;
        double hdnCRHome = 0.0030;// 0.0025;
        double hdnCRPersonal = 0.0075;
        double hdnCRLifes = 0.30;
        double hdnCRBusiness = 0.0075;
        double hdnCRCredit = 5;
        double hdnCRLAP = 0.0040;
        double hdnCRUsedCar = 0.0075;

        double hdnRPPrivateCar = 250;
        double hdnRPTwoWheeler = 500;
        double hdnRPHealth = 100;
        double hdnRPTravel = 100;
        double hdnRPHome = 2000;
        double hdnRPPersonal = 2000;
        double hdnRPLifes = 100;
        double hdnRPBusiness = 2000;
        double hdnRPCredit = 0;
        double hdnRPLAP = 2000;
        double hdnRPUsedCar = 2000;


        double TotalIncomeEarned = 0;
        double TotalPointsEarned = 0;

        if (findvalue.toString().matches("PRIVATE CAR")) {
            spnPrivateCar.setText(value);
        } else if (findvalue.toString().matches("TWO WHEELER")) {
            spnTwoWheeler.setText(value);
        } else if (findvalue.toString().matches("HEALTH INS")) {
            spnHealth.setText(value);
        } else if (findvalue.toString().matches("TRAVEL INS")) {
            spnTravel.setText(value);
        } else if (findvalue.toString().matches("HOME LOAN")) {
            spnHome.setText(value);
        } else if (findvalue.toString().matches("PERSONAL LOAN")) {
            spnPersonal.setText(value);
        }//new
        else if (findvalue.toString().matches("OTHER INS")) {
            spnOther.setText(value);
        } else if (findvalue.toString().matches("LIFE INS")) {
            spnLife.setText(value);
        } else if (findvalue.toString().matches("VEHICLE LOAN")) {
            spnVehicel.setText(value);
        }


        if (!spnPrivateCar.getText().toString().equals("0")) {
            double IEPrivateCar = Math.round(Double.parseDouble(spnPrivateCar.getText().toString()) * hdnCRPrivateCar);
            TotalIncomeEarned = Math.round(TotalIncomeEarned + IEPrivateCar);

            double PEPrivateCar = Math.round(Double.parseDouble(spnPrivateCar.getText().toString()) / hdnRPPrivateCar);
            TotalPointsEarned = Math.round(TotalPointsEarned + PEPrivateCar);
        }
        if (!spnTwoWheeler.getText().toString().equals("0")) {
            double IETwoWheeler = Math.round(Double.parseDouble(spnTwoWheeler.getText().toString()) * hdnCRTwoWheeler);
            TotalIncomeEarned = Math.round(TotalIncomeEarned + IETwoWheeler);

            double PETwoWheeler = Math.round(Double.parseDouble(spnTwoWheeler.getText().toString()) / hdnRPTwoWheeler);
            TotalPointsEarned = Math.round(TotalPointsEarned + PETwoWheeler);
        }
        if (!spnHealth.getText().toString().equals("0")) {
            double IEHealth = Math.round(Double.parseDouble(spnHealth.getText().toString()) * hdnCRHealth);
            TotalIncomeEarned = Math.round(TotalIncomeEarned + IEHealth);
            double PEHealth = Math.round(Double.parseDouble(spnHealth.getText().toString()) / hdnRPHealth);

            TotalPointsEarned = Math.round(TotalPointsEarned + PEHealth);
        }

        if (!spnTravel.getText().toString().equals("0")) {
            double IETravel = Math.round(Double.parseDouble(spnTravel.getText().toString()) * hdnCRTravel);
            TotalIncomeEarned = Math.round(TotalIncomeEarned + IETravel);

            double PETravel = Math.round(Double.parseDouble(spnTravel.getText().toString()) / hdnRPTravel);

            TotalPointsEarned = Math.round(TotalPointsEarned + PETravel);
        }

        if (!spnHome.getText().toString().equals("0")) {
            double IEHome = Math.round(Double.parseDouble(spnHome.getText().toString()) * hdnCRHome);
            TotalIncomeEarned = Math.round(TotalIncomeEarned + IEHome);


            double PEHome = Math.round(Double.parseDouble(spnHome.getText().toString()) / hdnRPHome);

            TotalPointsEarned = Math.round(TotalPointsEarned + PEHome);
        }

        if (!spnPersonal.getText().toString().equals("0")) {
            double IEPersonal = Math.round(Double.parseDouble(spnPersonal.getText().toString()) * hdnCRPersonal);
            TotalIncomeEarned = Math.round(TotalIncomeEarned + IEPersonal);

            double PEPersonal = Math.round(Double.parseDouble(spnPersonal.getText().toString()) / hdnRPPersonal);

            TotalPointsEarned = Math.round(TotalPointsEarned + PEPersonal);
        }
//life insurance

        if (!spnLife.getText().toString().equals("0")) {
            double IELife = Math.round(Double.parseDouble(spnLife.getText().toString()) * hdnCRLifes);
            TotalIncomeEarned = Math.round(TotalIncomeEarned + IELife);

            double PELife = Math.round(Double.parseDouble(spnLife.getText().toString()) / hdnRPLifes);
            TotalPointsEarned = Math.round(TotalPointsEarned + PELife);
        }


        // this.TotalIncomeEarned=this.TotalIncomeEarned+
        spnIncomeEarned.setText(getRemovedDecimal(TotalIncomeEarned));
        spnPointsEarned.setText(getRemovedDecimal(TotalPointsEarned));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sbCalc:
                if (fromUser) {
                    edittotalloan.setText(String.valueOf(((long) progress / seekBarProgress) * seekBarProgress));
                    edittotalloan.setSelection(edittotalloan.getText().length());
                }
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
