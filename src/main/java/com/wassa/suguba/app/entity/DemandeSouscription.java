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
    private String statutBanque = "TRAITEMENT";
    private String nomService;
    private String civilite;
    private String adresseBanque;
    private String numeroCompteBanque;
    private String identitePath;
    private String identite2Path;
    private String signaturePath;
    private String rib;
    private String agenceDomiciliation;
    @ManyToOne
    private Partenaire partenaire;

    @Override
    public String toString() {
        return "DemandeSouscription{" +
                "montant=" + montant +
                ", user=" + user +
                ", statut='" + statut + '\'' +
                ", statutBanque='" + statutBanque + '\'' +
                ", nomService='" + nomService + '\'' +
                ", civilite='" + civilite + '\'' +
                ", adresseBanque='" + adresseBanque + '\'' +
                ", numeroCompteBanque='" + numeroCompteBanque + '\'' +
                ", identitePath='" + identitePath + '\'' +
                ", identite2Path='" + identite2Path + '\'' +
                ", signaturePath='" + signaturePath + '\'' +
                ", rib='" + rib + '\'' +
                ", agenceDomiciliation='" + agenceDomiciliation + '\'' +
                ", partenaire=" + partenaire +
                '}';
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    public String getStatutBanque() {
        return statutBanque;
    }

    public void setStatutBanque(String statutBanque) {
        this.statutBanque = statutBanque;
    }

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

    public String getAgenceDomiciliation() {
        return agenceDomiciliation;
    }

    public void setAgenceDomiciliation(String agenceDomiciliation) {
        this.agenceDomiciliation = agenceDomiciliation;
    }

    public String getNumeroCompteBanque() {
        return numeroCompteBanque;
    }

    public void setNumeroCompteBanque(String numeroCompteBanque) {
        this.numeroCompteBanque = numeroCompteBanque;
    }

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
