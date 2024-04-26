package com.wassa.suguba.smsSender;



import com.wassa.suguba.app.entity.Generality;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "sms")
@Entity

public class Sms extends Generality {
    String phone;
    String codeConfirmation;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCodeConfirmation() {
        return codeConfirmation;
    }

    public void setCodeConfirmation(String codeConfirmation) {
        this.codeConfirmation = codeConfirmation;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "phone='" + phone + '\'' +
                ", codeConfirmation='" + codeConfirmation + '\'' +
                '}';
    }
}
