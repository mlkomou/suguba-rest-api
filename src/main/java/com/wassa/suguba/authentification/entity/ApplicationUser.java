package com.wassa.suguba.authentification.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wassa.suguba.app.entity.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = {"user"}, allowSetters = true)
    private List<PhoneNumbers> phoneNumbers;

    public List<PhoneNumbers> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumbers> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @ManyToOne
    private Partenaire servicePaiement;

    private Boolean supprime = true;




    @CreatedBy
    @Column(name = "last_modified_by")
    protected String lastModifiedBy;

    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "DATETIME")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    protected LocalDateTime createdAt= LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "last_modified_at", columnDefinition = "DATETIME")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    protected LocalDateTime lastModifiedAt = LocalDateTime.now() ;


    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Boolean getSupprime() {
        return supprime;
    }

    public void setSupprime(Boolean supprime) {
        this.supprime = supprime;
    }

    public Partenaire getServicePaiement() {
        return servicePaiement;
    }

    public void setServicePaiement(Partenaire servicePaiement) {
        this.servicePaiement = servicePaiement;
    }

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
