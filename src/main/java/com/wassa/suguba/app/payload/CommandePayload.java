package com.wassa.suguba.app.payload;

import java.util.List;

public class CommandePayload {
   public List<LigneQuantite> ligneQuantites;
   public String phnoneClient;
   public String nomClient;
   public String numero;
   public String adresse;
   public String adressePath;
   public String statut;
   public String oneSignalNotificationId;
    public String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOneSignalNotificationId() {
        return oneSignalNotificationId;
    }

    public void setOneSignalNotificationId(String oneSignalNotificationId) {
        this.oneSignalNotificationId = oneSignalNotificationId;
    }

    public List<LigneQuantite> getLigneQuantites() {
        return ligneQuantites;
    }

    public void setLigneQuantites(List<LigneQuantite> ligneQuantites) {
        this.ligneQuantites = ligneQuantites;
    }

    public String getPhnoneClient() {
        return phnoneClient;
    }

    public void setPhnoneClient(String phnoneClient) {
        this.phnoneClient = phnoneClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdressePath() {
        return adressePath;
    }

    public void setAdressePath(String adressePath) {
        this.adressePath = adressePath;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
