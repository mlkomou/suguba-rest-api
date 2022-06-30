package com.wassa.suguba.app.payload;

public class SouscriptionClient {
   public Long id;
   public Double montant;
   public String nomService;
   public Long partenaire;
   public  String nom;
   public String prenom;
   public String phone;
   public String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public Long getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Long partenaire) {
        this.partenaire = partenaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SouscriptionClient{" +
                "id=" + id +
                ", montant=" + montant +
                ", nomService='" + nomService + '\'' +
                ", partenaire=" + partenaire +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
