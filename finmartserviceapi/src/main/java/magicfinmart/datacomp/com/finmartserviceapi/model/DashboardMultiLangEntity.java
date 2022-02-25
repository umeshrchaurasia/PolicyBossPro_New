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


    private String ProductNameFontColor;

    private String ProductDetailsFontColor;
    private String ProductBackgroundColor;

    private String IsExclusive;
    private String IsNewprdClickable;

    private String IsSharable;


    private String popupmsg;
    private String title;


    private String info;



    private Boolean isChecked;



    public DashboardMultiLangEntity(String type, int productId, String productName, String productDetails, int icon, String productNameKey, String productDetailsKey) {
        this.type = type;
        this.productId = productId;
        this.productName = productName;
        this.productDetails = productDetails;
        this.icon = icon;
        this.productNameKey = productNameKey;
        this.productDetailsKey = productDetailsKey;


        this.ProductNameFontColor = "";
        this.ProductBackgroundColor = "";
        this.ProductDetailsFontColor = "";
        this.IsExclusive = "";
        this.IsNewprdClickable = "";

        this.IsSharable = "";
        this.popupmsg = "";
        this.title = "";
        this.info = "";
        this.isChecked = false;

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


    public String getProductNameFontColor() {
        return ProductNameFontColor;
    }

    public void setProductNameFontColor(String productNameFontColor) {
        ProductNameFontColor = productNameFontColor;
    }

    public String getProductDetailsFontColor() {
        return ProductDetailsFontColor;
    }

    public void setProductDetailsFontColor(String productDetailsFontColor) {
        ProductDetailsFontColor = productDetailsFontColor;
    }

    public String getProductBackgroundColor() {
        return ProductBackgroundColor;
    }

    public void setProductBackgroundColor(String productBackgroundColor) {
        ProductBackgroundColor = productBackgroundColor;
    }

    public String getIsExclusive() {
        return IsExclusive;
    }

    public void setIsExclusive(String isExclusive) {
        IsExclusive = isExclusive;
    }

    public String getIsNewprdClickable() {
        return IsNewprdClickable;
    }

    public void setIsNewprdClickable(String isNewprdClickable) {
        IsNewprdClickable = isNewprdClickable;
    }


    public String getIsSharable() {
        return IsSharable;
    }

    public void setIsSharable(String isSharable) {
        IsSharable = isSharable;
    }

    public String getPopupmsg() {
        return popupmsg;
    }

    public void setPopupmsg(String popupmsg) {
        this.popupmsg = popupmsg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }


}
