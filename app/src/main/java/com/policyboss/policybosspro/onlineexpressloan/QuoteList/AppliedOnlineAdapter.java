package com.policyboss.policybosspro.onlineexpressloan.QuoteList;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.policyboss.policybosspro.R;

import java.util.ArrayList;
import java.util.List;

import magicfinmart.datacomp.com.finmartserviceapi.express_loan.model.ExpressQuoteEntity;

/**
 * Created by IN-RB on 03-04-2018.
 */

public class AppliedOnlineAdapter extends RecyclerView.Adapter<AppliedOnlineAdapter.ViewHolder> implements Filterable {
    private LayoutInflater mInflater;
    Context mContext;
    List<ExpressQuoteEntity> listCreditCards;
    List<ExpressQuoteEntity> mCreditCardFiltered;

    // data is passed into the constructor
    AppliedOnlineAdapter(Context context, List<ExpressQuoteEntity> list) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        listCreditCards = list;
        mCreditCardFiltered = list;
    }


    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_applied_onlineadapter_item, parent, false);
        return new AppliedOnlineAdapter.ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final AppliedOnlineAdapter.ViewHolder holder, final int position) {

        ExpressQuoteEntity entity = mCreditCardFiltered.get(position);

        AppliedOnlineAdapter.ViewHolder hold = (AppliedOnlineAdapter.ViewHolder) holder;


        if (entity.getApplicationID() != null) {
            holder.txtApplicationNumber.setText(""+String.valueOf(entity.getApplicationID()));
        }else
        {
            holder.txtApplicationNumber.setText("");
        }


        if (entity.getCreatedDate() != null) {
            holder.txtApplicationDate.setText("" + entity.getCreatedDate());
        }else
        {
            holder.txtApplicationDate.setText("");
        }

        if(entity.getLoanType().toUpperCase().equals("PL"))
        {
            hold.txtLoanType.setText("Personal Loan");
        }
        else
        {
            hold.txtLoanType.setText("Short Term PL");
        }


        hold.txtPersonName.setText(entity.getFullName());

        hold.txtloanamount.setText(entity.getLoanAmount());
        try {
            Glide.with(mContext)
                    .load(entity.getDocument1())
                    .into(holder.imgbankLogo);


            //change Fresco

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void refreshAdapter(List<ExpressQuoteEntity> list) {
        mCreditCardFiltered = list;
        notifyDataSetChanged();
    }

    // total number of cells
    @Override
    public int getItemCount() {
        if (mCreditCardFiltered != null)
            return mCreditCardFiltered.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mCreditCardFiltered = listCreditCards;
                } else {
                    List<ExpressQuoteEntity> filerList = new ArrayList<>();

                    for (ExpressQuoteEntity row : listCreditCards) {
                        if (row.getFullName().toLowerCase().contains(charString.toLowerCase())
                                || row.getMobileNo().toLowerCase().contains(charString.toLowerCase())
                                || row.getApplicationID().toLowerCase().contains(charSequence.toString())) {
                            filerList.add(row);
                        }
                    }

                    mCreditCardFiltered = filerList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mCreditCardFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mCreditCardFiltered = (ArrayList<ExpressQuoteEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtOverflowMenu, txtApplicationDate, txtApplicationNumber, txtloanamount, txtPersonName,txtLoanType;
        ImageView imgbankLogo;

        ViewHolder(View v) {
            super(v);

            txtLoanType = (TextView) v.findViewById(R.id.txtLoanType);
            txtOverflowMenu = (TextView) itemView.findViewById(R.id.txtOverflowMenu);
            txtApplicationDate = (TextView) itemView.findViewById(R.id.txtApplicationDate);
            txtApplicationNumber = (TextView) itemView.findViewById(R.id.txtApplicationNumber);
            txtloanamount = (TextView) itemView.findViewById(R.id.txtloanamount);
            txtPersonName = (TextView) itemView.findViewById(R.id.txtPersonName);
            imgbankLogo = (ImageView) itemView.findViewById(R.id.imgbankLogo);
        }
    }

}
