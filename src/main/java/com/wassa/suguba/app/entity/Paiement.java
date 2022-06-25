package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "paiement")
@Access(AccessType.FIELD)
public class Paiement extends Generality{
    String typePaiement;
    Double montant;

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
