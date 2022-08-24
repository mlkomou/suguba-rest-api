package com.wassa.suguba.app.payload;

import com.wassa.suguba.app.entity.PhoneNumbers;

import java.util.List;

public class SouscriptionAndPhoneNumbers {
    public SouscriptionPayload souscriptionPayload;
    public List<PhoneNumbers> phoneNumbers;

    public SouscriptionPayload getSouscriptionPayload() {
        return souscriptionPayload;
    }

    public void setSouscriptionPayload(SouscriptionPayload souscriptionPayload) {
        this.souscriptionPayload = souscriptionPayload;
    }

    public List<PhoneNumbers> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumbers> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
