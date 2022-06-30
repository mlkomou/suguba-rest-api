package com.wassa.suguba.app.service;

import com.wassa.suguba.app.payload.ResponseSms;
import com.wassa.suguba.app.payload.SmsMessageResponse;
import com.wassa.suguba.app.payload.SmsObject;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class SendSmsService {
    public SmsMessageResponse sendSms(SmsObject smsObject) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SmsObject> entity = new HttpEntity<>(smsObject);
        return restTemplate.postForObject("https://ngsystem.net/sms/rest/smsService/sendSms", entity, SmsMessageResponse.class);
    }

//    public String sendJson(String phone) {
//
//        String payload = "{\"accountid\":\"Jonathan\","
//                + "\"password\":\"mdq1372dLZ\","
//                + "\"text\":\"Hello\","
//                + "\"sender\":\"LAM\","
//                + "\"ret_id\":\"sent_1\","
//                + "\"ret_url\":\"https://votre-entreprise.com/reception\","
//                + "\"to\":\"22395073552\"}";
//        System.err.println("payload: " + payload);
//
//        HttpClient httpClient = HttpClient.newBuilder()
//                .version(Version.HTTP_1_1)
//                .connectTimeout(Duration.ofSeconds(10))
//                .build();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .POST(BodyPublishers.ofString(payload))
//                .uri(URI.create("https://lampush-json.lafricamobile.com/sms/push"))
//                .header("Content-Type", "application/json")
//                .build();
//
//        try {
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//            System.out.println("response code: " + response.statusCode());
//
//            System.out.println("response body" + response.body());
//
//            return response.body();
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            System.err.println(e);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            System.err.println(e);
//        }
//
//        return null;
//    }
}


//"{"message":"Message envoy\u00e9 avec succ\u00e8s"}"
//        + "\"to\":\"223"+phone+"\"}";
