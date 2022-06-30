package com.wassa.suguba.app.entity;

import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.*;

@Entity
@Table(name = "souscrition")
@Access(AccessType.FIELD)
public class Souscrition extends Generality {
    @OneToOne
    private ApplicationUser user;
    @ManyToOne
    private Partenaire partenaire;
    Double montant;
    private String nomService;
    private String signaturePath;

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

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

    @Override
    public String toString() {
        return "Souscrition{" +
                "user=" + user +
                ", partenaire=" + partenaire +
                ", montant=" + montant +
                ", nomService='" + nomService + '\'' +
                ", signaturePath='" + signaturePath + '\'' +
                '}';
    }
}
