package magicfinmart.datacomp.com.finmartserviceapi.finmart.response;

import magicfinmart.datacomp.com.finmartserviceapi.finmart.APIResponse;
import magicfinmart.datacomp.com.finmartserviceapi.finmart.model.ZohoTicketCategoryEntity;

/**
 * Created by Rajeev Ranjan on 01/03/2018.
 */

public class TicketCategoryResponse extends APIResponse {
    /**
     * MasterDataEntity : {"category":[{"CateCode":"1","CateName":"FBA App","TranTypeID":1},{"CateCode":"2","CateName":"Training App","TranTypeID":2},{"CateCode":"3","CateName":"Product","TranTypeID":3}],"subcategory":[{"QuerType":"Notification Problem","QuerID":1,"CateCode":"1"},{"QuerType":"Marketing Collateral","QuerID":2,"CateCode":"1"},{"QuerType":"Login Issue","QuerID":3,"CateCode":"1"},{"QuerType":"Business MIS problem","QuerID":4,"CateCode":"1"},{"QuerType":"Offline Product Sales","QuerID":5,"CateCode":"1"},{"QuerType":"Rewards Issue","QuerID":6,"CateCode":"1"},{"QuerType":"Grievance","QuerID":7,"CateCode":"1"},{"QuerType":"Feedback","QuerID":8,"CateCode":"1"},{"QuerType":"Query","QuerID":9,"CateCode":"1"},{"QuerType":"Training Hours Logging","QuerID":10,"CateCode":"2"},{"QuerType":"Login Issue","QuerID":11,"CateCode":"2"},{"QuerType":"Login Issue","QuerID":12,"CateCode":"2"},{"QuerType":"Training App Link Not received","QuerID":13,"CateCode":"2"},{"QuerType":"Photograph Related","QuerID":14,"CateCode":"2"},{"QuerType":"Exam not enabled","QuerID":15,"CateCode":"2"},{"QuerType":"POSP Certificate not received","QuerID":16,"CateCode":"2"},{"QuerType":"Not Certified POSP Error","QuerID":17,"CateCode":"2"},{"QuerType":"Greivance","QuerID":18,"CateCode":"2"},{"QuerType":"Feedback","QuerID":19,"CateCode":"2"},{"QuerType":"Query","QuerID":20,"CateCode":"2"},{"QuerType":"Personal Loan","QuerID":21,"CateCode":"3"},{"QuerType":"Motor","QuerID":22,"CateCode":"3"},{"QuerType":"Home Loan","QuerID":23,"CateCode":"3"},{"QuerType":"Health Packages","QuerID":24,"CateCode":"3"},{"QuerType":"Health Insurance","QuerID":25,"CateCode":"3"},{"QuerType":"Fin Peace","QuerID":26,"CateCode":"3"},{"QuerType":"Credit Card","QuerID":27,"CateCode":"3"},{"QuerType":"Balance Transfer","QuerID":28,"CateCode":"3"}],"classification":[{"ID":1,"QuerID":21,"Description":"Proposal Link"},{"ID":2,"QuerID":21,"Description":"Document upload"},{"ID":3,"QuerID":21,"Description":"Feedback"},{"ID":4,"QuerID":21,"Description":"Greivance"},{"ID":5,"QuerID":21,"Description":"Other"},{"ID":6,"QuerID":21,"Description":"Application Status"},{"ID":7,"QuerID":22,"Description":"Quote related"},{"ID":8,"QuerID":22,"Description":"Payment Link"},{"ID":9,"QuerID":22,"Description":"Proposal Link"},{"ID":10,"QuerID":22,"Description":"Feedback"},{"ID":11,"QuerID":22,"Description":"Greivance"},{"ID":12,"QuerID":22,"Description":"None"},{"ID":13,"QuerID":22,"Description":"Policy Issuance"},{"ID":14,"QuerID":22,"Description":"Policy Cancellation"},{"ID":15,"QuerID":22,"Description":"Policy refund"},{"ID":16,"QuerID":22,"Description":"Policy rectification"},{"ID":17,"QuerID":22,"Description":"Claim Issue"},{"ID":18,"QuerID":23,"Description":"Quote related"},{"ID":19,"QuerID":23,"Description":"Payment Link"},{"ID":20,"QuerID":23,"Description":"Medical tests"},{"ID":21,"QuerID":23,"Description":"ID Cards"},{"ID":22,"QuerID":23,"Description":"Proposal Link"},{"ID":23,"QuerID":23,"Description":"Document upload"},{"ID":24,"QuerID":23,"Description":"Feedback"},{"ID":25,"QuerID":23,"Description":"Greivance"},{"ID":26,"QuerID":23,"Description":"None"},{"ID":27,"QuerID":23,"Description":"Policy Issuance"},{"ID":28,"QuerID":23,"Description":"Policy Cancellation"},{"ID":29,"QuerID":23,"Description":"Policy refund"},{"ID":30,"QuerID":23,"Description":"Policy rectification"},{"ID":31,"QuerID":23,"Description":"Claim Issue"},{"ID":32,"QuerID":23,"Description":"Application Status"},{"ID":33,"QuerID":24,"Description":"Feedback"},{"ID":34,"QuerID":24,"Description":"Greivance"},{"ID":35,"QuerID":24,"Description":"Other"},{"ID":36,"QuerID":25,"Description":"Quote related"},{"ID":37,"QuerID":25,"Description":"Payment Link"},{"ID":38,"QuerID":25,"Description":"Medical tests"},{"ID":39,"QuerID":25,"Description":"Proposal Link"},{"ID":40,"QuerID":25,"Description":"Feedback"},{"ID":41,"QuerID":25,"Description":"Greivance"},{"ID":42,"QuerID":25,"Description":"Other"},{"ID":43,"QuerID":25,"Description":"Policy Issuance"},{"ID":44,"QuerID":25,"Description":"Policy Cancellation"},{"ID":45,"QuerID":25,"Description":"Policy refund"},{"ID":46,"QuerID":25,"Description":"Policy rectification"},{"ID":47,"QuerID":25,"Description":"Claim Issue"},{"ID":48,"QuerID":26,"Description":"Feedback"},{"ID":49,"QuerID":26,"Description":"Greivance"},{"ID":50,"QuerID":26,"Description":"Other"},{"ID":51,"QuerID":27,"Description":"Feedback"},{"ID":52,"QuerID":27,"Description":"Greivance"},{"ID":53,"QuerID":27,"Description":"Other"},{"ID":54,"QuerID":27,"Description":"ID Cards"},{"ID":55,"QuerID":28,"Description":"Feedback"},{"ID":56,"QuerID":28,"Description":"Greivance"},{"ID":57,"QuerID":28,"Description":"Other"},{"ID":58,"QuerID":28,"Description":"Application Status"}]}
     */

    private ZohoTicketCategoryEntity MasterData;

    public ZohoTicketCategoryEntity getMasterData() {
        return MasterData;
    }

    public void setMasterData(ZohoTicketCategoryEntity MasterData) {
        this.MasterData = MasterData;
    }


}