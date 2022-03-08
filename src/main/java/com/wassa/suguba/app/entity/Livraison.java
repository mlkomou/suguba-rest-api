package com.wassa.suguba.app.entity;

import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.*;

@Entity
@Table(name = "livraison")
@Access(AccessType.FIELD)
public class Livraison extends Generality {
    @ManyToOne
    Commande commande;
    @ManyToOne
    Livreur livreur;


    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

}
