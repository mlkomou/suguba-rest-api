package com.wassa.suguba.app.payload;

public class CommandeAndPayementPayload {
    public CommandePayload commandePayload;
    public PaymentMarchandPayload paymentMarchandPayload;

    public CommandePayload getCommandePayload() {
        return commandePayload;
    }

    public void setCommandePayload(CommandePayload commandePayload) {
        this.commandePayload = commandePayload;
    }

    public PaymentMarchandPayload getPaymentMarchandPayload() {
        return paymentMarchandPayload;
    }

    public void setPaymentMarchandPayload(PaymentMarchandPayload paymentMarchandPayload) {
        this.paymentMarchandPayload = paymentMarchandPayload;
    }
}
