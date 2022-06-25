package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Access(AccessType.FIELD)
public class Client extends Generality{
    String phone;
    String nom;
    String email;
    String namePath;
    String phonePath;
    private String entreprise;

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
