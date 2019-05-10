package com.datacomp.magicfinmart.helpfeedback.raiseticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datacomp.magicfinmart.R;

import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.RaiseTickeViewEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.TicketEntity;

/**
 * Created by Rajeev Ranjan on 09/05/2019.
 */
public class RaiseTicketViewAdapter extends RecyclerView.Adapter<RaiseTicketViewAdapter.RaiseTicketViewItem> {

    Context context;
    List<RaiseTickeViewEntity> viewEntityList;

    public RaiseTicketViewAdapter(Context context, List<RaiseTickeViewEntity> viewEntityList) {
        this.context = context;
        this.viewEntityList = viewEntityList;
    }

    public class RaiseTicketViewItem extends RecyclerView.ViewHolder {

        public ImageView ivUser;
        public TextView txtComment;
        public LinearLayout lyParent;

        public RaiseTicketViewItem(View itemView) {
            super(itemView);
            txtComment = (TextView) itemView.findViewById(R.id.txtComment);
            lyParent =(LinearLayout)itemView.findViewById(R.id.lyParent);
            ivUser = (ImageView) itemView.findViewById(R.id.ivUser);
        }
    }

    @Override
    public RaiseTicketViewAdapter.RaiseTicketViewItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_view_raise_ticket, parent, false);
        return new RaiseTicketViewAdapter.RaiseTicketViewItem(itemView);

    }

    @Override
    public void onBindViewHolder(RaiseTicketViewAdapter.RaiseTicketViewItem holder, int position) {

        if (holder instanceof RaiseTicketViewAdapter.RaiseTicketViewItem) {
            final RaiseTickeViewEntity entity = viewEntityList.get(position);
            holder.txtComment.setText(""+entity.getComment());


        }
    }

    @Override
    public int getItemCount() {
        return viewEntityList.size();
    }
}
