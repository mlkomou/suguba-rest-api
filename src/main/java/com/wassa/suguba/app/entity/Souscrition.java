package com.wassa.suguba.app.entity;

import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.*;

@Entity
@Table(name = "souscrition")
@Access(AccessType.FIELD)
public class Souscrition extends Generality {
    @ManyToOne
    private ApplicationUser user;
    @ManyToOne
    private Partenaire partenaire;
    Double montant;
    private String nomService;

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
