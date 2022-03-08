package com.wassa.suguba.app.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client")
@Access(AccessType.FIELD)
public class Client extends Generality{
    String phone;
    String nom;
    String namePath;
    String phonePath;

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getPhonePath() {
        return phonePath;
    }

    public void setPhonePath(String phonePath) {
        this.phonePath = phonePath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
