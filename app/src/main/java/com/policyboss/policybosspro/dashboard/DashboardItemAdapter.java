package com.policyboss.policybosspro.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.policyboss.policybosspro.BaseFragment;
import com.policyboss.policybosspro.BuildConfig;
import com.policyboss.policybosspro.MyApplication;
import com.policyboss.policybosspro.R;
import com.policyboss.policybosspro.health.HealthQuoteAppActivity;
import com.policyboss.policybosspro.healthcheckupplans.HealthCheckUpListActivity;
import com.policyboss.policybosspro.home.HomeActivity;
import com.policyboss.policybosspro.motor.privatecar.activity.PrivateCarDetailActivity;
import com.policyboss.policybosspro.motor.twowheeler.activity.TwoWheelerQuoteAppActivity;
import com.policyboss.policybosspro.ncd.NCDActivity;
import com.policyboss.policybosspro.offline_quotes.AddNewOfflineQuotesActivity;
import com.policyboss.policybosspro.quicklead.QuickLeadActivity;
import com.policyboss.policybosspro.term.termselection.TermSelectionActivity;
import com.policyboss.policybosspro.ultralaksha.ultra_selection.UltraLakshaSelectionActivity;
import com.policyboss.policybosspro.utility.Constants;
import com.policyboss.policybosspro.webviews.CommonWebViewActivity;

import java.util.List;
import java.util.Map;

import magicfinmart.datacomp.com.finmartserviceapi.Utility;
import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.tracking.TrackingController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.DashboardarrayEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.TrackingData;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.TrackingRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.model.DashboardMultiLangEntity;

public class DashboardItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    Fragment mContext;
    List<DashboardMultiLangEntity> listInsur;
    DBPersistanceController dbPersistanceController;

    int fbaId = 0;
    String LangType;
    String loanurl = "";

    public DashboardItemAdapter(Fragment context, List<DashboardMultiLangEntity> list, String langType) {
        mContext = context;
        listInsur = list;
        LangType = langType;
        dbPersistanceController = new DBPersistanceController(mContext.getActivity());

        if (dbPersistanceController.getUserData().getFBAId() != 0) {
            fbaId = dbPersistanceController.getUserData().getFBAId();
        }
    }


    public class DashboardItemHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon, imgNew, imgShare, imgInfo;
        TextView txtProductName, txtProductDesc;
        CardView card_view;
        LinearLayout lyParent, lyMain;


        public DashboardItemHolder(View view) {
            super(view);
            card_view = (CardView) view.findViewById(R.id.card_view);
            imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            imgNew = (ImageView) view.findViewById(R.id.imgNew);
            imgInfo = (ImageView) view.findViewById(R.id.imgInfo);
            imgShare = (ImageView) view.findViewById(R.id.imgShare);
            txtProductName = (TextView) view.findViewById(R.id.txtProductName);
            txtProductDesc = (TextView) view.findViewById(R.id.txtProductDesc);
            lyParent = (LinearLayout) view.findViewById(R.id.lyParent);
            lyMain = (LinearLayout) view.findViewById(R.id.lyMain);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.dashboard_rv_item, parent, false);
        return new DashboardItemHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof DashboardItemHolder) {
            if (listInsur.get(position).getIcon() == -1) {
                Glide.with(mContext.getActivity()).load(listInsur.get(position).getServerIcon())
                        .into(((DashboardItemHolder) holder).imgIcon);
            } else {
                ((DashboardItemHolder) holder).imgIcon.setImageResource(listInsur.get(position).getIcon());
            }


            if ((!LangType.isEmpty()) && (!dbPersistanceController.getLangData(LangType,
                    listInsur.get(position).getProductNameKey()).trim().equals(""))) {
                ((DashboardItemHolder) holder).txtProductName.setText(dbPersistanceController.getLangData(LangType,
                        listInsur.get(position).getProductNameKey()));
                ((DashboardItemHolder) holder).txtProductDesc.setText(dbPersistanceController.getLangData(LangType,
                        listInsur.get(position).getProductDetailsKey()));


                ((BaseFragment) mContext).setLanguage(LangType, ((DashboardItemHolder) holder).txtProductName);
                ((BaseFragment) mContext).setLanguage(LangType, ((DashboardItemHolder) holder).txtProductDesc);


            } else {

                ((DashboardItemHolder) holder).txtProductName.setText(listInsur.get(position).getProductName());
                ((DashboardItemHolder) holder).txtProductDesc.setText(listInsur.get(position).getProductDetails());

            }


            // IsExclusive() Verifying Data Comming From server in static case ie Loan it is null

            if (listInsur.get(position).getIsExclusive() != null) {

                // region for Sharing Insurance Prod
                if (listInsur.get(position).getIsSharable().equals("Y")) {

                    ((DashboardItemHolder) holder).imgShare.setVisibility(View.VISIBLE);
                } else {
                    ((DashboardItemHolder) holder).imgShare.setVisibility(View.GONE);
                }

                if (!listInsur.get(position).getInfo().isEmpty()) {

                    ((DashboardItemHolder) holder).imgInfo.setVisibility(View.VISIBLE);
                } else {
                    ((DashboardItemHolder) holder).imgInfo.setVisibility(View.GONE);
                }
                //endregion

                if (!listInsur.get(position).getProductBackgroundColor().isEmpty()) {

                    ((DashboardItemHolder) holder).lyParent.setBackgroundColor(Color.parseColor("#" + listInsur.get(position).getProductBackgroundColor()));
                } else {
                    ((DashboardItemHolder) holder).lyParent.setBackgroundColor(ContextCompat.getColor(mContext.getActivity(), R.color.white));
                }


                if (!listInsur.get(position).getProductNameFontColor().isEmpty()) {
                    ((DashboardItemHolder) holder).txtProductName.setTextColor(Color.parseColor("#" + listInsur.get(position).getProductNameFontColor()));
                } else {
                    ((DashboardItemHolder) holder).txtProductName.setTextColor(ContextCompat.getColor(mContext.getActivity(), R.color.dashboard_title));
                }

                if (!listInsur.get(position).getProductDetailsFontColor().isEmpty()) {
                    ((DashboardItemHolder) holder).txtProductDesc.setTextColor(Color.parseColor("#" + listInsur.get(position).getProductDetailsFontColor()));
                } else {
                    ((DashboardItemHolder) holder).txtProductDesc.setTextColor(ContextCompat.getColor(mContext.getActivity(), R.color.header_text_color));
                }


                // For IsExclusive == Y Showing "New" Icon in Product
                if (listInsur.get(position).getIsExclusive().equals("Y")) {
                    ((DashboardItemHolder) holder).imgNew.setVisibility(View.VISIBLE);
                    Glide.with(mContext.getActivity()).
                            load(R.drawable.newicon)
                            .asGif()
                            .crossFade()
                            .into(((DashboardItemHolder) holder).imgNew);


                    // ((DashboardItemHolder) holder).card_view.setBackgroundResource(R.drawable.customeborder_blue_thin);
                } else {
                    ((DashboardItemHolder) holder).imgNew.setVisibility(View.GONE);
                    //  ((DashboardItemHolder) holder).card_view.setBackgroundResource(R.drawable.customeborder_grey_thin);
                }
            }


            //endregion


            ((DashboardItemHolder) holder).lyParent.setTag(R.id.lyParent, listInsur.get(position));
            ((DashboardItemHolder) holder).imgShare.setTag(R.id.imgShare, listInsur.get(position));
            ((DashboardItemHolder) holder).imgInfo.setTag(R.id.imgInfo, listInsur.get(position));

            ((DashboardItemHolder) holder).lyParent.setOnClickListener(this);
            ((DashboardItemHolder) holder).imgShare.setOnClickListener(this);
            ((DashboardItemHolder) holder).imgInfo.setOnClickListener(this);

            //
            //changed product id 17 to 12 for Commercial vehicle
            //date : 26/11/2019
//            if (listInsur.get(position).getProductId() == 12) {
//                ((DashboardItemHolder) holder).imgNew.setVisibility(View.VISIBLE);
//                Glide.with(mContext.getActivity()).
//                        load(R.drawable.newicon)
//                        .asGif()
//                        .crossFade()
//                        .into(((DashboardItemHolder) holder).imgNew);
//
//
//                ((DashboardItemHolder) holder).card_view.setBackgroundResource(R.drawable.customeborder_blue_thin);
//            } else {
//                ((DashboardItemHolder) holder).imgNew.setVisibility(View.GONE);
//                ((DashboardItemHolder) holder).card_view.setBackgroundResource(R.drawable.customeborder_grey_thin);
//            }


        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.lyParent:
                switchMenus((DashboardMultiLangEntity) view.getTag(view.getId()));   // ie DashboardMultiLangEntity entity
                break;

            case R.id.imgShare:
                ((HomeActivity) mContext.getActivity()).shareProductPopUp((DashboardMultiLangEntity) view.getTag(view.getId()));
                break;

            case R.id.imgInfo:
                ((HomeActivity) mContext.getActivity()).infoProductPopUp((DashboardMultiLangEntity) view.getTag(view.getId()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listInsur.size();
    }


    private void switchMenus(DashboardMultiLangEntity dashboardEntity) {
        int productID = dashboardEntity.getProductId();

        //Toast.makeText(mContext.getActivity(),"Produvt ID" + productID,Toast.LENGTH_LONG).show();
        //fetching parent ss_id in case of switch user
        Map<String, String> map = ((HomeActivity) mContext.getActivity()).loadMap();
        String parent_ssid = "";

        if (map.size() > 0) {
            parent_ssid = map.get("Parent_POSPNo");
        }

        String ipaddress = "0.0.0.0";
        switch (productID) {

            case 1:

                //car
                if (dbPersistanceController.getUserConstantsData().getFourWheelerEnabled().equalsIgnoreCase("1")) {
                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), PrivateCarDetailActivity.class));
                } else {

                    String motorUrl = dbPersistanceController.getUserConstantsData().getFourWheelerUrl();

                    try {
                        ipaddress = Utility.getMacAddress(mContext.getActivity());
                    } catch (Exception io) {
                        ipaddress = "0.0.0.0";
                    }


                    //&ip_address=10.0.3.64&mac_address=10.0.3.64&app_version=2.2.0&product_id=1
                    String append = "&ip_address=" + ipaddress + "&mac_address=" + ipaddress
                            + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                            + "&device_id=" + Utility.getDeviceId(mContext.getActivity())
                            + "&product_id=1&login_ssid=" + parent_ssid;
                    motorUrl = motorUrl + append;

                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                            .putExtra("URL", motorUrl)
                            .putExtra("dashBoardtype", "INSURANCE")
                            .putExtra("NAME", "Motor Insurance")
                            .putExtra("TITLE", "Motor Insurance"));

                }

                //mContext.startActivity(new Intent(mContext, PrivateCarDetailActivity.class));
                //  new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Motor insurance tab on home page"), Constants.PRIVATE_CAR), null);
                MyApplication.getInstance().trackEvent(Constants.PRIVATE_CAR, "Clicked", "Motor insurance tab on home page");
                break;
            case 23:

                // KOTAK
                String kotakUrl = dbPersistanceController.getUserConstantsData().getEliteKotakUrl();


                try {
                    ipaddress = Utility.getMacAddress(mContext.getActivity());
                } catch (Exception io) {
                    ipaddress = "0.0.0.0";
                }


                //&ip_address=10.0.3.64&mac_address=10.0.3.64&app_version=2.2.0&product_id=1
                String appendInKotak = "&ip_address=" + ipaddress + "&mac_address=" + ipaddress
                        + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                        + "&device_id=" + Utility.getDeviceId(mContext.getActivity())
                        + "&product_id=23&login_ssid=" + parent_ssid;
                kotakUrl = kotakUrl + appendInKotak;

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", kotakUrl)
                        .putExtra("dashBoardtype", "INSURANCE")
                        .putExtra("NAME", "Kotak Group health Care")
                        .putExtra("TITLE", "Kotak Group health Care"));

                break;

            case 24:
                //fin peace
                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", "https://10oqcnw.finpeace.ind.in/app#/"
                                + dbPersistanceController.getUserData().getFBAId())
                        .putExtra("NAME", "FIN-PEACE")
                        .putExtra("TITLE", "FIN-PEACE"));
                //  new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Fin Peace tab on home page"), Constants.FIN_PEACE), null);
                MyApplication.getInstance().trackEvent(Constants.FIN_PEACE, "Clicked", "Fin Peace tab on home page");
                break;
            case 2:
                //health

                // mContext.startActivity(new Intent(mContext, HealthQuoteAppActivity.class));


                if (dbPersistanceController.getConstantsData().getHealthappenable().equalsIgnoreCase("1")) {
                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), HealthQuoteAppActivity.class));
                } else {

                    String healthUrl = dbPersistanceController.getUserConstantsData().getHealthurl();
                    //String healthUrl = new DBPersistanceController(mContext).getUserConstantsData().getHealthurltemp();


                    try {
                        ipaddress = Utility.getMacAddress(mContext.getActivity());
                    } catch (Exception io) {
                        ipaddress = "0.0.0.0";
                    }

                    String append = "&ip_address=" + ipaddress
                            + "&app_version=policyboss-" + Utility.getVersionName(mContext.getActivity())
                            + "&device_id=" + Utility.getDeviceId(mContext.getActivity()) + "&login_ssid=" + parent_ssid;
                    ;
                    healthUrl = healthUrl + append;

                    if (dbPersistanceController.getConstantsData().getHealthThrowBrowser() != null &&
                            dbPersistanceController.getConstantsData().getHealthThrowBrowser().equalsIgnoreCase("1")) {
                        Utility.loadWebViewUrlInBrowser(mContext.getActivity(), healthUrl);
                    } else {
                        mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                                .putExtra("URL", healthUrl)
                                .putExtra("dashBoardtype", "INSURANCE")
                                .putExtra("NAME", "Health Insurance")
                                .putExtra("TITLE", "Health Insurance"));
                    }
                }


                //new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Health insurance tab on home page"), Constants.HEALTH_INS), null);
                MyApplication.getInstance().trackEvent(Constants.HEALTH_INS, "Clicked", "Health insurance tab on home page");
                break;
            case 7:
                //home loan
                //  mContext.getActivity().startActivity(new Intent(mContext.getActivity(), NewHomeApplicaionActivity.class));

                loanurl = "https://www.rupeeboss.com/finmart-home-loan-new?BrokerId=" + dbPersistanceController.getUserData().getLoanId() + "&client_source=finmart";

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", loanurl)
                        .putExtra("NAME", "HOME LOAN")
                        .putExtra("TITLE", "HOME LOAN"));

                //new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Home Loan tab on home page"), Constants.HOME_LOAN), null);
                MyApplication.getInstance().trackEvent(Constants.HOME_LOAN, "Clicked", "Home Loan tab on home page");
                break;
            case 19:
                //personal loan
                // mContext.getActivity().startActivity(new Intent(mContext.getActivity(), NewPersonalApplicaionActivity.class));
                loanurl = "https://www.rupeeboss.com/finmart-personal-loan-new?BrokerId=" + dbPersistanceController.getUserData().getLoanId() + "&client_source=finmart";

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", loanurl)
                        .putExtra("NAME", "PERSONAL LOAN")
                        .putExtra("TITLE", "PERSONAL LOAN"));

                //  new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Personal loan tab on home page"), Constants.PERSONA_LOAN), null);
                MyApplication.getInstance().trackEvent(Constants.PERSONA_LOAN, "Clicked", "Personal loan tab on home page");
                break;
            case 8:
                //lap
                //   mContext.getActivity().startActivity(new Intent(mContext.getActivity(), NewLAPApplicaionActivity.class));

                loanurl = "https://www.rupeeboss.com/finmart-property-loan?BrokerId=" + dbPersistanceController.getUserData().getLoanId() + "&client_source=finmart";

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", loanurl)
                        .putExtra("NAME", "LAP")
                        .putExtra("TITLE", "LAP"));
                //   new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("LAP tab on home page"), Constants.LAP), null);
                MyApplication.getInstance().trackEvent(Constants.LAP, "Clicked", "LAP tab on home page");
                break;
            case 4:
                //cc
                // mContext.startActivity(new Intent(mContext, CreditCardMainActivity.class));
                //  mContext.getActivity().startActivity(new Intent(mContext.getActivity(), AppliedCreditListActivity.class));

                loanurl = "https://www.rupeeboss.com/finmart-credit-card-loan-new?BrokerId=" + dbPersistanceController.getUserData().getLoanId() + "&client_source=finmart";

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", loanurl)
                        .putExtra("NAME", "CREDIT CARD")
                        .putExtra("TITLE", "CREDIT CARD"));
                //  new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Credit Card tab on home page"), Constants.CREDIT_CARD), null);
                MyApplication.getInstance().trackEvent(Constants.CREDIT_CARD, "Clicked", "Credit Card tab on home page");
                break;
            case 6:
                //BT
                //  mContext.startActivity(new Intent(mContext, BalanceTransferDetailActivity.class));
                //     mContext.getActivity().startActivity(new Intent(mContext.getActivity(), NewbusinessApplicaionActivity.class));

                loanurl = "https://www.rupeeboss.com/finmart-business-loan-new?BrokerId=" + dbPersistanceController.getUserData().getLoanId() + "&client_source=finmart";

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", loanurl)
                        .putExtra("NAME", "BUSINESS LOAN")
                        .putExtra("TITLE", "BUSINESS LOAN"));
                // new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Business tab on home page"), Constants.BUSINESS_LOAN), null);
                MyApplication.getInstance().trackEvent(Constants.BUSINESS_LOAN, "Clicked", "Business tab on home page");
                break;


            case 81:

                loanurl = "https://www.rupeeboss.com/car-loan-new?BrokerId=" + dbPersistanceController.getUserData().getLoanId() + "&client_source=finmart";

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", loanurl)
                        .putExtra("NAME", "CAR LOAN TOP UP")
                        .putExtra("TITLE", "CAR LOAN TOP UP"));
                //  new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("CAR LOAN TOP UP"), Constants.CAR_TOP_LOAN), null);
                MyApplication.getInstance().trackEvent(Constants.BUSINESS_LOAN, "Clicked", "Business tab on home page");
                break;


            case 9:

                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), QuickLeadActivity.class));
                //  new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Quick Lead tab on home page"), Constants.QUICK_LEAD), null);
                MyApplication.getInstance().trackEvent(Constants.QUICK_LEAD, "Clicked", "Quick Lead tab on home page");
                break;

            case 10:
                //bike

                if (dbPersistanceController.getUserConstantsData().getTwoWheelerEnabled().equalsIgnoreCase("1")) {
                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), TwoWheelerQuoteAppActivity.class));
                } else {

                    String motorUrl = dbPersistanceController.getUserConstantsData().getTwoWheelerUrl();


                    try {
                        ipaddress = Utility.getMacAddress(mContext.getActivity());
                    } catch (Exception io) {
                        ipaddress = "0.0.0.0";
                    }

                    String append = "&ip_address=" + ipaddress + "&mac_address=" + ipaddress
                            + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                            + "&device_id=" + Utility.getDeviceId(mContext.getActivity())
                            + "&product_id=10&login_ssid=" + parent_ssid;
                    motorUrl = motorUrl + append;
                    mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                            .putExtra("URL", motorUrl)
                            .putExtra("dashBoardtype", "INSURANCE")
                            .putExtra("NAME", "Two Wheeler Insurance")
                            .putExtra("TITLE", "Two Wheeler Insurance"));

                }


                //Toast.makeText(mContext.getContext(), "WIP.", Toast.LENGTH_SHORT).show();
                //mContext.startActivity(new Intent(mContext, TwoWheelerQuoteAppActivity.class));
                // new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Two Wheeler tab on home page"), Constants.TWO_WHEELER), null);
                MyApplication.getInstance().trackEvent(Constants.TWO_WHEELER, "Clicked", "Two Wheeler tab on home page");
                break;


            case 11:
                //health check up
                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), HealthCheckUpListActivity.class));
                // new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Health CheckUp"), Constants.HEALTH_CHECKUP), null);
                MyApplication.getInstance().trackEvent(Constants.HEALTH_CHECKUP, "Clicked", "Health CheckUp tab on home page");
                break;

            case 12:
                //Commercial vehicle

                String cvUrl = dbPersistanceController.getUserConstantsData().getCVUrl();


                try {
                    ipaddress = Utility.getMacAddress(mContext.getActivity());
                } catch (Exception io) {
                    ipaddress = "0.0.0.0";
                }

                String append = "&ip_address=" + ipaddress + "&mac_address="
                        + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                        + "&device_id=" + Utility.getDeviceId(mContext.getActivity())
                        + "&product_id=12&login_ssid=" + parent_ssid;
                cvUrl = cvUrl + append;
                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                        .putExtra("URL", cvUrl)
                        .putExtra("dashBoardtype", "INSURANCE")
                        .putExtra("NAME", "Commercial Vehicle Insurance")
                        .putExtra("TITLE", "Commercial Vehicle Insurance"));


                MyApplication.getInstance().trackEvent(Constants.CV, "Clicked", "Health CheckUp tab on home page");
                break;

            case 13:
                Utility.loadWebViewUrlInBrowser(mContext.getActivity(),
                        "http://www.rupeeboss.com/equifax-finmart?fbaid="
                                + String.valueOf(dbPersistanceController.getUserData().getFBAId()));
                break;


            case 14:
                Utility.loadWebViewUrlInBrowser(mContext.getActivity(),
                        "https://yesbankbot.buildquickbots.com/chat/rupeeboss/staff/?userid=" + String.valueOf(dbPersistanceController.getUserData().getFBAId()) + "&usertype=FBA&vkey=b34f02e9-8f1c");
               /* mContext.startActivity(new Intent(mContext, CommonWebViewActivity.class)
                        .putExtra(                break;
"URL", "https://yesbankbot.buildquickbots.com/chat/rupeeboss/staff/?userid=" + String.valueOf(dbPersistanceController.getUserData().getFBAId()) + "&usertype=FBA&vkey=b34f02e9-8f1c")
                        .putExtra("NAME", "" + "Loan On Messenger")
                        .putExtra("TITLE", "" + "Loan On Messenger"));*/

            case 15: //ncd
                //car
                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), NCDActivity.class));
                // new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Campaign "), Constants.CAMPAIGN), null);
                MyApplication.getInstance().trackEvent(Constants.CAMPAIGN, "Clicked", "CAMPAIGN");

                break;
            case 16:
                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), AddNewOfflineQuotesActivity.class));
                // new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Offline quote "), Constants.CAMPAIGN), null);
                MyApplication.getInstance().trackEvent(Constants.OFFLINE, "Clicked", "OFFLINE");
                break;

            case 49: // added by Nilesh 13.02.2019 -- Ultra laksh
                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), UltraLakshaSelectionActivity.class));
                //new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Ultra lakshya"), Constants.CAMPAIGN), null);
                MyApplication.getInstance().trackEvent(Constants.ULTRA_LAKSHA, "Clicked", "ULTRA_LAKSHYA");
                break;

            case 18:
                //Life Insurance
                mContext.getActivity().startActivity(new Intent(mContext.getActivity(), TermSelectionActivity.class));
                // new TrackingController(mContext.getActivity()).sendData(new TrackingRequestEntity(new TrackingData("Life insurance tab on home page"), Constants.LIFE_INS), null);
                MyApplication.getInstance().trackEvent(Constants.LIFE_INS, "Clicked", "Life insurance tab on home page");
                break;

            case 5: //investment

                if (dbPersistanceController.getUserConstantsData().getInvestmentEnabled().equals("1")) {
                    String invUrl = dbPersistanceController.getUserConstantsData().getInvestmentUrl();

                    try {
                        ipaddress = Utility.getMacAddress(mContext.getActivity());
                    } catch (Exception io) {
                        ipaddress = "0.0.0.0";
                    }

                    append = "&ip_address=" + ipaddress
                            + "&app_version=policyboss-" + Utility.getVersionName(mContext.getActivity())
                            + "&device_id=" + Utility.getDeviceId(mContext.getActivity()) + "&login_ssid=" + parent_ssid;

                    invUrl = invUrl + append;

                    if (dbPersistanceController.getConstantsData().getHealthThrowBrowser() != null &&
                            dbPersistanceController.getConstantsData().getHealthThrowBrowser().equalsIgnoreCase("1")) {
                        Utility.loadWebViewUrlInBrowser(mContext.getActivity(), invUrl);
                    } else {
                        mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                                .putExtra("URL", invUrl)
                                .putExtra("NAME", "INVESTMENT PLANS")
                                .putExtra("TITLE", "INVESTMENT PLANS"));
                    }
                } else {
                    Toast.makeText(mContext.getActivity(), "You'r not authorize to sell Investment.", Toast.LENGTH_SHORT).show();
                }
                break;

        }

        if (productID < 100) {
            if (dashboardEntity.getIsNewprdClickable() != null) {
                if (dashboardEntity.getIsNewprdClickable().equals("Y")) {
                    //   region Getting Dynamic Product and Clickable action Using UserConstatnt Data

                    String dynamicUrl = "";
                    for (DashboardarrayEntity entity : dbPersistanceController.getUserConstantsData().getDashboardarray()) {

                        if (Integer.valueOf(entity.getProdId()) == productID) {

                            dynamicUrl = entity.getUrl();
                            break;

                        }

                    }


                    if (!dynamicUrl.isEmpty()) {

                        try {
                            ipaddress = Utility.getMacAddress(mContext.getActivity());
                        } catch (Exception io) {
                            ipaddress = "0.0.0.0";
                        }


                        //&ip_address=10.0.3.64&mac_address=10.0.3.64&app_version=2.2.0&product_id=1
                        String append = "&ip_address=" + ipaddress + "&mac_address=" + ipaddress
                                + "&app_version=policyboss-" + BuildConfig.VERSION_NAME
                                + "&device_id=" + Utility.getDeviceId(mContext.getActivity())
                                + "&product_id=" + productID + "&login_ssid=" + parent_ssid;
                        dynamicUrl = dynamicUrl + append;

                        mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                                .putExtra("URL", dynamicUrl)
                                .putExtra("dashBoardtype", "INSURANCE")
                                .putExtra("NAME", dashboardEntity.getProductName())
                                .putExtra("TITLE", dashboardEntity.getProductName()));

                    }

                    //endregion
                }
            }
        } else if (productID >= 100) {
            mContext.getActivity().startActivity(new Intent(mContext.getActivity(), CommonWebViewActivity.class)
                    .putExtra("URL", "" + dashboardEntity.getLink())
                    .putExtra("NAME", "" + dashboardEntity.getProductName())
                    .putExtra("TITLE", "" + dashboardEntity.getProductName()));
        }
    }
}