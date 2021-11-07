package th.ac.ku.viewraidee.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {

    private String id;

    private String atcName;
    private String movieName;
    private String type;
    private String teaserLink;
    private String content;
    private String coverPath;
    private String authorName;
    private int heart;
    private double starRate;
    private Date publishDate;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getAtcName() {
        return atcName;
    }

    public void setAtcName(String atcName) {
        this.atcName = atcName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeaserLink() {
        return teaserLink;
    }

    public void setTeaserLink(String teaserLink) {
        this.teaserLink = teaserLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public double getStarRate() {
        return starRate;
    }

    public void setStarRate(double starRate) {
        this.starRate = starRate;
    }

    public String getPublishDate() {
        SimpleDateFormat simpDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return simpDate.format(publishDate);
    }

//    public void setPublishDate(Timestamp timestamp) { this.publishDate = timestamp; }

//    @Override
//    public String getId() {
//        return getAtcId();
//    }
}
