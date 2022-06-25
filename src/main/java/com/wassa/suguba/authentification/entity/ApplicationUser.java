package com.wassa.suguba.authentification.entity;


import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.entity.Entreprise;
import com.wassa.suguba.app.entity.Partenaire;

import javax.persistence.*;

@Entity
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String type;
    private String oneSignalUserId;
    @ManyToOne
    private Entreprise entreprise;
    @ManyToOne
    private Partenaire partenaire;
    @ManyToOne
    Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public String getOneSignalUserId() {
        return oneSignalUserId;
    }

    public void setOneSignalUserId(String oneSignalUserId) {
        this.oneSignalUserId = oneSignalUserId;
    }

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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
