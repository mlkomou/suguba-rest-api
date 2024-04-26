package com.wassa.suguba.smsSender;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsService {

    public SmsService() {
    }
    public void sendSimpleSMs(String phone, String message, String senderName) {
        try {
            SmsContent smsContent = new SmsContent();
            List<SmsContent> smsContentList = new ArrayList<>();
            SendSms sms = new SendSms();

            smsContent.setTo("+223" + phone);
            smsContent.setMessage(message);
            smsContentList.add(smsContent);
            sms.setMessages(smsContentList);
            sms.setSender_id(senderName);
//            System.err.println("========== BEFORE SEND SMS ========");

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGg6ODA4MC9hcGkvdjEvdXNlcnMvYXBpL2tleXMvZ2VuZXJhdGUiLCJpYXQiOjE3MTQxNTE3NTAsIm5iZiI6MTcxNDE1MTc1MCwianRpIjoiV3NObEZyREM3OXBGTzcxTCIsInN1YiI6NDU0OTIxLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.OSaS5JaJ4xRQtyxupqdKJHtyyExzfnKoIIIpAf7Z5K8");
            HttpEntity<SendSms> entity = new HttpEntity<>(sms, headers);
            SmsResponse smsResponse = restTemplate.postForObject("https://api.sms.to/sms/send", entity, SmsResponse.class);
//            System.out.println("======= SMS RESPONSE =========");
            if (smsResponse != null) {
                System.out.println(smsResponse.toString());
            }
        } catch (Exception e) {
            System.out.println("======= ERROR SEND SMS =========");
            System.out.println("======= ERROR =========> " + e);
        }
    }

}
