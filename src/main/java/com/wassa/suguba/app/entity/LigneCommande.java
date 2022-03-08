package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "ligne_commande")
@Access(AccessType.FIELD)
public class LigneCommande extends Generality {
    @ManyToOne
    Produit produit;
    @ManyToOne
    Commande commande;
    Integer quantite;

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
