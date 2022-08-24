package com.wassa.suguba.app.entity;

import com.wassa.suguba.app.payload.AdditionalInfosPayload;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payementMarchand")
public class PayementMarchand extends Generality {
    public String idFromClient;
    @ManyToOne
    public AdditionalInfo additionnalInfos;
    public Double amount;
    public String callback;
    public String recipientNumber;
    public String serviceCode;
    @ManyToOne
    public Commande commande;

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getIdFromClient() {
        return idFromClient;
    }

    public void setIdFromClient(String idFromClient) {
        this.idFromClient = idFromClient;
    }

    public AdditionalInfo getAdditionnalInfos() {
        return additionnalInfos;
    }

    public void setAdditionnalInfos(AdditionalInfo additionnalInfos) {
        this.additionnalInfos = additionnalInfos;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public void setRecipientNumber(String recipientNumber) {
        this.recipientNumber = recipientNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
