package th.ac.ku.viewraidee.model;

public class StreamingPlatform implements BlockComponents {

    private String id;
    private String picPath;


    public void setId(String id) {
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String getId() {
        return id;
    }
}
