package com.wassa.suguba.app.payload;

public class ValidationSouscrBanque {
    public Long demandeSouscriptionId;
    public Double montant;

    @Override
    public String toString() {
        return "ValidationSouscrBanque{" +
                "demandeSouscriptionId=" + demandeSouscriptionId +
                ", montant=" + montant +
                '}';
    }

    public Long getDemandeSouscriptionId() {
        return demandeSouscriptionId;
    }

    public void setDemandeSouscriptionId(Long demandeSouscriptionId) {
        this.demandeSouscriptionId = demandeSouscriptionId;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }
}
