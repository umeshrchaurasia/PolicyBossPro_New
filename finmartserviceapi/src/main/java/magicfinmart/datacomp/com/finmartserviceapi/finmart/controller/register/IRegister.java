package magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register;


import java.util.HashMap;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.ContactLeadRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.RegisterRequestEntity;
import okhttp3.MultipartBody;

/**
 * Created by Rajeev Ranjan on 22/01/2018.
 */

public interface IRegister {
    void generateOtp(String MobileNo,String email, IResponseSubcriber iResponseSubcriber);

    void validateOtp(String MobileNo, String MobileOTP, IResponseSubcriber iResponseSubcriber);


    void getCityState(String PinCode, IResponseSubcriber iResponseSubcriber);

    void registerFba(RegisterRequestEntity registerRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getPospDetails(IResponseSubcriber iResponseSubcriber);

    void enrollPosp(RegisterRequestEntity registerRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getIFSC(String IfscCode, IResponseSubcriber iResponseSubcriber);

    void saveAccDtl(RegisterRequestEntity registerRequestEntity, IResponseSubcriber iResponseSubcriber);

    void uploadDocuments(MultipartBody.Part document, HashMap<String, String> body, final IResponseSubcriber iResponseSubcriber);

    void newUploadDocuments(MultipartBody.Part document, int FBAID, int DocType, String DocName, final IResponseSubcriber iResponseSubcriber);

    void getMyAcctDtl(String FBAID, IResponseSubcriber iResponseSubcriber);

    void getNotificationData(String FBAID, IResponseSubcriber iResponseSubcriber);

    void getReceiveNotificationData(String NotifyReqID, IResponseSubcriber iResponseSubcriber);

    void getUserClickActionOnNotification(String NotifyReqID, IResponseSubcriber iResponseSubcriber);

    void getRegSource(IResponseSubcriber iResponseSubcriber);

    void getRegistPospAmount(IResponseSubcriber iResponseSubcriber);

    void addChildPosp(RegisterRequestEntity registerRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getChildPosp(IResponseSubcriber iResponseSubcriber);

    void saveContactLead(ContactLeadRequestEntity contactLeadRequestEntity, IResponseSubcriber iResponseSubcriber);

    void uploadContact(ContactLeadRequestEntity contactLeadRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getSmsTemplate(IResponseSubcriber iResponseSubcriber);

    void getEmailTemplate(String URL, String Type, IResponseSubcriber iResponseSubcriber);

    void getfieldsales( String campaignid, IResponseSubcriber iResponseSubcriber);

    void sendSyncSms(IResponseSubcriber iResponseSubcriber);

    void getUserCallingDetail(String fbaid, IResponseSubcriber iResponseSubcriber);

    void getMultiLanguageDetail(IResponseSubcriber iResponseSubcriber);

    void getMultiLanguageDetailOld(IResponseSubcriber iResponseSubcriber);

    void getProductShareUrl(int fba_id,int ss_id,int product_id,int sub_fba_id, IResponseSubcriber iResponseSubcriber);

    void hideFOSUser(String PospId, IResponseSubcriber iResponseSubcriber);



    void addToRazorPay(String FBAID, String CustId,String PayId, IResponseSubcriber iResponseSubcriber);


    void getDataForPayment(String FBAID , IResponseSubcriber iResponseSubcriber);

    void getDataForPayment_elite(String custid , IResponseSubcriber iResponseSubcriber);

    void addToRazorPay_elite(String FBAID, String CustId,String PayId, IResponseSubcriber iResponseSubcriber);

}
