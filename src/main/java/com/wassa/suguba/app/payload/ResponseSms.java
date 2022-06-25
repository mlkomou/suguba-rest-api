package com.wassa.suguba.app.payload;

public class ResponseSms {
    public String push_id;
    public String ret_id;
    public String to;
    public String text;
    public String status;

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getRet_id() {
        return ret_id;
    }

    public void setRet_id(String ret_id) {
        this.ret_id = ret_id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
