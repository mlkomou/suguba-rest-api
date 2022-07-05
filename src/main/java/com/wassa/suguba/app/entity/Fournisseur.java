package com.wassa.suguba.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fournisseur")
public class Fournisseur extends Generality {
    private String name;
    private String description;
    private String codeFournisseur;
    private String contact;
    private String altitude;
    private String longitude;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCodeFournisseur() {
        return codeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
