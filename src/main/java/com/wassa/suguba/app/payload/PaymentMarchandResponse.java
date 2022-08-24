package com.wassa.suguba.app.payload;

public class PaymentMarchandResponse {
    public String idFromClient;
    public String idFromGU;
    public String amount;
    public String fees;
    public String serviceCode;
    public String recipientNumber;
    public String dateTime;
    public String status;
    public String numTransaction;
    public String payment_url;

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getIdFromClient() {
        return idFromClient;
    }

    public void setIdFromClient(String idFromClient) {
        this.idFromClient = idFromClient;
    }

    public String getIdFromGU() {
        return idFromGU;
    }

    public void setIdFromGU(String idFromGU) {
        this.idFromGU = idFromGU;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public void setRecipientNumber(String recipientNumber) {
        this.recipientNumber = recipientNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumTransaction() {
        return numTransaction;
    }

    public void setNumTransaction(String numTransaction) {
        this.numTransaction = numTransaction;
    }
}
