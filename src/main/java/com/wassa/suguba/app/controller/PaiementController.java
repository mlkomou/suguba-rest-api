package com.wassa.suguba.app.controller;

import com.wassa.suguba.app.payload.CommandeAndPayementPayload;
import com.wassa.suguba.app.payload.PayementMarchandNotificationPayload;
import com.wassa.suguba.app.payload.PaymentMarchandPayload;
import com.wassa.suguba.app.service.PaiementService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/paiements")
public class PaiementController {
    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping
    public Map<String, Object> getBalance() throws IOException {
        return paiementService.getBalance();
    }

    @GetMapping("/orange")
    public Map<String, Object> orangeMoneyPay() throws IOException {
        return paiementService.orangeMoneyPay();
    }

//    @PostMapping("/makePay")
//    public Map<String, Object> makePay(@RequestBody CommandeAndPayementPayload payementPayload) {
//        return paiementService.makePayementMarchand(payementPayload);
//    }
    @PostMapping("/payement_callback")
    public void makePay(@RequestBody PayementMarchandNotificationPayload notificationPayload) {
        paiementService.payementNotification(notificationPayload);
    }
}
