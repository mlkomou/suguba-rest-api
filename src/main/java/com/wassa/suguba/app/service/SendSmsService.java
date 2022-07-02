package com.wassa.suguba.app.service;

import com.wassa.suguba.app.payload.SendSms;
import com.wassa.suguba.app.payload.SmsMessageResponse;
import com.wassa.suguba.app.payload.SmsObject;
import com.wassa.suguba.app.payload.SmsResponse;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SendSmsService {
    public SmsMessageResponse sendSms(SmsObject smsObject) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SmsObject> entity = new HttpEntity<>(smsObject);
        return restTemplate.postForObject("https://ngsystem.net/sms/rest/smsService/sendSms", entity, SmsMessageResponse.class);
    }

    public SmsResponse sendSimpleSMs(SendSms sendSms) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGg6ODA4MC9hcGkvdjEvdXNlcnMvYXBpL2tleS9nZW5lcmF0ZSIsImlhdCI6MTY0NDg0MzUyMiwibmJmIjoxNjQ0ODQzNTIyLCJqdGkiOiJ3VGdaanpjWFpsV053UWp2Iiwic3ViIjozNDA4NzAsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.DxueQ6iJqIspGv4SWxhP4r5FfC52esb0dDxh_h_9iug");
            HttpEntity<SendSms> entity = new HttpEntity<>(sendSms, headers);
           return restTemplate.postForObject("https://api.sms.to/sms/send", entity, SmsResponse.class);

        } catch (Exception e) {
            return null;
        }
    }
}
