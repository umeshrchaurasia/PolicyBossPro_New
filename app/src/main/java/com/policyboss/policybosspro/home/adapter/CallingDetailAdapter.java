package com.policyboss.policybosspro.home.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.home.HomeActivity;
import com.policyboss.policybosspro.homeMainKotlin.HomeMainActivity;

import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.UserCallingEntity;

/**
 * Created by IN-RB on 05-02-2018.
 */

public class CallingDetailAdapter extends RecyclerView.Adapter<CallingDetailAdapter.CallingDetailItem> {


    Activity mContext;
    List<UserCallingEntity> lstCallingDetail;


    public CallingDetailAdapter(Activity mContext, List<UserCallingEntity> lstCallingDetail ) {
        this.mContext = mContext;
        this.lstCallingDetail = lstCallingDetail;

    }

    public class CallingDetailItem extends RecyclerView.ViewHolder {
        TextView txtName ,txtDesig ,txtMobile,txtEmail;
        LinearLayout lyEmail,lyMobile;


        public CallingDetailItem(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDesig = (TextView) itemView.findViewById(R.id.txtDesig);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);

            lyEmail =   (LinearLayout) itemView.findViewById(R.id.lyEmail);
            lyMobile =   (LinearLayout) itemView.findViewById(R.id.lyMobile);

        }
    }

    @Override
    public CallingDetailItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_calling_userdetail_item, parent, false);

        return new CallingDetailAdapter.CallingDetailItem(itemView);
    }

    @Override
    public void onBindViewHolder(CallingDetailItem holder, int position) {


        final UserCallingEntity userCallingEntity = lstCallingDetail.get(position);

        holder.txtName.setText("" + userCallingEntity.getEmployeeName());
        holder.txtDesig.setText("" + userCallingEntity.getDesignation());

        holder.txtMobile.setText("" + userCallingEntity.getMobileNo());
        holder.txtEmail.setText("" + userCallingEntity.getEmailId());

        holder.lyMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             ((HomeMainActivity) mContext).shareCallingData(userCallingEntity);

            }
        });


        holder.lyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeMainActivity) mContext).shareEmailData(userCallingEntity);

            }
        });


    }


    @Override
    public int getItemCount() {
        return lstCallingDetail.size();
    }
}
