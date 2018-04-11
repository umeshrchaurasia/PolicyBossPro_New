package magicfinmart.datacomp.com.finmartserviceapi.express_loan.requestbuilder;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.HashMap;

import magicfinmart.datacomp.com.finmartserviceapi.express_loan.requestentity.SaveExpressLoanRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.express_loan.response.ExpressLoanListResponse;
import magicfinmart.datacomp.com.finmartserviceapi.express_loan.response.ExpressQuoteListResponse;
import magicfinmart.datacomp.com.finmartserviceapi.express_loan.response.ExpressSaveResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.retrobuilder.FinmartRetroRequestBuilder;
import magicfinmart.datacomp.com.finmartserviceapi.motor.requestbuilder.MotorQuotesRequestBuilder;
import magicfinmart.datacomp.com.finmartserviceapi.motor.requestentity.BikePremiumRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.requestentity.MotorRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.requestentity.SaveAddOnRequestEntity;
import magicfinmart.datacomp.com.finmartserviceapi.motor.response.BikePremiumResponse;
import magicfinmart.datacomp.com.finmartserviceapi.motor.response.BikeUniqueResponse;
import magicfinmart.datacomp.com.finmartserviceapi.motor.response.SaveAddOnResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by IN-RB on 03-04-2018.
 */

public class ExpressLoanRequestBuilder extends FinmartRetroRequestBuilder {

    public ExpressLoanRequestBuilder.ExpressNetworkService getService() {

        return super.build().create(ExpressLoanRequestBuilder.ExpressNetworkService.class);
    }

    public interface ExpressNetworkService {

        @Headers("token:" + token)
        @POST("/api/get-express-loan")
        Call<ExpressQuoteListResponse> getExpressQuoteList(@Body HashMap<String, String> body);

        @Headers("token:" + token)
        @POST("/api/express-loan")
        Call<ExpressLoanListResponse> getExpressLoanList();

        @Headers("token:" + token)
        @POST("/api/save-loan")
        Call<ExpressSaveResponse> saveExpressLoan(@Body SaveExpressLoanRequestEntity body);


//        @POST("/quote/premium_list_db")
//        Call<BikePremiumResponse> getPremiumList(@Body BikePremiumRequestEntity body);
//

    }



}