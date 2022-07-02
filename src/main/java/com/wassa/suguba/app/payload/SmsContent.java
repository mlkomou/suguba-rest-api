package com.wassa.suguba.app.payload;


import com.wassa.suguba.app.entity.Generality;

public class SmsContent extends Generality {
    public String message;
    public String to;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
