package magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.register;

import android.content.Context;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.AsyncBikeMaster;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.AsyncMultiLangOldMaster;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestbuilder.RegisterRequestBuilder;
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
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajeev Ranjan on 22/01/2018.
 */

public class RegisterController implements IRegister {
    RegisterRequestBuilder.RegisterQuotesNetworkService registerQuotesNetworkService;
    Context mContext;
    IResponseSubcriber iResponseSubcriber;
    DBPersistanceController dbPersistanceController;

    public RegisterController(Context context) {
        registerQuotesNetworkService = new RegisterRequestBuilder().getService();
        mContext = context;
        dbPersistanceController = new DBPersistanceController(mContext);
    }

    @Override
    public void getRegSource(final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.getRegSource().enqueue(new Callback<RegisterSourceResponse>() {
            @Override
            public void onResponse(Call<RegisterSourceResponse> call, Response<RegisterSourceResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RegisterSourceResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getRegistPospAmount(final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.getRegistPospAmount().enqueue(new Callback<RegisterationPospAmountResponse>() {
            @Override
            public void onResponse(Call<RegisterationPospAmountResponse> call, Response<RegisterationPospAmountResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RegisterationPospAmountResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void generateOtp(String MobileNo,String email, final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("MobileNo", MobileNo);
        body.put("email",email);
        registerQuotesNetworkService.generateOtp(body).enqueue(new Callback<GenerateOtpResponse>() {
            @Override
            public void onResponse(Call<GenerateOtpResponse> call, Response<GenerateOtpResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<GenerateOtpResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void validateOtp(String MobileNo, String MobileOTP, final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("MobileNo", MobileNo);
        body.put("MobileOTP", MobileOTP);
        registerQuotesNetworkService.verifyOtp(body).enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getCityState(String PinCode, final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("PinCode", PinCode);
        registerQuotesNetworkService.getCityStateCityPincode(body).enqueue(new Callback<PincodeResponse>() {
            @Override
            public void onResponse(Call<PincodeResponse> call, Response<PincodeResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<PincodeResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void registerFba(RegisterRequestEntity registerRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.registerFba(registerRequestEntity).enqueue(new Callback<RegisterFbaResponse>() {
            @Override
            public void onResponse(Call<RegisterFbaResponse> call, Response<RegisterFbaResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RegisterFbaResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getPospDetails(final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("FBAID", "" + dbPersistanceController.getUserData().getFBAId());

        registerQuotesNetworkService.getPospDetails(body).enqueue(new Callback<PospDetailsResponse>() {
            @Override
            public void onResponse(Call<PospDetailsResponse> call, Response<PospDetailsResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<PospDetailsResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }


    @Override
    public void enrollPosp(RegisterRequestEntity registerRequestEntity, final IResponseSubcriber iResponseSubcriber) {
        registerQuotesNetworkService.enrollPosp(registerRequestEntity).enqueue(new Callback<EnrollPospResponse>() {
            @Override
            public void onResponse(Call<EnrollPospResponse> call, Response<EnrollPospResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<EnrollPospResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getIFSC(String IfscCode, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("IFSCCode", IfscCode);
        registerQuotesNetworkService.getIfscCode(body).enqueue(new Callback<IfscCodeResponse>() {
            @Override
            public void onResponse(Call<IfscCodeResponse> call, Response<IfscCodeResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<IfscCodeResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void saveAccDtl(RegisterRequestEntity registerRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.saveAccDtl(registerRequestEntity).enqueue(new Callback<MyAccountResponse>() {
            @Override
            public void onResponse(Call<MyAccountResponse> call, Response<MyAccountResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<MyAccountResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void uploadDocuments(MultipartBody.Part document, HashMap<String, String> body, final IResponseSubcriber iResponseSubcriber) {


        registerQuotesNetworkService.uploadDocument(document, body).enqueue(new Callback<DocumentResponse>() {
            @Override
            public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatusNo() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<DocumentResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void newUploadDocuments(MultipartBody.Part document, int FBAID, int DocType, String DocName, final IResponseSubcriber iResponseSubcriber) {
        Map<String, String> partMapString = new HashMap<String, String>();
        partMapString.put("DocName", DocName);
        Map<String, Integer> partMapInt = new HashMap<String, Integer>();
        partMapInt.put("FBAID", FBAID);
        partMapInt.put("DocType", DocType);
        registerQuotesNetworkService.uploadDocumentNew(document, partMapString, partMapInt).enqueue(new Callback<DocumentResponse>() {
            @Override
            public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatusNo() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), "");
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }


                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<DocumentResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getMyAcctDtl(String FBAID, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("FBAID", FBAID);
        registerQuotesNetworkService.getMyAcctDtl(body).enqueue(new Callback<MyAcctDtlResponse>() {
            @Override
            public void onResponse(Call<MyAcctDtlResponse> call, Response<MyAcctDtlResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<MyAcctDtlResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getNotificationData(String FBAID, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("FBAID", FBAID);
        registerQuotesNetworkService.getNotificationData(body).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void getReceiveNotificationData(String NotifyReqID, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("UserNotificationRequestId", NotifyReqID);
        registerQuotesNetworkService.recieveNotificationData(body).enqueue(new Callback<NotificationUpdateResponse>() {
            @Override
            public void onResponse(Call<NotificationUpdateResponse> call, Response<NotificationUpdateResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    //  iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    // iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<NotificationUpdateResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getUserClickActionOnNotification(String NotifyReqID, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("UserNotificationRequestId", NotifyReqID);
        registerQuotesNetworkService.userClickActionOnNotification(body).enqueue(new Callback<NotificationUpdateResponse>() {
            @Override
            public void onResponse(Call<NotificationUpdateResponse> call, Response<NotificationUpdateResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    // iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    //iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<NotificationUpdateResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void addChildPosp(RegisterRequestEntity registerRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.addChildPosp(registerRequestEntity).enqueue(new Callback<RegisterFbaResponse>() {
            @Override
            public void onResponse(Call<RegisterFbaResponse> call, Response<RegisterFbaResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RegisterFbaResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getChildPosp(final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("FBAID", "" + dbPersistanceController.getUserData().getFBAId());
        registerQuotesNetworkService.getChildPosp(body).enqueue(new Callback<ChildPospResponse>() {
            @Override
            public void onResponse(Call<ChildPospResponse> call, Response<ChildPospResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<ChildPospResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void saveContactLead(ContactLeadRequestEntity contactLeadRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.saveContactLead(contactLeadRequestEntity).enqueue(new Callback<ContactLeadResponse>() {
            @Override
            public void onResponse(Call<ContactLeadResponse> call, Response<ContactLeadResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), "");

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<ContactLeadResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void uploadContact(ContactLeadRequestEntity contactLeadRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.saveContactLead(contactLeadRequestEntity).enqueue(new Callback<ContactLeadResponse>() {
            @Override
            public void onResponse(Call<ContactLeadResponse> call, Response<ContactLeadResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<ContactLeadResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getSmsTemplate(final IResponseSubcriber iResponseSubcriber) {

        registerQuotesNetworkService.getSmsTemplate().enqueue(new Callback<SmsTemplateResponse>() {
            @Override
            public void onResponse(Call<SmsTemplateResponse> call, Response<SmsTemplateResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<SmsTemplateResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getEmailTemplate(String URL, String Type, final IResponseSubcriber iResponseSubcriber) {


        HashMap<String, String> body = new HashMap<>();
        body.put("FBAID", "" + dbPersistanceController.getUserData().getFBAId());
        body.put("url", "" + URL);
        body.put("type", "" + Type);
        registerQuotesNetworkService.getEmailTemplate(body).enqueue(new Callback<PospAppointEmailResponse>() {
            @Override
            public void onResponse(Call<PospAppointEmailResponse> call, Response<PospAppointEmailResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<PospAppointEmailResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getfieldsales(String campaignid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("campaignid", "" + campaignid);

        registerQuotesNetworkService.getfieldsales(body).enqueue(new Callback<RegisterSaleResponse>() {
            @Override
            public void onResponse(Call<RegisterSaleResponse> call, Response<RegisterSaleResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RegisterSaleResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void sendSyncSms(final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("fbaid", "" + dbPersistanceController.getUserData().getFBAId());

        registerQuotesNetworkService.sendSyncSms(body).enqueue(new Callback<SendSyncSmsResponse>() {
            @Override
            public void onResponse(Call<SendSyncSmsResponse> call, Response<SendSyncSmsResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<SendSyncSmsResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getUserCallingDetail(String fbaid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("fbaid", "" + dbPersistanceController.getUserData().getFBAId());

        registerQuotesNetworkService.getUserCallingDetail(body).enqueue(new Callback<UserCallingResponse>() {
            @Override
            public void onResponse(Call<UserCallingResponse> call, Response<UserCallingResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<UserCallingResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getMultiLanguageDetail(final IResponseSubcriber iResponseSubcriber) {


        registerQuotesNetworkService.getMultiLanguageDetail().enqueue(new Callback<MultilanguageResponse>() {
            @Override
            public void onResponse(Call<MultilanguageResponse> call, Response<MultilanguageResponse> response) {

                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<MultilanguageResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });

    }


    @Override
    public void getMultiLanguageDetailOld(final IResponseSubcriber iResponseSubcriber) {


        registerQuotesNetworkService.getMultiLanguageDetailOld().enqueue(new Callback<MultiLangResponse>() {
            @Override
            public void onResponse(Call<MultiLangResponse> call, Response<MultiLangResponse> response) {

                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    new AsyncMultiLangOldMaster(mContext, response.body().getMasterData()).execute();

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<MultiLangResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void getProductShareUrl(int fba_id, int ss_id, int product_id, int sub_fba_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, Integer> body = new HashMap<>();
        body.put("fba_id", fba_id);
        body.put("ss_id", ss_id);
        body.put("product_id", product_id);
        body.put("sub_fba_id", sub_fba_id);

        registerQuotesNetworkService.getProductShareURL(body).enqueue(new Callback<ProductURLShareResponse>() {
            @Override
            public void onResponse(Call<ProductURLShareResponse> call, Response<ProductURLShareResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<ProductURLShareResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void hideFOSUser(String PospId, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("PospId", PospId);

        registerQuotesNetworkService.hideFOSUser(body).enqueue(new Callback<UserHideResponse>() {
            @Override
            public void onResponse(Call<UserHideResponse> call, Response<UserHideResponse> response) {
                if (response.body() != null) {

                    // store response to facade
                    new DBPersistanceController(mContext).storeFOSDetail(response.body());

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<UserHideResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void addToRazorPay(String FBAID, String CustId, String PayId, final IResponseSubcriber iResponseSubcriber) {


        HashMap<String, String> body = new HashMap<>();
        body.put("FBAID", FBAID);
        body.put("CustId", CustId);
        body.put("PayId", PayId);


        registerQuotesNetworkService.addToRazorPay(body).enqueue(new Callback<RazorPayResponse>() {
            @Override
            public void onResponse(Call<RazorPayResponse> call, Response<RazorPayResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RazorPayResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getDataForPayment(String FBAID, final IResponseSubcriber iResponseSubcriber) {


        HashMap<String, String> body = new HashMap<>();
        body.put("FBAID", FBAID);

        registerQuotesNetworkService.getDataForPayment(body).enqueue(new Callback<PaymentDetailResponse>() {
            @Override
            public void onResponse(Call<PaymentDetailResponse> call, Response<PaymentDetailResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<PaymentDetailResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void getDataForPayment_elite(String custid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();
        body.put("custid", custid);

        registerQuotesNetworkService.getDataForPayment_EliteCustomer(body).enqueue(new Callback<PaymentDetail_EliteResponse>() {
            @Override
            public void onResponse(Call<PaymentDetail_EliteResponse> call, Response<PaymentDetail_EliteResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<PaymentDetail_EliteResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void addToRazorPay_elite(String FBAID, String CustId, String PayId, final IResponseSubcriber iResponseSubcriber) {


        HashMap<String, String> body = new HashMap<>();
        // body.put("FBAID",FBAID);
        body.put("custid", CustId);
        body.put("PayId", PayId);


        registerQuotesNetworkService.addToRazorPayElite(body).enqueue(new Callback<RazorPayResponse>() {
            @Override
            public void onResponse(Call<RazorPayResponse> call, Response<RazorPayResponse> response) {
                if (response.body() != null) {

                    //callback of data
                    iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<RazorPayResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }
}
