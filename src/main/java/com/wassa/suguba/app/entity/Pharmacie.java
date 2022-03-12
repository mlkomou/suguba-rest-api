package com.wassa.suguba.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pharmacie")
public class Pharmacie extends Generality {
    private String path;
    private String phone;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
