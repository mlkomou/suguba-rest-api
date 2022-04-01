package com.wassa.suguba.app.payload;

public class AeroportCity {
    public Long id;
    public String nameEn;
    public String nameFr;
    public Long countryId;
    public AeroprtCountry country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public AeroprtCountry getCountry() {
        return country;
    }

    public void setCountry(AeroprtCountry country) {
        this.country = country;
    }
}
