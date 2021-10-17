package th.ac.ku.viewraidee.model;

public class StreamingPlatform implements BlockComponents {

    private String platformName;
    private String picPath;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String getId() {
        return getPlatformName();
    }
}
