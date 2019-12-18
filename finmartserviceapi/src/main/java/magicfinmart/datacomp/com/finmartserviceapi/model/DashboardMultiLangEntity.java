package magicfinmart.datacomp.com.finmartserviceapi.model;

import io.realm.RealmObject;

/**
 * Created by Rajeev Ranjan on 04/01/2018.
 */

public class DashboardMultiLangEntity extends RealmObject {
    private String type;
    private int productId;
    private String productName;
    private String productDetails;
    private String link;
    private String serverIcon;


    private String productNameKey;
    private String productDetailsKey;



    public DashboardMultiLangEntity(String type, int productId, String productName, String productDetails, int icon, String productNameKey, String productDetailsKey) {
        this.type = type;
        this.productId = productId;
        this.productName = productName;
        this.productDetails = productDetails;
        this.icon = icon;
        this.productNameKey = productNameKey;
        this.productDetailsKey = productDetailsKey;

    }



    public String getProductNameKey() {
        return productNameKey;
    }

    public void setProductNameKey(String productNameKey) {
        this.productNameKey = productNameKey;
    }

    public String getProductDetailsKey() {
        return productDetailsKey;
    }

    public void setProductDetailsKey(String productDetailsKey) {
        this.productDetailsKey = productDetailsKey;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getServerIcon() {
        return serverIcon;
    }

    public void setServerIcon(String serverIcon) {
        this.serverIcon = serverIcon;
    }


    public DashboardMultiLangEntity() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private int icon;
}
