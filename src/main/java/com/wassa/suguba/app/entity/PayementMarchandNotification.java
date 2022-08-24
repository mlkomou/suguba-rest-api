package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "payementMarchandNotification")
@Access(AccessType.FIELD)
public class PayementMarchandNotification extends Generality{
    public String service_id;
    public String gu_transaction_id;
    public String status;
    public String partner_transaction_id;
    public String call_back_url;
    @ManyToOne
    public Commande commande;

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getGu_transaction_id() {
        return gu_transaction_id;
    }

    public void setGu_transaction_id(String gu_transaction_id) {
        this.gu_transaction_id = gu_transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartner_transaction_id() {
        return partner_transaction_id;
    }

    public void setPartner_transaction_id(String partner_transaction_id) {
        this.partner_transaction_id = partner_transaction_id;
    }

    public String getCall_back_url() {
        return call_back_url;
    }

    public void setCall_back_url(String call_back_url) {
        this.call_back_url = call_back_url;
    }
}
