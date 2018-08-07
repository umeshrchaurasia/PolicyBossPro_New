package com.datacomp.magicfinmart.motor.twowheeler.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.motor.privatecar.adapter.GridAddonAdapter;
import com.datacomp.magicfinmart.motor.privatecar.fragment.QuoteFragment;
import com.datacomp.magicfinmart.motor.twowheeler.fragment.BikeQuoteFragment;
import com.datacomp.magicfinmart.utility.ClickListener;
import com.datacomp.magicfinmart.utility.RecyclerTouchListener;

import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.motor.model.ResponseEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.response.BikePremiumResponse;

public class BikeQuoteAdapter extends RecyclerView.Adapter<BikeQuoteAdapter.BikeQuoteItem> {


    Fragment mContext;
    BikePremiumResponse response;

    List<ResponseEntity> listQuotes;
    DBPersistanceController dbPersistanceController;

    public BikeQuoteAdapter(Fragment mContext, BikePremiumResponse response) {
        this.mContext = mContext;
        this.response = response;
        dbPersistanceController = new DBPersistanceController(mContext.getContext());
        if (response.getResponse() != null)
            this.listQuotes = response.getResponse();
        else
            this.listQuotes = null;
    }

    @Override
    public BikeQuoteItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_bikequote_item_new, parent, false);
        return new BikeQuoteAdapter.BikeQuoteItem(itemView);
    }

    @Override
    public void onBindViewHolder(BikeQuoteItem holder, final int position) {

        final ResponseEntity responseEntity = listQuotes.get(position);
        holder.viewDisableLayout.setTag(R.id.viewDisableLayout, responseEntity);
        holder.txtInsurerName.setText(responseEntity.getInsurer().getInsurer_Name());
        // holder.txtIDV.setText(responseEntity);
        try {
            if (responseEntity.getPremium_Breakup() != null) {
                if (responseEntity.isAddonApplied()) {
                    holder.txtFinalPremium.setText("\u20B9 " + Math.round(Double.parseDouble(responseEntity.getFinal_premium_with_addon())));
                } else {
                    holder.txtFinalPremium.setText("\u20B9 " + Math.round(Double.parseDouble(responseEntity.getPremium_Breakup().getFinal_premium())));
                }

            } else {
                holder.txtFinalPremium.setText("");
            }
        } catch (Exception io) {

        }

        holder.txtIDV.setText(String.valueOf(responseEntity.getLM_Custom_Request().getVehicle_expected_idv()));
        //holder.imgInsurerLogo.setImageResource(dbPersistanceController.getInsImage(Integer.parseInt(responseEntity.getInsurer().getInsurer_ID())));

        try {

            int logo = new DBPersistanceController(mContext.getActivity())
                    .getInsurerLogo(Integer.parseInt(responseEntity.getInsurer_Id()));

            Glide.with(mContext).load(logo)
                    .into(holder.imgInsurerLogo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Glide.with(mContext)
                //.load(dbgetProfessionalID1(Integer.parseInt(responseEntity.getInsurer().getInsurer_ID())))
                .load("http://www.policyboss.com/Images/insurer_logo/" + responseEntity.getInsurer().getInsurer_Logo_Name())
                .into(holder.imgInsurerLogo);*/

        holder.txtFinalPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BikeQuoteFragment) mContext).redirectToPopUpPremium(responseEntity, response.getSummary(), responseEntity.getLM_Custom_Request().getVehicle_expected_idv());
            }
        });
        holder.txtPremiumBreakUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BikeQuoteFragment) mContext).redirectToPopUpPremium(responseEntity, response.getSummary(), responseEntity.getLM_Custom_Request().getVehicle_expected_idv());
            }
        });
        holder.llIdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BikeQuoteFragment) mContext).redirectToPopUpPremium(responseEntity, response.getSummary(), responseEntity.getLM_Custom_Request().getVehicle_expected_idv());
            }
        });


      /*  holder.rvAddOn.addOnItemTouchListener(new RecyclerTouchListener(mContext.getActivity(),
                holder.rvAddOn, new ClickListener() {
            @Override
            public void onClick(View view) {
                ResponseEntity responseEntity = (ResponseEntity) view.getTag(R.id.llAddonName);
                ((BikeQuoteFragment) mContext).redirectToPopUpPremium(responseEntity,
                        response.getSummary(),
                        responseEntity.getLM_Custom_Request().getVehicle_expected_idv());
            }

        }));*/

        holder.viewDisableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ResponseEntity responseEntity = (ResponseEntity) view.getTag(R.id.viewDisableLayout);
                ((BikeQuoteFragment) mContext).redirectToPopUpPremium(responseEntity,
                        response.getSummary(),
                        responseEntity.getLM_Custom_Request().getVehicle_expected_idv());
            }
        });
        holder.txtBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BikeQuoteFragment) mContext).redirectToBuy(responseEntity);
            }
        });

        if (responseEntity.getListAppliedAddons() != null) {
            if (responseEntity.getListAppliedAddons().size() != 0) {
                holder.rvAddOn.setVisibility(View.VISIBLE);
                holder.rvAddOn.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext.getActivity(), 2);
                holder.rvAddOn.setLayoutManager(mLayoutManager);
                GridAddonAdapter adapter = new GridAddonAdapter(mContext.getActivity(),
                        responseEntity.getListAppliedAddons(),
                        responseEntity);
                holder.rvAddOn.setAdapter(adapter);
            } else {
                holder.rvAddOn.setVisibility(View.GONE);
            }
        } else {
            holder.rvAddOn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (listQuotes != null) {
            return listQuotes.size();
        } else {
            return 0;
        }
    }

    public void setQuoteResponse(BikePremiumResponse response) {
        this.response = response;
        if (response.getResponse() != null)
            this.listQuotes = response.getResponse();
    }

    public class BikeQuoteItem extends RecyclerView.ViewHolder {
        public TextView txtInsurerName, txtIDV, txtFinalPremium, txtPremiumBreakUp;
        LinearLayout txtBuy;
        ImageView imgInsurerLogo;
        LinearLayout llIdv;
        RecyclerView rvAddOn;
        View viewDisableLayout;
        public BikeQuoteItem(View itemView) {
            super(itemView);
            llIdv = (LinearLayout) itemView.findViewById(R.id.llIdv);
            viewDisableLayout = (View) itemView.findViewById(R.id.viewDisableLayout);
            rvAddOn = (RecyclerView) itemView.findViewById(R.id.rvAddOn);
            txtInsurerName = (TextView) itemView.findViewById(R.id.txtInsuranceCompName);
            txtIDV = (TextView) itemView.findViewById(R.id.txtIDV);
            txtBuy = (LinearLayout) itemView.findViewById(R.id.txtBuy);
            txtFinalPremium = (TextView) itemView.findViewById(R.id.txtFinalPremium);
            imgInsurerLogo = (ImageView) itemView.findViewById(R.id.imgInsurerLogo);
            txtPremiumBreakUp = (TextView) itemView.findViewById(R.id.txtPremiumBreakUp);
        }
    }


}
