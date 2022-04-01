package com.wassa.suguba.app.payload;

public class AeroportPayload {
        public Long Id;
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
        public Long cityId;
        public AeroportCity city;

        public Long getId() {
                return Id;
        }

        public void setId(Long id) {
                Id = id;
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

        public Boolean getActive() {
                return active;
        }

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

        public Long getCityId() {
                return cityId;
        }

        public void setCityId(Long cityId) {
                this.cityId = cityId;
        }

        public AeroportCity getCity() {
                return city;
        }

        public void setCity(AeroportCity city) {
                this.city = city;
        }
}
