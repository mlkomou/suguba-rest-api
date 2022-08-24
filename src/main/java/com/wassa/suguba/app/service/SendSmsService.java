package com.wassa.suguba.app.service;

import com.wassa.suguba.app.payload.SmsMessageResponse;
import com.wassa.suguba.app.payload.SmsObject;
import com.wassa.suguba.app.payload.Smses;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class SendSmsService {
    public SmsMessageResponse sendSms(SmsObject smsObject) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SmsObject> entity = new HttpEntity<>(smsObject);
        return restTemplate.postForObject("https://ngsystem.net/sms/rest/smsService/sendSms", entity, SmsMessageResponse.class);
    }

    void sendSmsSingle(String phone, String message) {
        SmsObject smsObject = new SmsObject();
        List<Smses> smses = new ArrayList<>();
        Smses smses1 = new Smses();
        smses1.setText(message);
        smses1.setPhoneNumber("+223" + phone);
        smses.add(smses1);

        smsObject.setLogin("kamara");
        smsObject.setPassword("Bengaly2021!");
        smsObject.setSenderId("SUGUBA");
        smsObject.setSmses(smses);
        sendSms(smsObject);
    }

//    public SmsResponse sendSimpleSMs(SendSms sendSms) {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGg6ODA4MC9hcGkvdjEvdXNlcnMvYXBpL2tleS9nZW5lcmF0ZSIsImlhdCI6MTY0NDg0MzUyMiwibmJmIjoxNjQ0ODQzNTIyLCJqdGkiOiJ3VGdaanpjWFpsV053UWp2Iiwic3ViIjozNDA4NzAsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.DxueQ6iJqIspGv4SWxhP4r5FfC52esb0dDxh_h_9iug");
//            HttpEntity<SendSms> entity = new HttpEntity<>(sendSms, headers);
//           return restTemplate.postForObject("https://api.sms.to/sms/send", entity, SmsResponse.class);
//
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
