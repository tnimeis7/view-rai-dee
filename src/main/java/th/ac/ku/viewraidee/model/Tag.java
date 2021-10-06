package th.ac.ku.viewraidee.model;

public class Tag implements BlockComponents {
    private String nameTag;
    private String atcId;

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public String getAtcId() {
        return atcId;
    }

    public void setAtcId(String atcId) {
        this.atcId = atcId;
    }

    @Override
    public String getId() {
        return getNameTag()+"_"+getAtcId();
    }
}
