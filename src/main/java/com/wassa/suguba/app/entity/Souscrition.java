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

    private String civilite;
    private String adresseBanque;
    private String numeroCompteBanque;
    private String identitePath;
    private String identite2Path;
    private String rib;
    private String agenceDomiciliation;

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getIdentite2Path() {
        return identite2Path;
    }

    public void setIdentite2Path(String identite2Path) {
        this.identite2Path = identite2Path;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getAdresseBanque() {
        return adresseBanque;
    }

    public void setAdresseBanque(String adresseBanque) {
        this.adresseBanque = adresseBanque;
    }

    public String getNumeroCompteBanque() {
        return numeroCompteBanque;
    }

    public void setNumeroCompteBanque(String numeroCompteBanque) {
        this.numeroCompteBanque = numeroCompteBanque;
    }

    public String getIdentitePath() {
        return identitePath;
    }

    public void setIdentitePath(String identitePath) {
        this.identitePath = identitePath;
    }

    public String getAgenceDomiciliation() {
        return agenceDomiciliation;
    }

    public void setAgenceDomiciliation(String agenceDomiciliation) {
        this.agenceDomiciliation = agenceDomiciliation;
    }

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
