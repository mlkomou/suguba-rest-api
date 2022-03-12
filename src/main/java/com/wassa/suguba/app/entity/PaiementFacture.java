package com.wassa.suguba.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "paiement_facture")
public class PaiementFacture extends Generality{
    private String prenom;
    private String nom;
    private String phone;
    private String mail;
    private String numCompteur;
    private String formuleCanal;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumCompteur() {
        return numCompteur;
    }

    public void setNumCompteur(String numCompteur) {
        this.numCompteur = numCompteur;
    }

    public String getFormuleCanal() {
        return formuleCanal;
    }

    public void setFormuleCanal(String formuleCanal) {
        this.formuleCanal = formuleCanal;
    }
}
