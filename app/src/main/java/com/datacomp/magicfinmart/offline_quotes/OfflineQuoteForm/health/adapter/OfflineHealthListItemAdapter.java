package com.datacomp.magicfinmart.offline_quotes.OfflineQuoteForm.health.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.offline_quotes.OfflineQuoteForm.health.OfflineHealthListActivity;
import com.datacomp.magicfinmart.offline_quotes.OfflineQuoteForm.offline_motor.OfflineMotorListActivity;

import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.HealthQuote;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.OfflineMotorListEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.OfflineQuoteListEntity;


public class OfflineHealthListItemAdapter extends RecyclerView.Adapter<OfflineHealthListItemAdapter.OfflineMotorItem> {

    Activity mcontext;
    List<HealthQuote> offlineHealthList;


    public OfflineHealthListItemAdapter(Activity mcontext, List<HealthQuote> offlineHealthList) {
        this.mcontext = mcontext;
        this.offlineHealthList = offlineHealthList;
    }


    public class OfflineMotorItem extends RecyclerView.ViewHolder {

        TextView txtName, txtVehicleNo, txtRegDate;
        LinearLayout llQuoteList, llOfflineMotor;


        public OfflineMotorItem(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtVehicleNo = (TextView) view.findViewById(R.id.txtVehicleNo);
            txtRegDate = (TextView) view.findViewById(R.id.txtRegDate);
            llQuoteList = (LinearLayout) view.findViewById(R.id.llQuoteList);
            llOfflineMotor = (LinearLayout) view.findViewById(R.id.llOfflineMotor);
        }
    }

    @Override
    public OfflineMotorItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_offline_motor_list_item, parent, false);
        return new OfflineMotorItem(view);
    }

    @Override
    public void onBindViewHolder(OfflineMotorItem holder, int position) {

        final HealthQuote entity = offlineHealthList.get(position);

        holder.txtName.setText("Name : " + entity.getHealthRequest().getContactName());
        holder.txtVehicleNo.setText("Sum Insured : " + entity.getHealthRequest().getSumInsured());
        holder.txtRegDate.setText("Reg. Date : " + entity.getHealthRequest().getCreated_date());
        holder.llOfflineMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getHealthRequest() != null )
                    ((OfflineHealthListActivity) mcontext).editOfflineHealth(entity);
            }
        });



        if (entity.getQuote() != null && entity.getQuote().size() > 0) {
            holder.llQuoteList.setVisibility(View.VISIBLE);

            for (int i = 0; i < entity.getQuote().size(); i++) {
                ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView txtQuote = new TextView(mcontext);
                txtQuote.setPadding(0, 4, 0, 4);
                txtQuote.setPaintFlags(txtQuote.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                txtQuote.setLayoutParams(lparams);
                txtQuote.setTextColor(mcontext.getResources().getColor(R.color.colorPrimary));
                txtQuote.setText(entity.getQuote().get(i).getDocument_name());
                txtQuote.setTag(R.id.llQuoteList, entity.getQuote().get(i));
                txtQuote.setOnClickListener(onClickListener);
                holder.llQuoteList.addView(txtQuote);
            }

        } else {
            holder.llQuoteList.setVisibility(View.GONE);
        }

    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                OfflineQuoteListEntity entity = (OfflineQuoteListEntity) v.getTag(R.id.llQuoteList);
                Utility.loadWebViewUrlInBrowser(mcontext, entity.getDocument_path());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int getItemCount() {
        return offlineHealthList.size();
    }
}
