package com.wassa.suguba.app.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "partenaire")
@Access(AccessType.FIELD)
public class Partenaire extends Generality{
    private String nom;
    private String description;
    private String contact;
    private String altitude;
    private String longitude;
    private String codePartenaire;

    public String getCodePartenaire() {
        return codePartenaire;
    }

    public void setCodePartenaire(String codePartenaire) {
        this.codePartenaire = codePartenaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
}
