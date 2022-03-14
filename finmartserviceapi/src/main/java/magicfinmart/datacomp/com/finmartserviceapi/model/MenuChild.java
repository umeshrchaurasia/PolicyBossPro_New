package magicfinmart.datacomp.com.finmartserviceapi.model;

public class MenuChild {

    private  String mId;


    private String mChildName;
    private  int mImg;

    public MenuChild(String mId, String mChildName, int mImg) {
        this.mId = mId;
        this.mChildName = mChildName;
        this.mImg = mImg;
    }


    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmChildName() {
        return mChildName;
    }

    public void setmChildName(String mChildName) {
        this.mChildName = mChildName;
    }

    public int getmImg() {
        return mImg;
    }

    public void setmImg(int mImg) {
        this.mImg = mImg;
    }

}
