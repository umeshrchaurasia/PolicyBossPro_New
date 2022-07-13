package magicfinmart.datacomp.com.finmartserviceapi.finmart.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserConstantEntity extends RealmObject {
    /**
     * pospselfid : 3416
     * pospparentid : 2345
     * pospsendid : 3416
     * pospselfname : TESTPS POSP
     * pospparentname : Teet Tetet
     * pospsendname : TESTPS POSP
     * pospselfemail : testfl@gmail.com
     * pospparentemail : qq@gmail.com
     * pospsendemail : testfl@gmail.com
     * pospselfmobile : 9649679767
     * pospparentmobile : 8864545484
     * pospsendmobile : 9649679767
     * pospselfdesignation : LANDMARK POSP
     * pospparentdesignation : test
     * pospsenddesignation : LANDMARK POSP
     * pospselfphoto : http://qa.mgfm.in/uploads/37292/POSPPhotograph.jpg
     * pospparentphoto :
     * pospsendphoto : http://qa.mgfm.in/uploads/37292/POSPPhotograph.jpg
     * loanselfid : 38054
     * loanparentid : 333
     * loansendid : 38054
     * loanselfname : TEST TEDT
     * loanparentname : nitin test
     * loansendname : TEST TEDT
     * loanselfemail : testfl@gmail.com
     * loanparentemail : tedtgr@fff.vfty
     * loansendemail : testfl@gmail.com
     * loanselfmobile : 9687554545
     * loanparentmobile : 9625657576
     * loansendmobile : 9687554545
     * loanselfdesignation : Finmart Business Associate
     * loanparentdesignation : FINMART BUSINESS ASSOCIATE
     * loansenddesignation : Finmart Business Associate
     * loanselfphoto : http://qa.mgfm.in/uploads/37292/FBAPhotograph.jpg
     * loanparentphoto :
     * loansendphoto : http://qa.mgfm.in/uploads/37292/FBAPhotograph.jpg
     * FullName : TESTpl Test DATACOMP
     * FBAId : 37292
     * POSPNo : 3416
     * POSP_STATUS : Certified Agent for GI
     * MangMobile : 9137850198
     * MangEmail : rrm.12@magicfinmart.com
     * SuppMobile : 022-66048200
     * SuppEmail : fba.support@magicfinmart.com
     */
    @PrimaryKey
    private String FBAId;
    private String pospselfid;
    private String pospparentid;
    private String pospsendid;
    private String pospselfname;
    private String pospparentname;
    private String pospsendname;
    private String pospselfemail;
    private String pospparentemail;
    private String pospsendemail;
    private String pospselfmobile;
    private String pospparentmobile;
    private String pospsendmobile;
    private String pospselfdesignation;
    private String pospparentdesignation;
    private String pospsenddesignation;
    private String pospselfphoto;
    private String pospparentphoto;
    private String pospsendphoto;
    private String loanselfid;
    private String loanparentid;
    private String loansendid;
    private String loanselfname;
    private String loanparentname;
    private String loansendname;
    private String loanselfemail;
    private String loanparentemail;
    private String loansendemail;
    private String loanselfmobile;
    private String loanparentmobile;
    private String loansendmobile;
    private String loanselfdesignation;
    private String loanparentdesignation;
    private String loansenddesignation;
    private String loanselfphoto;
    private String loanparentphoto;
    private String loansendphoto;
    private String FullName;

    private String POSPNo;
    private String POSP_STATUS;
    private String MangMobile;
    private String MangEmail;
    private String SuppMobile;
    private String SuppEmail;
    private String LoginID;
    private String ManagName;

    private String finmartwhatsappno;
    private int isactive;
    private String enablemyaccountupdate;

    /**
     * plbanner :https://horizon.policyboss.com:5443/images/plbanner.gif
     * plactive : 1
     * addposplimit : 10
     * serviceurl : http://bo.mgfm.in/get_service/1976
     */

    private String plbanner;
    private String plactive;
    private String addposplimit;
    private String serviceurl;
    private String healthurl;
    private String AddPospVisible;
    private String PBByCrnSearch;

    private String ERPID;

    private int androidproversion;
    /**
     * userid :
     * marketinghomepopupid : 1
     * marketinghometitle : Test Title 1
     * marketinghomedesciption : Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ... The first word, “Lorem,” isn't even a word; instead it's a piece of the word “dolorem,” meaning pain, suffering, or sorrow
     * marketinghomemaxcount : 3
     * marketinghomeenabled : 1
     * marketinghometransfertype : 1
     */

    private String userid;
    private String marketinghomepopupid;
    private String marketinghometitle;
    private String marketinghomedesciption;
    private String marketinghomemaxcount;
    private String marketinghomeenabled;
    private String marketinghometransfertype;

    private String healthurltemp;

    private String messagesender;

    private String enablencd;
    private String ultralakshyaenabled;


    private String finperkurl;
    private String finboxurl;

    //added by nilesh 19.06.2019
    private String boempuid;
    private String enableInsuranceBusiness;


    //25.07.2019 by nilesh
    private String fba_uid;
    private String fba_campaign_name;
    private String fba_campaign_id;
    private String showmyinsurancebusiness;


    //22.10.2019
    private String cobrowserisactive;
    private String cobrowserlicensecode;

    //30.12.2019
    private String RaiseTickitEnabled;
    private String RaiseTickitUrl;

    //03.01.2020
    private String InvestmentUrl;
    private String InvestmentEnabled;
    private RealmList<DashboardarrayEntity> dashboardarray;


    private String LeadDashUrl;


    private String EliteKotakUrl;


    private String KotakEliteEnabled;

    private String enablesynccontact;

    //02/11/2020
    private String GenerateMotorLeadsEnabled;
    private String policyByCRNEnabled;
    private String FinboxEnabled;
    private String FinperksEnabled;

    private String PospletterEnabled;
    private String PospappformEnabled;
    private String MyTransactionsEnabled;
    private String MyMessagesEnabled;
    private String SmsTemplatesEnabled;

    public String getFinboxEnabled() {
        return FinboxEnabled;
    }

    public void setFinboxEnabled(String finboxEnabled) {
        FinboxEnabled = finboxEnabled;
    }

    public String getFinperksEnabled() {
        return FinperksEnabled;
    }

    public void setFinperksEnabled(String finperksEnabled) {
        FinperksEnabled = finperksEnabled;
    }

    public String getGenerateMotorLeadsEnabled() {
        return GenerateMotorLeadsEnabled;
    }

    public void setGenerateMotorLeadsEnabled(String generateMotorLeadsEnabled) {
        GenerateMotorLeadsEnabled = generateMotorLeadsEnabled;
    }

    public String getMyMessagesEnabled() {
        return MyMessagesEnabled;
    }

    public void setMyMessagesEnabled(String myMessagesEnabled) {
        MyMessagesEnabled = myMessagesEnabled;
    }

    public String getMyTransactionsEnabled() {
        return MyTransactionsEnabled;
    }

    public void setMyTransactionsEnabled(String myTransactionsEnabled) {
        MyTransactionsEnabled = myTransactionsEnabled;
    }

    public String getPolicyByCRNEnabled() {
        return policyByCRNEnabled;
    }

    public void setPolicyByCRNEnabled(String policyByCRNEnabled) {
        this.policyByCRNEnabled = policyByCRNEnabled;
    }

    public String getPospappformEnabled() {
        return PospappformEnabled;
    }

    public void setPospappformEnabled(String pospappformEnabled) {
        PospappformEnabled = pospappformEnabled;
    }

    public String getPospletterEnabled() {
        return PospletterEnabled;
    }

    public void setPospletterEnabled(String pospletterEnabled) {
        PospletterEnabled = pospletterEnabled;
    }

    public String getSmsTemplatesEnabled() {
        return SmsTemplatesEnabled;
    }

    public void setSmsTemplatesEnabled(String smsTemplatesEnabled) {
        SmsTemplatesEnabled = smsTemplatesEnabled;
    }

    //////////////////////////

    public String getTermPopup() {
        return TermPopup;
    }

    public void setTermPopup(String termPopup) {
        TermPopup = termPopup;
    }

    public String getTermPopupurl() {
        return TermPopupurl;
    }

    public void setTermPopupurl(String termPopupurl) {
        TermPopupurl = termPopupurl;
    }

    //for term
    private String TermPopup;
    private String TermPopupurl;

    public String getInvestmentEnabled() {
        return InvestmentEnabled;
    }

    public void setInvestmentEnabled(String investmentEnabled) {
        InvestmentEnabled = investmentEnabled;
    }

    public String getInvestmentUrl() {
        return InvestmentUrl;
    }

    public void setInvestmentUrl(String investmentUrl) {
        InvestmentUrl = investmentUrl;
    }

    public String getRaiseTickitEnabled() {
        return RaiseTickitEnabled;
    }

    public void setRaiseTickitEnabled(String raiseTickitEnabled) {
        RaiseTickitEnabled = raiseTickitEnabled;
    }

    public String getRaiseTickitUrl() {
        return RaiseTickitUrl;
    }

    public void setRaiseTickitUrl(String raiseTickitUrl) {
        RaiseTickitUrl = raiseTickitUrl;
    }

    public String getCobrowserisactive() {
        return cobrowserisactive;
    }

    public void setCobrowserisactive(String cobrowserisactive) {
        this.cobrowserisactive = cobrowserisactive;
    }

    public String getCobrowserlicensecode() {
        return cobrowserlicensecode;
    }

    public void setCobrowserlicensecode(String cobrowserlicensecode) {
        this.cobrowserlicensecode = cobrowserlicensecode;
    }

    public String getShowmyinsurancebusiness() {
        return showmyinsurancebusiness;
    }

    public void setShowmyinsurancebusiness(String showmyinsurancebusiness) {
        this.showmyinsurancebusiness = showmyinsurancebusiness;
    }

    public String getFba_campaign_id() {
        return fba_campaign_id;
    }

    public void setFba_campaign_id(String fba_campaign_id) {
        this.fba_campaign_id = fba_campaign_id;
    }

    public String getFba_uid() {
        return fba_uid;
    }

    public void setFba_uid(String fba_uid) {
        this.fba_uid = fba_uid;
    }

    public String getFba_campaign_name() {
        return fba_campaign_name;
    }

    public void setFba_campaign_name(String fba_campaign) {
        this.fba_campaign_name = fba_campaign;
    }

    public String getUltralakshyaenabled() {
        return ultralakshyaenabled;
    }

    public void setUltralakshyaenabled(String ultralakshyaenabled) {
        this.ultralakshyaenabled = ultralakshyaenabled;
    }


    /**
     * marketinghomeimageurl :
     * marketinghomebirthdayimageurl :
     * Status : 0
     * textmessage : Important UPDATE
     * <p>
     * Hello Magicfinmart Partner,
     * New version 2.1.1 of Magicfinmart is live on google play store with some new and exciting features and ease of use.
     * http://bit.ly/2NT1gTH
     * Pease Update your APPLICATION & Rate 5 Star.
     * <p>
     * In-case any of any query you may contact to your RM or you can also call to 9137850207 (Tech-Support)
     */


    private String marketinghomeimageurl;

    private String notificationpopupurltype;
    private String notificationpopupurl;


    private String crnmobileno;
    private String insurancerepositorylink;


    private String parentid; // added by Nilesh 08/02/2019
    private String enableenrolasposp;


    // added by Nilesh 17.04.2019
    private String paenable;


    //added by nilesh 26.07.19
    private String TwoWheelerEnabled;
    private String TwoWheelerUrl;
    private String FourWheelerEnabled;
    private String FourWheelerUrl;

    //added by nilesh 25.11.2019
    private String CVUrl;

    public String getCVUrl() {
        return CVUrl;
    }

    public void setCVUrl(String CVUrl) {
        this.CVUrl = CVUrl;
    }


    public String getTwoWheelerEnabled() {
        return TwoWheelerEnabled;
    }

    public void setTwoWheelerEnabled(String twoWheelerEnabled) {
        TwoWheelerEnabled = twoWheelerEnabled;
    }

    public String getTwoWheelerUrl() {
        return TwoWheelerUrl;
    }

    public void setTwoWheelerUrl(String twoWheelerUrl) {
        TwoWheelerUrl = twoWheelerUrl;
    }

    public String getFourWheelerEnabled() {
        return FourWheelerEnabled;
    }

    public void setFourWheelerEnabled(String fourWheelerEnabled) {
        FourWheelerEnabled = fourWheelerEnabled;
    }

    public String getFourWheelerUrl() {
        return FourWheelerUrl;
    }

    public void setFourWheelerUrl(String fourWheelerUrl) {
        FourWheelerUrl = fourWheelerUrl;
    }


    public String getEnableInsuranceBusiness() {
        return enableInsuranceBusiness;
    }

    public void setEnableInsuranceBusiness(String enableInsuranceBusiness) {
        this.enableInsuranceBusiness = enableInsuranceBusiness;
    }

    public String getBoempuid() {
        return boempuid;
    }

    public void setBoempuid(String boempuid) {
        this.boempuid = boempuid;
    }


    public String getPaenable() {
        return paenable;
    }

    public void setPaenable(String paenable) {
        this.paenable = paenable;
    }

    public String getEnableenrolasposp() {
        return enableenrolasposp;
    }

    public void setEnableenrolasposp(String enableenrolasposp) {
        this.enableenrolasposp = enableenrolasposp;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getEnablencd() {
        return enablencd;
    }

    public void setEnablencd(String enablencd) {
        this.enablencd = enablencd;
    }

    public String getInsurancerepositorylink() {
        return insurancerepositorylink;
    }

    public void setInsurancerepositorylink(String insurancerepositorylink) {
        this.insurancerepositorylink = insurancerepositorylink;
    }

    public String getNotificationpopupurltype() {
        return notificationpopupurltype;
    }

    public void setNotificationpopupurltype(String notificationpopupurltype) {
        this.notificationpopupurltype = notificationpopupurltype;
    }

    public String getNotificationpopupurl() {
        return notificationpopupurl;
    }

    public void setNotificationpopupurl(String notificationpopupurl) {
        this.notificationpopupurl = notificationpopupurl;
    }

    public String getHealthurltemp() {
        return healthurltemp;
    }

    public void setHealthurltemp(String healthurltemp) {
        this.healthurltemp = healthurltemp;
    }

    /**
     * marketinghomeurl : http://magicfinmart.com
     */

    private String marketinghomeurl;

    public String getAddPospVisible() {
        return AddPospVisible;
    }

    public void setAddPospVisible(String addPospVisible) {
        AddPospVisible = addPospVisible;
    }

    public String getHealthurl() {
        return healthurl;
    }

    public void setHealthurl(String healthurl) {
        this.healthurl = healthurl;
    }

    public String getLoginID() {
        return LoginID;
    }

    public void setLoginID(String loginID) {
        LoginID = loginID;
    }

    public String getManagName() {
        return ManagName;
    }

    public void setManagName(String managName) {
        ManagName = managName;
    }


    public String getPospselfid() {
        return pospselfid;
    }

    public void setPospselfid(String pospselfid) {
        this.pospselfid = pospselfid;
    }

    public String getPospparentid() {
        return pospparentid;
    }

    public void setPospparentid(String pospparentid) {
        this.pospparentid = pospparentid;
    }

    public String getPospsendid() {
        return pospsendid;
    }

    public void setPospsendid(String pospsendid) {
        this.pospsendid = pospsendid;
    }

    public String getPospselfname() {
        return pospselfname;
    }

    public void setPospselfname(String pospselfname) {
        this.pospselfname = pospselfname;
    }

    public String getPospparentname() {
        return pospparentname;
    }

    public void setPospparentname(String pospparentname) {
        this.pospparentname = pospparentname;
    }

    public String getPospsendname() {
        return pospsendname;
    }

    public void setPospsendname(String pospsendname) {
        this.pospsendname = pospsendname;
    }

    public String getPospselfemail() {
        return pospselfemail;
    }

    public void setPospselfemail(String pospselfemail) {
        this.pospselfemail = pospselfemail;
    }

    public String getPospparentemail() {
        return pospparentemail;
    }

    public void setPospparentemail(String pospparentemail) {
        this.pospparentemail = pospparentemail;
    }

    public String getPospsendemail() {
        return pospsendemail;
    }

    public void setPospsendemail(String pospsendemail) {
        this.pospsendemail = pospsendemail;
    }

    public String getPospselfmobile() {
        return pospselfmobile;
    }

    public void setPospselfmobile(String pospselfmobile) {
        this.pospselfmobile = pospselfmobile;
    }

    public String getPospparentmobile() {
        return pospparentmobile;
    }

    public void setPospparentmobile(String pospparentmobile) {
        this.pospparentmobile = pospparentmobile;
    }

    public String getPospsendmobile() {
        return pospsendmobile;
    }

    public void setPospsendmobile(String pospsendmobile) {
        this.pospsendmobile = pospsendmobile;
    }

    public String getPospselfdesignation() {
        return pospselfdesignation;
    }

    public void setPospselfdesignation(String pospselfdesignation) {
        this.pospselfdesignation = pospselfdesignation;
    }

    public String getPospparentdesignation() {
        return pospparentdesignation;
    }

    public void setPospparentdesignation(String pospparentdesignation) {
        this.pospparentdesignation = pospparentdesignation;
    }

    public String getPospsenddesignation() {
        return pospsenddesignation;
    }

    public void setPospsenddesignation(String pospsenddesignation) {
        this.pospsenddesignation = pospsenddesignation;
    }

    public String getPospselfphoto() {
        return pospselfphoto;
    }

    public void setPospselfphoto(String pospselfphoto) {
        this.pospselfphoto = pospselfphoto;
    }

    public String getPospparentphoto() {
        return pospparentphoto;
    }

    public void setPospparentphoto(String pospparentphoto) {
        this.pospparentphoto = pospparentphoto;
    }

    public String getPospsendphoto() {
        return pospsendphoto;
    }

    public void setPospsendphoto(String pospsendphoto) {
        this.pospsendphoto = pospsendphoto;
    }

    public String getLoanselfid() {
        return loanselfid;
    }

    public void setLoanselfid(String loanselfid) {
        this.loanselfid = loanselfid;
    }

    public String getLoanparentid() {
        return loanparentid;
    }

    public void setLoanparentid(String loanparentid) {
        this.loanparentid = loanparentid;
    }

    public String getLoansendid() {
        return loansendid;
    }

    public void setLoansendid(String loansendid) {
        this.loansendid = loansendid;
    }

    public String getLoanselfname() {
        return loanselfname;
    }

    public void setLoanselfname(String loanselfname) {
        this.loanselfname = loanselfname;
    }

    public String getLoanparentname() {
        return loanparentname;
    }

    public void setLoanparentname(String loanparentname) {
        this.loanparentname = loanparentname;
    }

    public String getLoansendname() {
        return loansendname;
    }

    public void setLoansendname(String loansendname) {
        this.loansendname = loansendname;
    }

    public String getLoanselfemail() {
        return loanselfemail;
    }

    public void setLoanselfemail(String loanselfemail) {
        this.loanselfemail = loanselfemail;
    }

    public String getLoanparentemail() {
        return loanparentemail;
    }

    public void setLoanparentemail(String loanparentemail) {
        this.loanparentemail = loanparentemail;
    }

    public String getLoansendemail() {
        return loansendemail;
    }

    public void setLoansendemail(String loansendemail) {
        this.loansendemail = loansendemail;
    }

    public String getLoanselfmobile() {
        return loanselfmobile;
    }

    public void setLoanselfmobile(String loanselfmobile) {
        this.loanselfmobile = loanselfmobile;
    }

    public String getLoanparentmobile() {
        return loanparentmobile;
    }

    public void setLoanparentmobile(String loanparentmobile) {
        this.loanparentmobile = loanparentmobile;
    }

    public String getLoansendmobile() {
        return loansendmobile;
    }

    public void setLoansendmobile(String loansendmobile) {
        this.loansendmobile = loansendmobile;
    }

    public String getLoanselfdesignation() {
        return loanselfdesignation;
    }

    public void setLoanselfdesignation(String loanselfdesignation) {
        this.loanselfdesignation = loanselfdesignation;
    }

    public String getLoanparentdesignation() {
        return loanparentdesignation;
    }

    public void setLoanparentdesignation(String loanparentdesignation) {
        this.loanparentdesignation = loanparentdesignation;
    }

    public String getLoansenddesignation() {
        return loansenddesignation;
    }

    public void setLoansenddesignation(String loansenddesignation) {
        this.loansenddesignation = loansenddesignation;
    }

    public String getLoanselfphoto() {
        return loanselfphoto;
    }

    public void setLoanselfphoto(String loanselfphoto) {
        this.loanselfphoto = loanselfphoto;
    }

    public String getLoanparentphoto() {
        return loanparentphoto;
    }

    public void setLoanparentphoto(String loanparentphoto) {
        this.loanparentphoto = loanparentphoto;
    }

    public String getLoansendphoto() {
        return loansendphoto;
    }

    public void setLoansendphoto(String loansendphoto) {
        this.loansendphoto = loansendphoto;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getFBAId() {
        return FBAId;
    }

    public void setFBAId(String FBAId) {
        this.FBAId = FBAId;
    }

    public String getPOSPNo() {
        return POSPNo;
    }

    public void setPOSPNo(String POSPNo) {
        this.POSPNo = POSPNo;
    }

    public String getPOSP_STATUS() {
        return POSP_STATUS;
    }

    public void setPOSP_STATUS(String POSP_STATUS) {
        this.POSP_STATUS = POSP_STATUS;
    }

    public String getMangMobile() {
        return MangMobile;
    }

    public void setMangMobile(String MangMobile) {
        this.MangMobile = MangMobile;
    }

    public String getMangEmail() {
        return MangEmail;
    }

    public void setMangEmail(String MangEmail) {
        this.MangEmail = MangEmail;
    }

    public String getSuppMobile() {
        return SuppMobile;
    }

    public void setSuppMobile(String SuppMobile) {
        this.SuppMobile = SuppMobile;
    }

    public String getSuppEmail() {
        return SuppEmail;
    }

    public void setSuppEmail(String SuppEmail) {
        this.SuppEmail = SuppEmail;
    }

    public String getPlbanner() {
        return plbanner;
    }

    public void setPlbanner(String plbanner) {
        this.plbanner = plbanner;
    }

    public String getPlactive() {
        return plactive;
    }

    public void setPlactive(String plactive) {
        this.plactive = plactive;
    }

    public String getAddposplimit() {
        return addposplimit;
    }

    public void setAddposplimit(String addposplimit) {
        this.addposplimit = addposplimit;
    }

    public String getServiceurl() {
        return serviceurl;
    }

    public void setServiceurl(String serviceurl) {
        this.serviceurl = serviceurl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMarketinghomepopupid() {
        return marketinghomepopupid;
    }

    public void setMarketinghomepopupid(String marketinghomepopupid) {
        this.marketinghomepopupid = marketinghomepopupid;
    }

    public String getMarketinghometitle() {
        return marketinghometitle;
    }

    public void setMarketinghometitle(String marketinghometitle) {
        this.marketinghometitle = marketinghometitle;
    }

    public String getMarketinghomedesciption() {
        return marketinghomedesciption;
    }

    public void setMarketinghomedesciption(String marketinghomedesciption) {
        this.marketinghomedesciption = marketinghomedesciption;
    }

    public String getMarketinghomemaxcount() {
        return marketinghomemaxcount;
    }

    public void setMarketinghomemaxcount(String marketinghomemaxcount) {
        this.marketinghomemaxcount = marketinghomemaxcount;
    }

    public String getMarketinghomeenabled() {
        return marketinghomeenabled;
    }

    public void setMarketinghomeenabled(String marketinghomeenabled) {
        this.marketinghomeenabled = marketinghomeenabled;
    }

    public String getMarketinghometransfertype() {
        return marketinghometransfertype;
    }

    public void setMarketinghometransfertype(String marketinghometransfertype) {
        this.marketinghometransfertype = marketinghometransfertype;
    }

    public String getMarketinghomeurl() {
        return marketinghomeurl;
    }

    public void setMarketinghomeurl(String marketinghomeurl) {
        this.marketinghomeurl = marketinghomeurl;
    }

    public String getMarketinghomeimageurl() {
        return marketinghomeimageurl;
    }

    public void setMarketinghomeimageurl(String marketinghomeimageurl) {
        this.marketinghomeimageurl = marketinghomeimageurl;
    }

    public String getERPID() {
        return ERPID;
    }

    public void setERPID(String ERPID) {
        this.ERPID = ERPID;
    }

    public String getCrnmobileno() {
        return crnmobileno;
    }

    public void setCrnmobileno(String crnmobileno) {
        this.crnmobileno = crnmobileno;
    }

    public String getMessagesender() {
        return messagesender;
    }

    public void setMessagesender(String messagesender) {
        this.messagesender = messagesender;
    }

    public String getFinmartwhatsappno() {
        return finmartwhatsappno;
    }

    public void setFinmartwhatsappno(String finmartwhatsappno) {
        this.finmartwhatsappno = finmartwhatsappno;
    }

    public String getPBByCrnSearch() {
        return PBByCrnSearch;
    }

    public void setPBByCrnSearch(String PBByCrnSearch) {
        this.PBByCrnSearch = PBByCrnSearch;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

    public String getFinperkurl() {
        return finperkurl;
    }

    public void setFinperkurl(String finperkurl) {
        this.finperkurl = finperkurl;
    }

    public String getFinboxurl() {
        return finboxurl;
    }

    public void setFinboxurl(String finboxurl) {
        this.finboxurl = finboxurl;
    }

    public RealmList<DashboardarrayEntity> getDashboardarray() {
        return dashboardarray;
    }

    public void setDashboardarray(RealmList<DashboardarrayEntity> dashboardarray) {
        this.dashboardarray = dashboardarray;
    }


    public String getLeadDashUrl() {
        return LeadDashUrl;
    }

    public void setLeadDashUrl(String leadDashUrl) {
        LeadDashUrl = leadDashUrl;
    }

    public String getEliteKotakUrl() {
        return EliteKotakUrl;
    }

    public void setEliteKotakUrl(String eliteKotakUrl) {
        EliteKotakUrl = eliteKotakUrl;
    }


    public String getKotakEliteEnabled() {
        return KotakEliteEnabled;
    }

    public void setKotakEliteEnabled(String kotakEliteEnabled) {
        KotakEliteEnabled = kotakEliteEnabled;
    }

    public String getEnablesynccontact() {
        return enablesynccontact;
    }

    public void setEnablesynccontact(String enablesynccontact) {
        this.enablesynccontact = enablesynccontact;
    }

    public int getAndroidproversion() {
        return androidproversion;
    }

    public void setAndroidproversion(int androidproversion) {
        this.androidproversion = androidproversion;
    }

    public String getEnablemyaccountupdate() {
        return enablemyaccountupdate;
    }

    public void setEnablemyaccountupdate(String enablemyaccountupdate) {
        this.enablemyaccountupdate = enablemyaccountupdate;
    }
}