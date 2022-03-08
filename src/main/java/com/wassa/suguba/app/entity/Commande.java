package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "commande")
@Access(AccessType.FIELD)
public class Commande extends Generality{
    String numero;
    String adresse;
    String adressePath;
    String statut;
    @ManyToOne
    Client client;

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
}
