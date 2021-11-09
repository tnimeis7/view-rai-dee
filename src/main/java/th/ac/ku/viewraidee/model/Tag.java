package th.ac.ku.viewraidee.model;

import java.util.List;

public class Tag implements BlockComponents {
    private String nameTag;
    private String atcId;
    private List<String[]> tagList;

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

    public List<String[]> splitTag(){
        tagList.add(nameTag.split("#"));
        return tagList;
    }

    @Override
    public String getId() {
        return getNameTag()+"_"+getAtcId();
    }
}
