package vn.edu.hutech.quanlychitieu.model;

import java.util.Date;

public class Notification {
    public static final String STATUS_UNSEEN = "UNSEEN";
    public static final String STATUS_SEEN = "SEEN";

    private int id;
    private String content;
    private Date date;
    private String status;

    public Notification(int id, String content, Date date, String status) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.status = status;
    }

    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
