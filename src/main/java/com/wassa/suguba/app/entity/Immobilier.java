package com.wassa.suguba.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "immobilier")
public class Immobilier extends Generality{
    private String type;
    private String bien;
    private String localisation;
    private Double montantMax;
    private Double montantMin;
    private String phone;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBien() {
        return bien;
    }

    public void setBien(String bien) {
        this.bien = bien;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Double getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(Double montantMax) {
        this.montantMax = montantMax;
    }

    public Double getMontantMin() {
        return montantMin;
    }

    public void setMontantMin(Double montantMin) {
        this.montantMin = montantMin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
