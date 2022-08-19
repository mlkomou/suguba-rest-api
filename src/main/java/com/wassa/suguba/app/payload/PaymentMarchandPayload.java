package com.wassa.suguba.app.payload;

public class PaymentMarchandPayload {
    public String idFromClient;
    public AdditionalInfosPayload additionnalInfos;
    public Integer amount;
    public String callback;
    public String recipientNumber;
    public String serviceCode;

    public String getIdFromClient() {
        return idFromClient;
    }

    public void setIdFromClient(String idFromClient) {
        this.idFromClient = idFromClient;
    }

    public AdditionalInfosPayload getAdditionnalInfos() {
        return additionnalInfos;
    }

    public void setAdditionnalInfos(AdditionalInfosPayload additionnalInfos) {
        this.additionnalInfos = additionnalInfos;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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

    @Override
    public String toString() {
        return "PaymentMarchand{" +
                "idFromClient='" + idFromClient + '\'' +
                ", additionnalInfos=" + additionnalInfos +
                ", amount=" + amount +
                ", callback='" + callback + '\'' +
                ", recipientNumber='" + recipientNumber + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                '}';
    }
}
