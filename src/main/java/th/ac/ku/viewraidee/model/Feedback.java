package th.ac.ku.viewraidee.model;

public class Feedback implements BlockComponents {

    private String fbId;
    private String fbContent;
    private String fbBy;
    private String fbStatus;

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFbContent() {
        return fbContent;
    }

    public void setFbContent(String fbContent) {
        this.fbContent = fbContent;
    }

    public String getFbBy() {
        return fbBy;
    }

    public void setFbBy(String fbBy) {
        this.fbBy = fbBy;
    }

    public String getFbStatus() {
        return fbStatus;
    }

    public void setFbStatus(String fbStatus) {
        this.fbStatus = fbStatus;
    }

    @Override
    public String getId() {
        return getFbId();
    }
}
