package th.ac.ku.viewraidee.model;

import java.util.UUID;

public class Feedback{

    private String id;
    private String fbContent;
    private String fbBy;
    private String fbStatus;

    public String getId() {
        return id;
    }

    public void setFbId(String id) {

        this.id = id;
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

    public String generateUUIDForId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

//    @Override
//    public String toString() {
//        return "Feedback{" +
//                "fbId='" + id + '\'' +
//                ", fbContent='" + fbContent + '\'' +
//                ", fbBy='" + fbBy + '\'' +
//                ", fbStatus='" + fbStatus + '\'' +
//                '}';
//    }

}
