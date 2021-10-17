package th.ac.ku.viewraidee.model;

public class Genre implements BlockComponents {
    private String genreName;
    private String atcId;

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getAtcId() {
        return atcId;
    }

    public void setAtcId(String atcId) {
        this.atcId = atcId;
    }

    @Override
    public String getId() {
        return getGenreName()+"_"+getAtcId();
    }
}
