package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.entity.Notification;
import com.wassa.suguba.app.entity.Produit;
import com.wassa.suguba.app.payload.*;
import com.wassa.suguba.app.repository.CommandeRepository;
import com.wassa.suguba.app.repository.NotificationRepository;
import com.wassa.suguba.app.repository.ProduitRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {
    private final CommandeRepository commandeRepository;
    private final ProduitRepository produitRepository;
    private NotificationRepository notificationRepository;

    public NotificationService(CommandeRepository commandeRepository, ProduitRepository produitRepository, NotificationRepository notificationRepository) {
        this.commandeRepository = commandeRepository;
        this.produitRepository = produitRepository;
        this.notificationRepository = notificationRepository;
    }

    public void sendPushNotification(NotificationPayload notificationPayload, List<String> included_segments) {
        RestTemplate restTemplate = new RestTemplate();

        PushNotification pushNotification = new PushNotification();
        SingleNotification singleNotification = new SingleNotification();

        pushNotification.setApp_id("74997912-e585-45cf-b8ba-713f0a70b080");
        pushNotification.setIncluded_segments(included_segments);
        pushNotification.setHeadings(new PushDetail("SUGUBA"));

        singleNotification.setApp_id("74997912-e585-45cf-b8ba-713f0a70b080");
        singleNotification.setInclude_player_ids(included_segments);
        singleNotification.setHeadings(new PushDetail("SUGUBA"));
        Map<String, Object> data = new HashMap<>();

        switch (notificationPayload.getType()) {
            case "PRODUIT":
                Optional<Produit> produitOptional = produitRepository.findById(notificationPayload.getProduitId());
                Produit produit = produitOptional.get();
                data.put("data", produit);
                data.put("type", "PRODUIT");
                pushNotification.setData(new PushDataDetail(data));
                pushNotification.setContents(new PushDetail("Un nouveau produit est disponible: " + produit.getName()));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Basic NTA1MGU4YTUtZDQ4Mi00ZjZkLWEyODUtMGZhOTNkYWIwNTAz");
                HttpEntity<PushNotification> entity = new HttpEntity<>(pushNotification, headers);
                restTemplate.postForObject("https://onesignal.com/api/v1/notifications", entity, ResponsePush.class);
                break;
            case "COMMANDE":
                Optional<Commande> commandeOptional = commandeRepository.findById(notificationPayload.getCommandeId());
                Commande commande = commandeOptional.get();
                data.put("data", commande);
                data.put("type", notificationPayload.getType());
                singleNotification.setData(new PushDataDetail(data));
                singleNotification.setContents(new PushDetail(notificationPayload.getDescription()));
                HttpHeaders headerSingle = new HttpHeaders();
                headerSingle.add("Authorization", "Basic NTA1MGU4YTUtZDQ4Mi00ZjZkLWEyODUtMGZhOTNkYWIwNTAz");
                HttpEntity<SingleNotification> entitySingle = new HttpEntity<>(singleNotification, headerSingle);
                restTemplate.postForObject("https://onesignal.com/api/v1/notifications", entitySingle, ResponsePush.class);
                break;

            default:
                // code block
        }
    }

    public Map<String, Object> saveNotification(NotificationPayload notificationPayload, List<String> included_segments) {
        try {
            Notification notification = new Notification();
            notification.setTitre(notification.getTitre());
            notification.setDescription(notificationPayload.getDescription());
            notification.setType(notification.getType());
            notification.setOneSignalUserId(notification.getOneSignalUserId());
            sendPushNotification(notificationPayload, included_segments);
            Notification notificationSaved = notificationRepository.save(notification);
            return Response.success(notificationSaved, "Notification enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de notification.");
        }
    }
}
