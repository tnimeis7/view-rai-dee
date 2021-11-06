package th.ac.ku.viewraidee.model;

import com.google.cloud.Timestamp;

public class Report {
    private String id;
    private String reportContent;
    private String mentionedId;
    private String reportBy;
    private String type; //article, comment
    private Timestamp reportDate;

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

    public Timestamp getReportDate() {
        return reportDate;
    }

    public void setReportDate(Timestamp reportDate) {
        this.reportDate = reportDate;
    }
}
