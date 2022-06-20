package magicfinmart.datacomp.com.finmartserviceapi.finmart.response

import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.RegisterPospAmountModel

data class RegisterationPospAmountResponse (
val MasterData: List<RegisterPospAmountModel>

): APIResponse()