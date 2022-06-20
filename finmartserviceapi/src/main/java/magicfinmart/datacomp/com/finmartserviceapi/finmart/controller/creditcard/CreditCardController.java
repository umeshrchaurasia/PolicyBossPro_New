package magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.creditcard;

import android.content.Context;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import magicfinmart.datacomp.com.finmartserviceapi.database.DBPersistanceController;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.IResponseSubcriber;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.controller.masters.AsyncRblCityMaster;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestbuilder.CreditCardRequestBuilder;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.CCICICIRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.CCRblRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.AppliedCreditCardResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.CCICICIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.CCRblResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.CreditCardMasterResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.ICICICompanyResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RblCityMasterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nilesh Birhade on 09/02/2018.
 */

public class CreditCardController implements ICreditCard {

    CreditCardRequestBuilder.CreditCardNetworkService creditCardNetworkService;

    Context mContext;

    public CreditCardController(Context context) {
        creditCardNetworkService = new CreditCardRequestBuilder().getService();
        mContext = context;
    }

    @Override
    public void getICICICompany(String companyName, final IResponseSubcriber iResponseSubcriber) {

        String url = "http://www.rupeeboss.com/api/icici-company-master?search_company=" + companyName;
        creditCardNetworkService.getICICICompany(url).enqueue(new Callback<ICICICompanyResponse>() {
            @Override
            public void onResponse(Call<ICICICompanyResponse> call, Response<ICICICompanyResponse> response) {
                if (response.isSuccessful()) {
                    iResponseSubcriber.OnSuccess(response.body(), "");
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ICICICompanyResponse> call, Throwable t) {
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
    public void getRblCityMaster(final IResponseSubcriber iResponseSubcriber) {

        creditCardNetworkService.getRblCityMaster().enqueue(new Callback<RblCityMasterResponse>() {
            @Override
            public void onResponse(Call<RblCityMasterResponse> call, Response<RblCityMasterResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatusNo() == 0) {
                        new AsyncRblCityMaster(mContext, response.body().getMasterData()).execute();
                    } else {
                        if (iResponseSubcriber != null)
                            iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                    }

                } else {
                    if (iResponseSubcriber != null)
                        iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                }

            }

            @Override
            public void onFailure(Call<RblCityMasterResponse> call, Throwable t) {
                if (iResponseSubcriber != null) {
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
            }
        });
    }

    @Override
    public void applyICICI(CCICICIRequestEntity cciciciRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        creditCardNetworkService.applyICICI(cciciciRequestEntity).enqueue(new Callback<CCICICIResponse>() {
            @Override
            public void onResponse(Call<CCICICIResponse> call, Response<CCICICIResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatusNo() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                }
            }

            @Override
            public void onFailure(Call<CCICICIResponse> call, Throwable t) {
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
    public void getAllCreditCards(final IResponseSubcriber iResponseSubcriber) {

        creditCardNetworkService.getAllCreditCards().enqueue(new Callback<CreditCardMasterResponse>() {
            @Override
            public void onResponse(Call<CreditCardMasterResponse> call, Response<CreditCardMasterResponse> response) {

                if (response.body() != null) {
                    if (response.body().getStatusNo() == 0) {

                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException("Failed to fetch information."));
                }

            }

            @Override
            public void onFailure(Call<CreditCardMasterResponse> call, Throwable t) {

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
    public void applyRbl(CCRblRequestEntity rblRequestEntity, final IResponseSubcriber iResponseSubcriber) {

        creditCardNetworkService.applyRbl(rblRequestEntity).enqueue(new Callback<CCRblResponse>() {
            @Override
            public void onResponse(Call<CCRblResponse> call, Response<CCRblResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatusNo() == 0) {

                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException("Failed to fetch information."));
                }
            }

            @Override
            public void onFailure(Call<CCRblResponse> call, Throwable t) {
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
    public void getAppliedCreditCards(final IResponseSubcriber iResponseSubcriber) {
        HashMap<String, String> body = new HashMap<>();
        body.put("fba_id", String.valueOf(new DBPersistanceController(mContext).getUserData().getFBAId()));
        body.put("CardType", "0");
        creditCardNetworkService.getAppliedCreditCards(body).enqueue(new Callback<AppliedCreditCardResponse>() {
            @Override
            public void onResponse(Call<AppliedCreditCardResponse> call, Response<AppliedCreditCardResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatusNo() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException("Failed to fetch information."));
                }
            }

            @Override
            public void onFailure(Call<AppliedCreditCardResponse> call, Throwable t) {
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
