package com.datacomp.magicfinmart.ultralaksha.ultralakshya.fragment.quote.content;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacomp.magicfinmart.BaseFragment;
import com.datacomp.magicfinmart.R;
import com.datacomp.magicfinmart.home.HomeActivity;
import com.datacomp.magicfinmart.webviews.CommonWebViewActivity;

import magicfinmart.datacomp.com.finmartserviceapi.database.UltraLakshaFacade;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.HDFCEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.PageTwoStandAloneList;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ProductComboList;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UltraLakshaIllustrationResponseNew;

/**
 * A simple {@link Fragment} subclass.
 */
public class UltraLakshayProductCombo extends BaseFragment implements View.OnClickListener {


    TextView  txtLicTerm ,txtLicPPT ,txtLicMode ,txtLicSum ,txtOtherYear,
            txtLicPremYearOne,txtLicPremOtherYears,
            txtHdfcTerm,txtHdfcPPT,txtHdfcMode,txtHdfcSum,txtHdfcPremYearOne,txtHdfcPremOtherYears,
            txtTotalOne,txtTotalTwo ,lblPremiumYear ,lblPremiumOtherYear ,lblPremiumTotalYear,lblPremiumOtherTotalYear;

    ImageView imgBuyNowHDFC;

    UltraLakshaFacade ultraLakshaFacade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ultra_lakshay_product_combo_new, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        ultraLakshaFacade = new UltraLakshaFacade(getActivity());

        bindData();
        setOnclickListener();

    }

    private void initialize(View view)
    {
        imgBuyNowHDFC  = view.findViewById(R.id.imgBuyNowHDFC);
        txtLicTerm = view.findViewById(R.id.txtLicTerm);
        txtLicPPT = view.findViewById(R.id.txtLicPPT);
        txtLicMode = view.findViewById(R.id.txtLicMode);

        txtLicSum = view.findViewById(R.id.txtLicSum);


        txtLicPremYearOne = view.findViewById(R.id.txtLicPremYearOne);
        txtLicPremOtherYears = view.findViewById(R.id.txtLicPremOtherYears);



        txtHdfcTerm = view.findViewById(R.id.txtHdfcTerm);
        txtHdfcPPT = view.findViewById(R.id.txtHdfcPPT);
        txtHdfcMode = view.findViewById(R.id.txtHdfcMode);

        txtHdfcSum = view.findViewById(R.id.txtHdfcSum);
        txtHdfcPremYearOne = view.findViewById(R.id.txtHdfcPremYearOne);
        txtHdfcPremOtherYears = view.findViewById(R.id.txtHdfcPremOtherYears);

        txtTotalOne = view.findViewById(R.id.txtTotalOne);
        txtTotalTwo = view.findViewById(R.id.txtTotalTwo);

        //lblPremiumYear ,lblPremiumOtherYear ,lblPremiumTotalYear,lblPremiumOtherTotalYear;
        lblPremiumYear = view.findViewById(R.id.lblPremiumYear);
        lblPremiumOtherYear = view.findViewById(R.id.lblPremiumOtherYear);

        lblPremiumTotalYear = view.findViewById(R.id.lblPremiumTotalYear);
        lblPremiumOtherTotalYear = view.findViewById(R.id.lblPremiumOtherTotalYear);

    }

    private void setOnclickListener()
    {
        imgBuyNowHDFC.setOnClickListener(this);
    }

    private void bindData()
    {
        if(ultraLakshaFacade.getProductComboList() != null)
        {

            UltraLakshaIllustrationResponseNew.MasterDataBean.ProductComboBean objComboEntity = ultraLakshaFacade.getProductComboList().get(0);

            txtLicTerm.setText(""+objComboEntity.getLicTerm());
            txtLicPPT.setText(""+objComboEntity.getLicPPT());
            txtLicMode.setText(""+objComboEntity.getLicMode());

            txtLicSum.setText(""+getNumbeFormatComma(objComboEntity.getLicSum()));
           // txtOtherYear.setText(""+objComboEntity.getLicYears());

            txtLicPremYearOne.setText(""+getNumbeFormatComma(objComboEntity.getLicPremYearOne()));
            txtLicPremOtherYears.setText(""+getNumbeFormatComma(objComboEntity.getLicPremOtherYears()));
            ///////////////

            txtHdfcTerm.setText(""+objComboEntity.getHdfcTerm());
            txtHdfcPPT.setText(""+objComboEntity.getHdfcPPT());
            txtHdfcMode.setText(""+objComboEntity.getHdfcMode());

            txtHdfcSum.setText(""+getNumbeFormatComma(objComboEntity.getHdfcSum()));
            txtHdfcPremYearOne.setText(""+getNumbeFormatComma(objComboEntity.getHdfcPremYearOne()));
            txtHdfcPremOtherYears.setText(""+getNumbeFormatComma(objComboEntity.getHdfcPremOtherYears()));

            txtTotalOne.setText(""+getNumbeFormatComma(objComboEntity.getTotalOne()));
            txtTotalTwo.setText(""+getNumbeFormatComma(objComboEntity.getTotalTwo()));


            lblPremiumYear.setText("" +(objComboEntity.getLblPremiumYear()));
            lblPremiumOtherYear.setText("" +(objComboEntity.getLblPremiumOtherYear()));

            lblPremiumTotalYear.setText("" +(objComboEntity.getLblPremiumTotalYear()));
            lblPremiumOtherTotalYear.setText("" +(objComboEntity.getLblPremiumOtherTotalYear()));



        }
    }


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.imgBuyNowHDFC)
        {
           if(  ultraLakshaFacade.getUltraLakshaHDFC() !=null) {
               HDFCEntity hdfcEntity = ultraLakshaFacade.getUltraLakshaHDFC();
               startActivity(new Intent(getActivity(), CommonWebViewActivity.class)
                       .putExtra("URL", hdfcEntity.getProposerPageUrl())
                       .putExtra("NAME", "" + "HDFC Protect 3D Plus")
                       .putExtra("TITLE", "" + "HDFC Protect 3D Plus"));
           }


        }

    }
}
