package magicfinmart.datacomp.com.finmartserviceapi.finmart.requestbuilder;

import java.util.HashMap;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.CCRblRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.AppliedCreditCardResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.CCRblResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.CreditCardMasterResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.RblCityMasterResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.retrobuilder.FinmartRetroRequestBuilder;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Nilesh birhade on 23/02/2018.
 */

public class CreditCardRequestBuilder extends FinmartRetroRequestBuilder {

    public CreditCardNetworkService getService() {

        return super.build().create(CreditCardNetworkService.class);
    }

    public interface CreditCardNetworkService {

        @Headers("token:" + token)
        @POST("/api/get-credit-card-data")
        Call<CreditCardMasterResponse> getAllCreditCards();

        @Headers("token:" + token)
        @POST("/api/credit-card-rbl")
        Call<CCRblResponse> applyRbl(@Body CCRblRequestEntity ccRblRequestEntity);


        @Headers("token:" + token)
        @POST("/api/get-saved-creditcard-info")
        Call<AppliedCreditCardResponse> getAppliedCreditCards(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/api/get-rbl-city")
        Call<RblCityMasterResponse> getRblCityMaster();


    }
}
