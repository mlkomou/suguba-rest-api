package com.wassa.suguba.app.entity;

import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "demandeSouscription")
public class DemandeSouscription extends Generality{
    private Double montant;
    @ManyToOne
    private ApplicationUser user;
    private String statut;
    private String nomService;
    private String civilite;
    private String adresseBanque;
    private String identitePath;
    private String signaturePath;

    public String getAdresseBanque() {
        return adresseBanque;
    }

    public void setAdresseBanque(String adresseBanque) {
        this.adresseBanque = adresseBanque;
    }

    public String getIdentitePath() {
        return identitePath;
    }

    public void setIdentitePath(String identitePath) {
        this.identitePath = identitePath;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
