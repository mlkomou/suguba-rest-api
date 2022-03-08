package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.entity.LigneCommande;
import com.wassa.suguba.app.entity.Produit;
import com.wassa.suguba.app.payload.CommandePayload;
import com.wassa.suguba.app.repository.ClientRepository;
import com.wassa.suguba.app.repository.CommandeRepository;
import com.wassa.suguba.app.repository.LigneCommendeRepository;
import com.wassa.suguba.app.repository.ProduitRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final LigneCommendeRepository ligneCommendeRepository;
    private final ProduitRepository produitRepository;

    public CommandeService(CommandeRepository commandeRepository, ClientRepository clientRepository, LigneCommendeRepository ligneCommendeRepository, ProduitRepository produitRepository) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.ligneCommendeRepository = ligneCommendeRepository;
        this.produitRepository = produitRepository;
    }


    public Map<String, Object> saveCommande(CommandePayload commandePayload) {
      try {
            List<LigneCommande> ligneArray = new ArrayList();
            Commande commande = new Commande();
            Client client = new Client();
            client.setPhone(commandePayload.getPhnoneClient());
            client.setNom(commandePayload.getNomClient());
            Client clientSaved = clientRepository.save(client);

            commande.setClient(clientSaved);
            commande.setAdresse(commandePayload.getAdresse());
            commande.setAdressePath(commandePayload.getAdressePath());
            commande.setNumero(commandePayload.getNumero());
            commande.setStatut(commandePayload.getStatut());
            Commande commandeSaved = commandeRepository.save(commande);

            commandePayload.getLigneQuantites().forEach(ligneQuantite -> {
                Optional<Produit> produit = produitRepository.findById(ligneQuantite.idProduit);
                LigneCommande ligneCommande = new LigneCommande();
                ligneCommande.setCommande(commandeSaved);
                ligneCommande.setProduit(produit.get());
                ligneCommande.setQuantite(ligneQuantite.getQuantite());
                ligneArray.add(ligneCommande);
            });
            ligneCommendeRepository.saveAll(ligneArray);

            return Response.success(commandeSaved, "Commande enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la commande.");
        }
    }

    public Map<String, Object> updateCommandeWithoutFile(Commande commande) {
        try {
            Optional<Commande> commandeOptional = commandeRepository.findById(commande.getId());
            if (commandeOptional.isPresent()) {
                Commande commandeSaced = commandeRepository.save(commande);
                return Response.success(commandeSaced, "Commande modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Commande n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la commande.");
        }
    }

    public Map<String, Object> getCommandesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Commande> commandes = commandeRepository.findAll(paging);
            return Response.error(commandes, "Liste des commandes.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getCommandes() {
        try {
            List<Commande> commandes = commandeRepository.findAll();
            return Response.error(commandes, "Liste des commandes.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
}
