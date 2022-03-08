package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "produit_commande")
@Access(AccessType.FIELD)
public class ProduitCommande extends Generality{
    @ManyToOne
    Produit produit;
    @ManyToOne
    Commande commande;

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}
