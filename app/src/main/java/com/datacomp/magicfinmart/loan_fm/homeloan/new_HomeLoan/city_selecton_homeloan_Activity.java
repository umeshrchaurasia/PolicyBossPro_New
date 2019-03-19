package com.datacomp.magicfinmart.loan_fm.homeloan.new_HomeLoan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;

import com.datacomp.magicfinmart.BaseActivity;
import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.loan_fm.personalloan.new_personalloan.bank_selection_personalloanActivity;

import java.util.ArrayList;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;

public class city_selecton_homeloan_Activity extends BaseActivity implements View.OnClickListener {


    AutoCompleteTextView acCity;
    List<String> cityList;
    Button btnNEXT;
    DBPersistanceController databaseController;
    LoginResponseEntity loginEntity;
    ArrayList<String> arrayNewLoan, arrayPreferedCity;

    ArrayAdapter<String> preferedCityAdapter;
    CardView cvMUMBAI,cvDELHI,cvBangalore,cvhyderabad,cvAhamadabad,cvKolkata,cvCHENNAI,cvPUNE,cvJAIPUR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selecton_homeloan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        databaseController = new DBPersistanceController(city_selecton_homeloan_Activity.this);
        cityList = databaseController.getLoanCity();

        acCity = (AutoCompleteTextView)findViewById(R.id.acCity);
        acCity.setOnFocusChangeListener(acCityFocusChange);
        cvMUMBAI=(CardView)findViewById(R.id.cvMUMBAI) ;
        cvDELHI=(CardView)findViewById(R.id.cvDELHI) ;
        cvBangalore=(CardView)findViewById(R.id.cvBangalore) ;
        cvhyderabad=(CardView)findViewById(R.id.cvhyderabad) ;
        cvAhamadabad=(CardView)findViewById(R.id.cvAhamadabad) ;
        cvKolkata=(CardView)findViewById(R.id.cvKolkata) ;
        cvCHENNAI=(CardView)findViewById(R.id.cvCHENNAI) ;
        cvPUNE=(CardView)findViewById(R.id.cvPUNE) ;
        cvJAIPUR=(CardView)findViewById(R.id.cvJAIPUR) ;

        btnNEXT = (Button) findViewById(R.id.btnNEXT);
        btnNEXT.setOnClickListener(this);

        cvMUMBAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","677")
                );
            }
        });
        cvDELHI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","1171")
                );
            }
        });
        cvBangalore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","93")
                );
            }
        });
        cvhyderabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","404")
                );
            }
        });
        cvAhamadabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","9")
                );
            }
        });
        cvKolkata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","550")
                );
            }
        });
        cvCHENNAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","196")
                );
            }
        });
        cvPUNE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","828")
                );
            }
        });

        cvJAIPUR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                        .putExtra("city_id","419")
                );
            }
        });

        loadSpinner();
    }
    View.OnFocusChangeListener acCityFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) {

                String str = acCity.getText().toString();

                ListAdapter listAdapter = acCity.getAdapter();
                for (int i = 0; i < listAdapter.getCount(); i++) {
                    String temp = listAdapter.getItem(i).toString();
                    if (str.compareTo(temp) == 0) {
                        return;
                    }
                }

                acCity.setText("");
                acCity.setError("Invalid city");
                acCity.setFocusable(true);
            }
        }
    };
    private void loadSpinner() {



        //region Preferred City Adapter
        arrayPreferedCity = new ArrayList<String>();
        preferedCityAdapter = new ArrayAdapter<String>(city_selecton_homeloan_Activity.this,
                android.R.layout.simple_list_item_1, cityList);

        acCity.setAdapter(preferedCityAdapter);
        acCity.setThreshold(1);
        //endregion

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNEXT) {
            if (acCity.getText().toString().equals("") || acCity.getText().toString().length() == 0) {
                acCity.setError("Please Enter city.");
                acCity.requestFocus();
                return;
            }
            int city_id = databaseController.getLoanCityID(acCity.getText().toString().toUpperCase());

        //    startActivity(new Intent(city_selecton_personalloan_Activity.this, bank_selection_personalloanActivity.class));

            startActivity(new Intent(city_selecton_homeloan_Activity.this, bank_selection_personalloanActivity.class)
                    .putExtra("city_id",String.valueOf( city_id ))
                  );
        }


    }
}