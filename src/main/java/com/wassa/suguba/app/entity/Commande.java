package com.wassa.suguba.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "commande")
    @JsonIgnoreProperties(value = {"commande"}, allowSetters = true)
    private List<LigneCommande> ligneCommandes;

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
}
