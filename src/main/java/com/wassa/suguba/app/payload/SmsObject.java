package com.wassa.suguba.app.payload;

import java.util.List;

public class SmsObject {
    public String login;
    public String password;
    public String senderId;
    public List<Smses> smses;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public List<Smses> getSmses() {
        return smses;
    }

    public void setSmses(List<Smses> smses) {
        this.smses = smses;
    }
}
