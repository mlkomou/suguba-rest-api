package com.wassa.suguba.app.entity;

import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.*;

@Entity
@Table(name = "livreur")
@Access(AccessType.FIELD)
public class Livreur extends Generality {
    String nom;
    String phone;
    String adresse;
    @ManyToOne
    Files files;
    @OneToOne
    ApplicationUser applicationUser;

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }
}
