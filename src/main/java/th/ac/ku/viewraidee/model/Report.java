package th.ac.ku.viewraidee.model;

import com.google.cloud.Timestamp;

import java.util.Date;
import java.util.UUID;

public class Report {
    private String id;
    private String reportContent;
    private String mentionedId;
    private String reportBy;
    private String type; //article, comment
    private Date reportDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getMentionedId() {
        return mentionedId;
    }

    public void setMentionedId(String mentionedId) {
        this.mentionedId = mentionedId;
    }

    public String getReportBy() {
        return reportBy;
    }

    public void setReportBy(String reportBy) {
        this.reportBy = reportBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getReportDate() {
        if(reportDate==null){
            return null;
        }
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", reportContent='" + reportContent + '\'' +
                ", mentionedId='" + mentionedId + '\'' +
                ", reportBy='" + reportBy + '\'' +
                ", type='" + type + '\'' +
                ", reportDate=" + reportDate +
                '}';
    }
}
