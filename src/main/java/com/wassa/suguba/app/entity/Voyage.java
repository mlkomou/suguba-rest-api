package com.wassa.suguba.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "voyage")
public class Voyage extends Generality {
   private String moyenTransport;
   private String typeVoyage;
   private String depart;
   private String dateDepart;
   private String destination;
   private String dateRetour;
   private String nom;
   private String prenom;
   private String phone;
   private String mail;
   private String path;

    public String getMoyenTransport() {
        return moyenTransport;
    }

    public void setMoyenTransport(String moyenTransport) {
        this.moyenTransport = moyenTransport;
    }

    public String getTypeVoyage() {
        return typeVoyage;
    }

    public void setTypeVoyage(String typeVoyage) {
        this.typeVoyage = typeVoyage;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
