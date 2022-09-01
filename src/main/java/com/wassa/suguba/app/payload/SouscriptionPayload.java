package com.wassa.suguba.app.payload;

public class SouscriptionPayload {
    Double montant;
    String statut;
    String phone;

    String nomService;
    public Long userId;
    public String civilite;
    public String prenom;
    public String nom;
    public String adresse;
    public String adresseBanque;
    private String numeroCompteBanque;
    private String agenceDomiciliation;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresseBanque() {
        return adresseBanque;
    }

    public void setAdresseBanque(String adresseBanque) {
        this.adresseBanque = adresseBanque;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }
}
