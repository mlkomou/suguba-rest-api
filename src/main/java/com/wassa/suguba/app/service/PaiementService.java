package com.wassa.suguba.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.wassa.suguba.app.entity.*;
import com.wassa.suguba.app.payload.*;
import com.wassa.suguba.app.repository.AdditionalInfoRepository;
import com.wassa.suguba.app.repository.PaiementRepository;
import com.wassa.suguba.app.repository.PayementMarchandNotificationRepository;
import com.wassa.suguba.app.repository.PayementMarchandRepository;
import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.entity.Response;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import java.util.Map;

@Service
public class PaiementService {
    private final PayementMarchandRepository payementMarchandRepository;
    private final AdditionalInfoRepository additionalInfoRepository;
    private final PaiementRepository paiementRepository;
    private final PayementMarchandNotificationRepository payementMarchandNotificationRepository;
    private final SendSmsService sendSmsService;

    public PaiementService(PayementMarchandRepository payementMarchandRepository, AdditionalInfoRepository additionalInfoRepository, PaiementRepository paiementRepository, PayementMarchandNotificationRepository payementMarchandNotificationRepository, SendSmsService sendSmsService) {
        this.payementMarchandRepository = payementMarchandRepository;
        this.additionalInfoRepository = additionalInfoRepository;
        this.paiementRepository = paiementRepository;
        this.payementMarchandNotificationRepository = payementMarchandNotificationRepository;
        this.sendSmsService = sendSmsService;
    }

    public Map<String, Object> getBalance() throws IOException {
        URL url = new URL("https://api.gutouch.com/v1/MTCSU6019/get_balance");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Authorization", "Basic MjY5ZjU5YmVlMGNjNmY5NWI1N2ZiZjY1NDdiMjI0YTYwZmViMDY5Njc3NzU2Y2RmMTdiYTBiMWEwNGEzNDJmMTpmMTY1MzEyZGQ0MjMxOWIwZjk0OTMwY2U5MDM0YzRmNjJkMmYyMTY3OWIzOWRiMGJkZDhiYWUyYjJmNWZjN2Qx");
        httpConn.setRequestProperty("Content-Type", "application/json");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\n\t\"partner_id\":\"MTC2607\",\n\t\"login_api\":\"79347878\",\n\t\"password_api\":\"vTFGt58vJL\"\n}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);
        Gson g = new Gson();

        BalanceResponse balanceResponse = g.fromJson(response, BalanceResponse.class);

        return Response.success(balanceResponse, "Balance");
    }

    public Map<String, Object> orangeMoneyPay() {
        try {
            URL url = new URL("https://api.gutouch.com/dist/api/touchpayapi/v1/MTC2607/transaction?loginAgent=79347878&passwordAgent=vTFGt58vJL");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Authorization", "Basic MjY5ZjU5YmVlMGNjNmY5NWI1N2ZiZjY1NDdiMjI0YTYwZmViMDY5Njc3NzU2Y2RmMTdiYTBiMWEwNGEzNDJmMTpmMTY1MzEyZGQ0MjMxOWIwZjk0OTMwY2U5MDM0YzRmNjJkMmYyMTY3OWIzOWRiMGJkZDhiYWUyYjJmNWZjN2Qx");
            httpConn.setRequestProperty("Content-Type", "application/json");

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("{\n\t\"idFromClient\": \"4785411421145411645654654\",\n\t\"additionnalInfos\": {\n\t\t\"recipientEmail\": \"komou35@gmail.com\",\n\t\t\"recipientFirstName\": \"Lamine\",\n\t\t\"recipientLastName\": \"KOMOU\",\n\t\t\"destinataire\": \"70272328\"\n\t},\n\t\"amount\": 500,\n\t\"callback\": \"https://suguba.online\",\n\t\"recipientNumber\": \"70272328\",\n\t\"serviceCode\": \"ML_PAIEMENTMARCHAND_MOOV_TP\"\n}");
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);
            return Response.success(response, "Orange pay");
        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Erreur");
        }
    }


    ////// digest auth
    public CredentialsProvider provider() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials =
                new UsernamePasswordCredentials("269f59bee0cc6f95b57fbf6547b224a60feb069677756cdf17ba0b1a04a342f1", "f165312dd42319b0f94930ce9034c4f62d2f21679b39db0bdd8bae2b2f5fc7d1");
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }

    public void makePayementMarchand(PaymentMarchandPayload payementPayload, Commande commande) {
        try {
            AdditionalInfosPayload additionalInfosPayload = new AdditionalInfosPayload();
            ApplicationUser user = commande.getUser();

            additionalInfosPayload.setDestinataire(user.getUsername());
            additionalInfosPayload.setRecipientFirstName(user.getUsername());
            additionalInfosPayload.setRecipientLastName(user.getUsername());
            additionalInfosPayload.setRecipientEmail(user.getUsername());

            payementPayload.setAdditionnalInfos(additionalInfosPayload);


            ///// Digest auth
            HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider()).useSystemProperties().build();
//            HttpPut httpPost = new HttpPut("https://api.gutouch.com/dist/api/touchpayapi/v1/MTC2607/transaction?loginAgent=79347878&passwordAgent=vTFGt58vJL");
            HttpPut httpPost = new HttpPut("https://api.gutouch.com/dist/api/touchpayapi/v1/MTCSU6019/transaction?loginAgent=79347878&passwordAgent=vTFGt58vJL");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "application/json");

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(payementPayload);

            StringEntity stringEntity = new StringEntity(json);
            httpPost.getRequestLine();
            httpPost.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(httpPost);
            String responseString = new BasicResponseHandler().handleResponse(response);
            System.out.println("responseString: " + responseString);
            PaymentMarchandResponse paymentMarchandResponse = new ObjectMapper().readValue(responseString, PaymentMarchandResponse.class);
            if (Objects.equals(paymentMarchandResponse.status, "INITIATED")) {
                AdditionalInfo additionalInfo = new AdditionalInfo();
                additionalInfo.setDestinataire(payementPayload.getAdditionnalInfos().getDestinataire());
                additionalInfo.setRecipientEmail(payementPayload.getAdditionnalInfos().getRecipientEmail());
                additionalInfo.setRecipientFirstName(payementPayload.getAdditionnalInfos().getRecipientFirstName());
                additionalInfo.setRecipientLastName(payementPayload.getAdditionnalInfos().getRecipientLastName());
                AdditionalInfo additionalInfoSaved = additionalInfoRepository.save(additionalInfo);

                PayementMarchand payementMarchandToSave = new PayementMarchand();
                payementMarchandToSave.setAmount(payementPayload.getAmount());
                payementMarchandToSave.setCallback(payementPayload.getCallback());
                payementMarchandToSave.setServiceCode(payementPayload.getServiceCode());
                payementMarchandToSave.setAdditionnalInfos(additionalInfoSaved);
                payementMarchandToSave.setIdFromClient(payementPayload.getIdFromClient());
                payementMarchandToSave.setRecipientNumber(payementPayload.getRecipientNumber());
                payementMarchandToSave.setCommande(commande);

                payementMarchandRepository.save(payementMarchandToSave);
                sendSmsService.sendSmsSingle(commande.getUser().getUsername(), "Le payement de votre commande est en cours de traitement, nous vous enverrons un message de confirmation. SUGUBA vous remercie.");
            } else {
                System.err.println("Erreur API Payment: " + paymentMarchandResponse.toString());
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void payementNotification(PayementMarchandNotificationPayload payementMarchandNotificationPayload) {
        try {
            Commande commandeToUpdate = null;
            Optional<PayementMarchand> payementMarchandOptional = payementMarchandRepository.findByIdFromClient(payementMarchandNotificationPayload.getPartner_transaction_id());
            PayementMarchandNotification payementMarchandNotification = new PayementMarchandNotification();
            payementMarchandNotification.setService_id(payementMarchandNotificationPayload.getService_id());
            payementMarchandNotification.setCall_back_url(payementMarchandNotificationPayload.getCall_back_url());
            payementMarchandNotification.setGu_transaction_id(payementMarchandNotificationPayload.getGu_transaction_id());
            payementMarchandNotification.setPartner_transaction_id(payementMarchandNotificationPayload.getPartner_transaction_id());
            payementMarchandNotification.setStatus(payementMarchandNotificationPayload.getStatus());

            if (payementMarchandOptional.isPresent()) {
                PayementMarchand payementMarchand = payementMarchandOptional.get();
                Commande commande = payementMarchand.getCommande();
                commandeToUpdate = payementMarchand.getCommande();
                Paiement paiement = commande.getPaiement();
                paiement.setStatus(payementMarchandNotificationPayload.getStatus());
                paiementRepository.save(paiement);

                if (Objects.equals(payementMarchandNotificationPayload.getStatus(), "SUCCESSFUL")) {
                    sendSmsService.sendSmsSingle(commande.getUser().getUsername(), "Le payement de votre commande est effectué avec succès, nous vous contacterons pour la livraison. SUGUBA vous remercie.");
                } else {
                    sendSmsService.sendSmsSingle(commande.getUser().getUsername(), "Le payement de votre commande a échoué, vous devez vérifier si le solde votre compte est suffisant. SUGUBA vous remercie.");
                }
            }
            payementMarchandNotification.setCommande(commandeToUpdate);
            payementMarchandNotificationRepository.save(payementMarchandNotification);
        } catch (Exception ignored) {

        }
    }
}
