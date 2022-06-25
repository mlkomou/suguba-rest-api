package com.wassa.suguba.app.payload;

public class SmsObject {
    public String accountid;
    public String password;
    public String ret_id;
    public String ret_url;
    public String sender;
    public String text;
    public String to;

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRet_id() {
        return ret_id;
    }

    public void setRet_id(String ret_id) {
        this.ret_id = ret_id;
    }

    public String getRet_url() {
        return ret_url;
    }

    public void setRet_url(String ret_url) {
        this.ret_url = ret_url;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
