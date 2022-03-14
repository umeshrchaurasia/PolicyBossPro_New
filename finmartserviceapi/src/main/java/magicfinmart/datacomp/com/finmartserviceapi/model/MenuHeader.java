package magicfinmart.datacomp.com.finmartserviceapi.model;

import java.util.List;

public class MenuHeader {

    private String headerName;
    private  boolean isExpanded;

    private  int mheaderImg;
    private List<MenuChild> menuChildSection;

    public MenuHeader(String headerName, boolean isExpanded,int mheaderImg, List<MenuChild> menuChildSection) {
        this.headerName = headerName;
        this.isExpanded = isExpanded;
        this.mheaderImg = mheaderImg;
        this.menuChildSection = menuChildSection;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public List<MenuChild> getMenuChildSection() {
        return menuChildSection;
    }

    public void setMenuChildSection(List<MenuChild> menuChildSection) {
        this.menuChildSection = menuChildSection;
    }


    public int getMheaderImg() {
        return mheaderImg;
    }

    public void setMheaderImg(int mheaderImg) {
        this.mheaderImg = mheaderImg;
    }

}
