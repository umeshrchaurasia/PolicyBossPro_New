package magicfinmart.datacomp.com.finmartserviceapi.finmart.requestbuilder;

import java.util.HashMap;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.HealthQuote;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.HealthCompareRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.requestentity.TermFinmartRequest;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.BenefitsListResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.HealthDeleteResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.HealthQuoteAppResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.HealthQuoteCompareResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.HealthQuoteExpResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.HealthQuoteResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.HealthQuotetoAppResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.TermCompareQuoteResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.response.TermQuoteApplicationResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.retrobuilder.FinmartRetroRequestBuilder;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Rajeev Ranjan on 25/01/2018.
 */

public class TermRequestBuilder extends FinmartRetroRequestBuilder {

    public TermNetworkService getService() {

        return super.build().create(TermNetworkService.class);
    }

    public interface TermNetworkService {

        @Headers("token:" + token)
        @POST("/api/smart-term-life")
        Call<TermCompareQuoteResponse> getTermCompareQuotes(@Body TermFinmartRequest body);

        @Headers("token:" + token)
        @POST("/api/get-smart-term-life")
        Call<TermQuoteApplicationResponse> getTermQuoteApplication(@Body HashMap<String, String> body);
/*
        @Headers("token:" + token)
        @POST("/api/smart-health")
        Call<HealthQuoteExpResponse> getHealthQuoteExp(@Body HealthQuote body);


        @Headers("token:" + token)
        @POST("/api/set-quote-application-smart-health")
        Call<HealthQuotetoAppResponse> convertHealthQuoteToApp(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/api/delete-smart-health")
        Call<HealthDeleteResponse> deleteQuote(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/api/compare-premium")
        Call<HealthQuoteCompareResponse> compareQuotes(@Body HealthCompareRequestEntity entity);

        @Headers("token:" + token)
        @POST("/api/GetCompareBenefits")
        Call<BenefitsListResponse> getBenefits(@Body HashMap<String, String> body);*/
    }
}
