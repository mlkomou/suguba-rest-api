package com.wassa.suguba.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "commande")
@Access(AccessType.FIELD)
public class Commande extends Generality{
    String numero;
    String adresse;
    String adressePath;
    String statut = "Traitement";
    String oneSignalNotificationId;
    String email;
    @ManyToOne
    Client client;
    @OneToOne
    Paiement paiement;

    @ManyToOne
    private ApplicationUser user;

    @OneToMany(mappedBy = "commande")
    @JsonIgnoreProperties(value = {"commande"}, allowSetters = true)
    private List<LigneCommande> ligneCommandes;

   private Boolean receptionMe;

    public Boolean getReceptionMe() {
        return receptionMe;
    }

    public void setReceptionMe(Boolean receptionMe) {
        this.receptionMe = receptionMe;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public String getOneSignalNotificationId() {
        return oneSignalNotificationId;
    }

    public void setOneSignalNotificationId(String oneSignalNotificationId) {
        this.oneSignalNotificationId = oneSignalNotificationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "numero='" + numero + '\'' +
                ", adresse='" + adresse + '\'' +
                ", adressePath='" + adressePath + '\'' +
                ", statut='" + statut + '\'' +
                ", oneSignalNotificationId='" + oneSignalNotificationId + '\'' +
                ", email='" + email + '\'' +
                ", client=" + client +
                ", paiement=" + paiement +
                ", user=" + user +
                ", ligneCommandes=" + ligneCommandes +
                ", receptionMe=" + receptionMe +
                '}';
    }
}
