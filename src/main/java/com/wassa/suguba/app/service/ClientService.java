package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.repository.ClientRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Map<String, Object> saveClient(Client client) {
        try {
            Client clientSaced = clientRepository.save(client);
            return Response.success(clientSaced, "Client enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la client.");
        }
    }

    public Map<String, Object> updateClientWithoutFile(Client client) {
        try {
            Optional<Client> clientOptional = clientRepository.findById(client.getId());
            if (clientOptional.isPresent()) {
                Client clientSaced = clientRepository.save(client);
                return Response.success(clientSaced, "Client modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Client n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la client.");
        }
    }

    public Map<String, Object> getClientsByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Client> clients = clientRepository.findAll(paging);
            return Response.error(clients, "Liste des clients.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getClients() {
        try {
            List<Client> clients = clientRepository.findAll();
            return Response.error(clients, "Liste des clients.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
