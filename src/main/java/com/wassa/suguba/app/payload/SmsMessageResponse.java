package com.wassa.suguba.app.payload;

import java.util.List;

public class SmsMessageResponse {
    public List<SmsData> data;
    public String message;
    public String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SmsData> getData() {
        return data;
    }

    public void setData(List<SmsData> data) {
        this.data = data;
    }
}
