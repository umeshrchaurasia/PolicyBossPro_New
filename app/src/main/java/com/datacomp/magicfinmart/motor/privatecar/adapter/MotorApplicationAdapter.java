package com.datacomp.magicfinmart.motor.privatecar.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.datacomp.magicfinmart.R;

import java.util.ArrayList;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ApplicationListEntity;

/**
 * Created by Rajeev Ranjan on 11/01/2018.
 */

public class MotorApplicationAdapter extends RecyclerView.Adapter<MotorApplicationAdapter.ApplicationItem> implements Filterable {
    Fragment fragment;
    List<ApplicationListEntity> mAppList;
    List<ApplicationListEntity> mAppListFiltered;

    public MotorApplicationAdapter(Fragment context, List<ApplicationListEntity> mApplicationList) {
        this.fragment = context;
        mAppList = mApplicationList;
        mAppListFiltered = mApplicationList;
    }

    @Override

    public ApplicationItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_application, parent, false);
        return new ApplicationItem(itemView);
    }

    @Override
    public void onBindViewHolder(ApplicationItem holder, int position) {
        if (holder instanceof ApplicationItem) {

            final ApplicationListEntity entity = mAppListFiltered.get(position);

            holder.txtPersonName.setText(entity.getMotorRequestEntity().getFirst_name()
                    + " " + entity.getMotorRequestEntity().getLast_name());
            holder.txtCRN.setText(String.valueOf(entity.getMotorRequestEntity().getCrn()));
            holder.txtCreatedDate.setText("" + entity.getMotorRequestEntity().getCreated_date());
            holder.txtOverflowMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openPopUp(view, entity);
                }
            });

            try {
                holder.imgInsurerLogo.setImageResource(
                        new DBPersistanceController(fragment.getContext()).getInsurerImage(Integer.parseInt(entity.getMotorRequestEntity().getPrev_insurer_id())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void openPopUp(View v, final ApplicationListEntity entity) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(fragment.getActivity(), v);
        //inflating menu from xml resource
        popup.inflate(R.menu.recycler_menu_application);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuCall:
                        Toast.makeText(fragment.getActivity(), "WIP " + entity.getMotorRequestEntity().getMobile(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuSms:
                        Toast.makeText(fragment.getActivity(), "WIP SMS ", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }


    @Override
    public int getItemCount() {
        if (mAppListFiltered == null) {
            return 0;
        } else {
            return mAppListFiltered.size();
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mAppListFiltered = mAppList;
                } else {
                    List<ApplicationListEntity> filteredList = new ArrayList<>();
                    for (ApplicationListEntity row : mAppList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMotorRequestEntity().getFirst_name().toLowerCase().contains(charString.toLowerCase())
                                || row.getMotorRequestEntity().getLast_name().toLowerCase().contains(charString.toLowerCase())
                                || row.getMotorRequestEntity().getRegistration_no().toLowerCase().contains(charString.toLowerCase())
                                || String.valueOf(row.getMotorRequestEntity().getCrn()).contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mAppListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mAppListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mAppListFiltered = (ArrayList<ApplicationListEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ApplicationItem extends RecyclerView.ViewHolder {

        TextView txtOverflowMenu, txtCreatedDate, txtCRN, txtVehicleNo, txtPersonName;
        ImageView imgInsurerLogo;

        public ApplicationItem(View itemView) {
            super(itemView);
            txtOverflowMenu = (TextView) itemView.findViewById(R.id.txtOverflowMenu);
            txtCreatedDate = (TextView) itemView.findViewById(R.id.txtCreatedDate);
            txtCRN = (TextView) itemView.findViewById(R.id.txtCRN);
            txtVehicleNo = (TextView) itemView.findViewById(R.id.txtVehicleNo);
            txtPersonName = (TextView) itemView.findViewById(R.id.txtPersonName);
            imgInsurerLogo = (ImageView) itemView.findViewById(R.id.imgInsurerLogo);
        }
    }


    public void refreshAdapter(List<ApplicationListEntity> list) {
        mAppList = list;
    }

}