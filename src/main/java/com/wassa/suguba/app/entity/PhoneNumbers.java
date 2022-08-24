package com.wassa.suguba.app.entity;

import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.*;

@Entity
@Table(name = "phoneNumbers")
@Access(AccessType.FIELD)
public class PhoneNumbers extends Generality {
    private String phone;
    private String typeNumber;
    @ManyToOne
    private ApplicationUser user;

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(String typeNumber) {
        this.typeNumber = typeNumber;
    }
}
