package magicfinmart.datacomp.com.finmartserviceapi.finmart.requestbuilder;

import java.util.HashMap;
import java.util.Map;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.ContactLeadRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.RegisterRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ChildPospResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ContactLeadResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.DocumentResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.EnrollPospResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.GenerateOtpResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.IfscCodeResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MultiLangResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MultilanguageResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MyAccountResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.MyAcctDtlResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.NotificationResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.NotificationUpdateResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PaymentDetailResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PaymentDetail_EliteResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PincodeResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PospAgentResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PospAppointEmailResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.PospDetailsResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ProductURLShareResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RazorPayResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RegisterFbaResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RegisterSaleResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RegisterSourceResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RegisterationPospAmountResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.SendSyncSmsResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.SmsTemplateResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserCallingResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.UserHideResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.VerifyOtpResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.retrobuilder.FinmartRetroRequestBuilder;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Rajeev Ranjan on 22/01/2018.
 */

public class RegisterRequestBuilder extends FinmartRetroRequestBuilder {

    public RegisterRequestBuilder.RegisterQuotesNetworkService getService() {

        return super.build().create(RegisterRequestBuilder.RegisterQuotesNetworkService.class);
    }

    public interface RegisterQuotesNetworkService {

        @Headers("token:" + token)
        @POST("/quote/Postfm/generate-otp")
        Call<GenerateOtpResponse> generateOtp(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/retrive-otp")
        Call<VerifyOtpResponse> verifyOtp(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/get-city-and-state")
        Call<PincodeResponse> getCityStateCityPincode(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/insert-fba-registration")
        Call<RegisterFbaResponse> registerFba(@Body RegisterRequestEntity body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/AddChildPosp")
        Call<RegisterFbaResponse> addChildPosp(@Body RegisterRequestEntity body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/get-child-fba")
        Call<ChildPospResponse> getChildPosp(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/posp-registration")
        Call<EnrollPospResponse> enrollPosp(@Body RegisterRequestEntity body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/get-posp-detail")
        Call<PospDetailsResponse> getPospDetails(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/get-ifsc-code")
        Call<IfscCodeResponse> getIfscCode(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/my-account")
        Call<MyAccountResponse> saveAccDtl(@Body RegisterRequestEntity body);


        @Headers("token:" + token)
        @POST("/quote/Postfm/get-my-account")
        Call<MyAcctDtlResponse> getMyAcctDtl(@Body HashMap<String, String> body);


        @Headers("token:" + token)
        @POST("/quote/Postfm/get-registration-source")
        Call<RegisterSourceResponse> getRegSource();

        @Headers("token:" + token)
        @POST("/quote/Postfm/get-registration-pospamount")
        Call<RegisterationPospAmountResponse> getRegistPospAmount();

        @Headers("token:" + token)
        @Multipart
        @POST("/quote/Postfm_fileupload/upload-doc")
        Call<DocumentResponse> uploadDocument(@Part() MultipartBody.Part doc, @PartMap() Map<String, String> partMap);

        @Headers("token:" + token)
        @Multipart
        @POST("/quote/Postfm_fileupload/upload-doc")
        Call<DocumentResponse> uploadDocumentNew(@Part() MultipartBody.Part doc, @PartMap() Map<String, String> partMapString, @PartMap() Map<String, Integer> partMapInt);


////////////////////// Notification ////////////////////////////////

        @Headers("token:" + token)
        @POST("/quote/Postfm/get-notification-data")
        Call<NotificationResponse> getNotificationData(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/UserClickActionOnNotification")
        Call<NotificationUpdateResponse> recieveNotificationData(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/update-notification")
        Call<NotificationUpdateResponse> userClickActionOnNotification(@Body HashMap<String, String> body);

        ////////////////////// Contact Lead ////////////////////////////////

        @Headers("token:" + token)
        @POST("/quote/Postfm/addcontacts")
        Call<ContactLeadResponse> saveContactLead(@Body ContactLeadRequestEntity body);

        ////////////////////// Posp Sms & Email ////////////////////////////////

        @Headers("token:" + token)
        @POST("/quote/Postfm/get-customers-sms-template_pb")
        Call<SmsTemplateResponse> getSmsTemplate();


        @Headers("token:" + token)
        @POST("/quote/Postfm/posp-appointment-email")
        Call<PospAppointEmailResponse> getEmailTemplate(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/getempbyregsource")
        Call<RegisterSaleResponse> getfieldsales(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/sync-lead-data-send-sms")
        Call<SendSyncSmsResponse> sendSyncSms(@Body HashMap<String, String> body);

    ///////////////////////////////// Home page ///////////////////////////////

        @Headers("token:" + token)
        @POST("/quote/Postfm/user-calling")
        Call<UserCallingResponse> getUserCallingDetail(@Body HashMap<String, String> body);

        ///////////////////////////////// Multi Language Calling ///////////////////////////////
        @Headers("token:" + token)
        @POST("/quote/Postfm/GetLangData")
        Call<MultilanguageResponse> getMultiLanguageDetail();


        @Headers("token:" + token)
        @POST("/quote/Postfm/GetLangDataOld")
        Call<MultiLangResponse> getMultiLanguageDetailOld();

        @Headers("token:" + token)
        @POST("/quote/Postfm/GetShareUrl")
        Call<ProductURLShareResponse> getProductShareURL(@Body HashMap<String, Integer> body);


        @Headers("token:" + token)
        @POST("/quote/Postfm/GetFosInfo")
        Call<UserHideResponse> hideFOSUser(@Body HashMap<String, String> body);


        // Payment Gateway

        @Headers("token:" + token)
        @POST("/quote/Postfm/Addtorazorpaydata")
        Call<RazorPayResponse> addToRazorPay(@Body HashMap<String, String> body);


        @Headers("token:" + token)
        @POST("/quote/Postfm/getfbadataforrpay")
        Call<PaymentDetailResponse> getDataForPayment(@Body HashMap<String, String> body);


        @Headers("token:" + token)
        @POST("/quote/Postfm/GetEliteKotakCustomer")
        Call<PaymentDetail_EliteResponse> getDataForPayment_EliteCustomer(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/quote/Postfm/EliteKotakRazorPayment")
        Call<RazorPayResponse> addToRazorPayElite(@Body HashMap<String, String> body);

    }






}
