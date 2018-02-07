package com.datacomp.magicfinmart.health.quoappfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datacomp.magicfinmart.BaseFragment;
import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.health.healthquotetabs.HealthQuoteTabsActivity;
import com.datacomp.magicfinmart.motor.privatecar.ActivityTabsPagerAdapter;
import com.datacomp.magicfinmart.motor.privatecar.addquote.InputQuoteBottmActivity;
import com.datacomp.magicfinmart.utility.RecyclerItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.quoteapplication.QuoteApplicationController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.QuoteListEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.QuoteAppUpdateDeleteResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthQuoteListFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

    FloatingActionButton btnAddQuote;
    RecyclerView rvHealthQuoteList;
    HealthQuoteAdapter healthQuoteAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<QuoteListEntity> mQuoteList;
    QuoteListEntity removeQuoteEntity;

    public HealthQuoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_quote, container, false);
        initView(view);
        mQuoteList = new ArrayList<>();
        if (getArguments().getParcelableArrayList(ActivityTabsPagerAdapter.QUOTE_LIST) != null) {
            mQuoteList = getArguments().getParcelableArrayList(ActivityTabsPagerAdapter.QUOTE_LIST);
        }
        healthQuoteAdapter = new HealthQuoteAdapter(HealthQuoteListFragment.this, mQuoteList);
        rvHealthQuoteList.setAdapter(healthQuoteAdapter);

        //recyclerview item click listener
        rvHealthQuoteList.addOnItemTouchListener(new RecyclerItemClickListener(rvHealthQuoteList, onItemClickListener));
        return view;
    }

    RecyclerItemClickListener.OnItemClickListener onItemClickListener =
            new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    startActivity(new Intent(getActivity(), HealthQuoteTabsActivity.class));
                }
            };

    private void initView(View view) {
        btnAddQuote = (FloatingActionButton) view.findViewById(R.id.fbAddHealthQuote);
        rvHealthQuoteList = (RecyclerView) view.findViewById(R.id.rvHealthQuoteList);
        rvHealthQuoteList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvHealthQuoteList.setLayoutManager(layoutManager);
        btnAddQuote.setOnClickListener(this);

    }

    public void removeQuote(QuoteListEntity entity) {

        removeQuoteEntity = entity;
        showDialog("Please wait,Removing quote..");
        new QuoteApplicationController(getContext()).deleteQuote("" + entity.getVehicleRequestID(),
                this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fbAddHealthQuote:
                startActivity(new Intent(getActivity(), HealthQuoteTabsActivity.class));
                break;
        }
    }


    //region server response

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof QuoteAppUpdateDeleteResponse) {
            if (response.getStatusNo() == 0) {
                mQuoteList.remove(removeQuoteEntity);
                healthQuoteAdapter.refreshAdapter(mQuoteList);
                healthQuoteAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
    }

    //endregion
}
