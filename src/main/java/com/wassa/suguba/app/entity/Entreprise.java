package com.wassa.suguba.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wassa.suguba.authentification.entity.ApplicationUser;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "entreprise")
public class Entreprise extends Generality{
    private String name;
    private String description;
    private String contact;
    private String altitude;
    private String longitude;

    @JsonIgnoreProperties(value = {"entreprise"}, allowSetters = true)
    @OneToMany(mappedBy = "entreprise")
    private List<ApplicationUser> users;

    public List<ApplicationUser> getUsers() {
        return users;
    }

    public void setUsers(List<ApplicationUser> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
