package com.wassa.suguba.app.payload;

public class AdminUserPayload {
    public Long id;
    public String username;
    public String password;
    public String nom;
    public String prenom;
    public String phone;
    public String email;
    public String typeUser;
    public Long partenaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public Long getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Long partenaire) {
        this.partenaire = partenaire;
    }

    //    id:this.data.data.id,
//    username: this.data.data.username,
//    password:this.data.data.password,
//    nom:this.data.data.client.nom,
//    prenom:this.data.data.client.prenom,
//    phone:this.data.data.client.phone,
//    email:this.data.data.client.email,
//    type: this.data.data.type,
//    partenaire: this.data.data.partenaire
}
