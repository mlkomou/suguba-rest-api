package com.wassa.suguba.app.entity;

import com.wassa.suguba.app.payload.AeroportCity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "aeroport")
public class Aeroport extends Generality {
    public String nameEn;
    public String nameFr;
    public String iata;
    public Boolean active;
    public Boolean acceptingComplaints;
    public String latitude;
    public String longitude;
    public Integer elevationFeet;
    public String website;
    public String wikipediaLink;

    @ManyToOne
    public City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAcceptingComplaints() {
        return acceptingComplaints;
    }

    public void setAcceptingComplaints(Boolean acceptingComplaints) {
        this.acceptingComplaints = acceptingComplaints;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getElevationFeet() {
        return elevationFeet;
    }

    public void setElevationFeet(Integer elevationFeet) {
        this.elevationFeet = elevationFeet;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

    public void setWikipediaLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }
}
