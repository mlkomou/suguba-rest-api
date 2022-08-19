package com.wassa.suguba.app.payload;

public class PayementPayload {
    public String service_id;
    public String recipient_phone_number;
    public Integer amount;
    public String partner_id;
    public String partner_transaction_id;
    public String login_api;
    public String password_api;
    public String call_back_url;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getRecipient_phone_number() {
        return recipient_phone_number;
    }

    public void setRecipient_phone_number(String recipient_phone_number) {
        this.recipient_phone_number = recipient_phone_number;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getPartner_transaction_id() {
        return partner_transaction_id;
    }

    public void setPartner_transaction_id(String partner_transaction_id) {
        this.partner_transaction_id = partner_transaction_id;
    }

    public String getLogin_api() {
        return login_api;
    }

    public void setLogin_api(String login_api) {
        this.login_api = login_api;
    }

    public String getPassword_api() {
        return password_api;
    }

    public void setPassword_api(String password_api) {
        this.password_api = password_api;
    }

    public String getCall_back_url() {
        return call_back_url;
    }

    public void setCall_back_url(String call_back_url) {
        this.call_back_url = call_back_url;
    }

    @Override
    public String toString() {
        return "PayementPayload{" +
                "service_id='" + service_id + '\'' +
                ", recipient_phone_number='" + recipient_phone_number + '\'' +
                ", amount=" + amount +
                ", partner_id='" + partner_id + '\'' +
                ", partner_transaction_id='" + partner_transaction_id + '\'' +
                ", login_api='" + login_api + '\'' +
                ", password_api='" + password_api + '\'' +
                ", call_back_url='" + call_back_url + '\'' +
                '}';
    }
}
