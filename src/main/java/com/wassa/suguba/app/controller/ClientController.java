package com.wassa.suguba.app.controller;
import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping()
    public Map<String, Object> saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PutMapping()
    public Map<String, Object> updateClientWithoutFile(@RequestBody Client client) {
        return clientService.updateClientWithoutFile(client);
    }


    @GetMapping
    public Map<String, Object> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/page")
    public Map<String, Object> getClientsByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return clientService.getClientsByPage(page, size);
    }

}
