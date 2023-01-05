package com.example.haba_app;


public class Requests {

    private String id;
    private String request_type;
    private String request_description;
    private String request_location;
    private String request_status;
    private String posted_by;
    private String accepted_by;
    private String notification_status;

    public Requests() {
    }

    public Requests(String id, String request_type, String request_description, String request_location, String request_status,String posted_by,String accepted_by,String notification_status) {
        this.id = id;
        this.request_type = request_type;
        this.request_description = request_description;
        this.request_location = request_location;
        this.request_status = request_status;
        this.posted_by=posted_by;
        this.accepted_by=accepted_by;
        this.notification_status=notification_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getRequest_description() {
        return request_description;
    }

    public void setRequest_description(String request_description) {
        this.request_description = request_description;
    }

    public String getRequest_location() {
        return request_location;
    }

    public void setRequest_location(String request_location) {
        this.request_location = request_location;
    }

    public String getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(String notification_status) {
        this.notification_status = notification_status;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public String getAccepted_by() {
        return accepted_by;
    }

    public void setAccepted_by(String accepted_by) {
        this.accepted_by = accepted_by;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }
}