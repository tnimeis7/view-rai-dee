package th.ac.ku.viewraidee.model;

public class ArticleStream implements BlockComponents{
    private String atcId;
    private String platform;

    public String getAtcId() {
        return atcId;
    }

    public void setAtcId(String atcId) {
        this.atcId = atcId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String getId() {
        return getAtcId()+"_"+getPlatform();
    }
}
