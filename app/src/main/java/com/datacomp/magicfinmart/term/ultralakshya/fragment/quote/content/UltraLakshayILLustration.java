package com.datacomp.magicfinmart.term.ultralakshya.fragment.quote.content;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.term.ultralakshya.fragment.quote.content.adapter.UltraLakshyaBenefitIllustratorAdapter;
import com.datacomp.magicfinmart.term.ultralakshya.fragment.quote.content.adapter.UltraLakshyaDeathNomineeAdapter;

import java.util.ArrayList;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.PrefManager;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DeathBenefitEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LakshyaBenefitIllustratorEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.LoginResponseEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UltraLakshayILLustration extends Fragment {


    RecyclerView rvDeathBenefit;
    List<LakshyaBenefitIllustratorEntity> BenefitsIllustratorLst;
    UltraLakshyaBenefitIllustratorAdapter mAdapter;
    DBPersistanceController dbPersistanceController;
    LoginResponseEntity loginEntity;
    PrefManager prefManager;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ultra_lakshay_illutrator, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);


        mAdapter = new UltraLakshyaBenefitIllustratorAdapter(UltraLakshayILLustration.this, getDeathNomineeLst());
        rvDeathBenefit.setAdapter(mAdapter);
    }
    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());
        BenefitsIllustratorLst = new ArrayList<LakshyaBenefitIllustratorEntity>();

        prefManager.setNotificationCounter(0);

        rvDeathBenefit = (RecyclerView) view.findViewById(R.id.rvDeathBenefit);
        rvDeathBenefit.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvDeathBenefit.setLayoutManager(layoutManager);


    }

    private List<LakshyaBenefitIllustratorEntity> getDeathNomineeLst()
    {
        BenefitsIllustratorLst.clear();
        for(int i =1; i <20 ; i++) {
            LakshyaBenefitIllustratorEntity obj = new LakshyaBenefitIllustratorEntity();
            obj.setYear(""+i);
            obj.setAge( +19 +i);
            obj.setAnnualPremium(""+700 +i);
            obj.setCashFlow(""+178900 +i);
            obj.setLicCover(""+3347900 +i);
            obj.setLoanAvailable(""+6747900 +i);
            BenefitsIllustratorLst.add(obj);
        }

        return BenefitsIllustratorLst;
    }

}