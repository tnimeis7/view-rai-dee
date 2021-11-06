package th.ac.ku.viewraidee.model;

import com.google.cloud.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Comment{
    private String id;
    private String articleId;
    private String commentContent;
    private Date commentDate;
    private String username;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentDate() {
        if(commentDate!=null){
            SimpleDateFormat simpDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return simpDate.format(commentDate);
        }
        return null;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
