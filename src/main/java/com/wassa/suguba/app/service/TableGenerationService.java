package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.*;
import com.wassa.suguba.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableGenerationService {

    @Autowired
    private LigneCommendeRepository ligneCommendeRepository;
    @Autowired
    private BanqueRepository banqueRepository;
    @Autowired
    private ImmobilierRepository immobilierRepository;
    @Autowired
    private PaiementFactureRepository paiementFactureRepository;
    @Autowired
    private VoyageRepository voyageRepository;

    public String generateReportMessage(List<LigneCommande> ligneCommandes) {
        StringBuilder stringBuilder = generateCommonHtmlHead();

        for (LigneCommande ligneCommande : ligneCommandes) {

            stringBuilder.append("<tbody>");
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>").append(ligneCommande.getId()).append("</td>");
            stringBuilder.append("<td>").append(ligneCommande.getProduit().getName()).append("</td>");
            stringBuilder.append("<td>").append(ligneCommande.getProduit().getPrix()).append(" FCFA</td>");
            stringBuilder.append("<td>").append(ligneCommande.getQuantite()).append("</td>");
            stringBuilder.append("<td>").append(ligneCommande.getQuantite() * ligneCommande.getProduit().getPrix()).append(" FCFA </td>");
            stringBuilder.append("</tr>");
            stringBuilder.append("</tbody>");
        }
        generateCommonFooter(stringBuilder);
        return stringBuilder.toString();
    }


    private StringBuilder generateCommonHtmlHead() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("<head>")
                .append("<h1>Commande<h1>")
                .append("</head>")
                .append("<body>")
                .append("<table border=1 class=\"table\">")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Ligne commande id</th><th>Article</th><th>Prix unitaire</th><th>Quantité</th><th>Prix total</th>")
                .append("</tr>")
                .append("</thead>");
    }

    private void generateCommonFooter(StringBuilder stringBuilder) {
        stringBuilder.append("</table></body>");
    }


    public String generateReportMessage(Long idCommande) {
        List<LigneCommande> all = ligneCommendeRepository.findAllByCommandeId(idCommande);
        return generateReportMessage(all);
    }


    ///banque
    public String generateReportMessageBanque(Banque banque) {
        StringBuilder stringBuilder = generateCommonHtmlHeadBanque();

        stringBuilder.append("<tbody>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>").append(banque.getId()).append("</td>");
        stringBuilder.append("<td>").append("PRÊT").append("</td>");
        stringBuilder.append("<td>").append(banque.getMontant()).append(" FCFA</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</tbody>");
        generateCommonFooterBanque(stringBuilder);
        return stringBuilder.toString();
    }


    private StringBuilder generateCommonHtmlHeadBanque() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("<head>")
                .append("<h1>Commande<h1>")
                .append("</head>")
                .append("<body>")
                .append("<table border=1 class=\"table\">")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Demande id</th><th>Nature</th><th>Montant</th>")
                .append("</tr>")
                .append("</thead>");
    }

    private void generateCommonFooterBanque(StringBuilder stringBuilder) {
        stringBuilder.append("</table></body>");
    }


    public String generateReportMessageBanque(Long idBanque) {
       Optional<Banque> banque = banqueRepository.findById(idBanque);
        return generateReportMessageBanque(banque.get());
    }



    ///immobilier
    public String generateReportMessageImmobilier(Immobilier immobilier) {
        StringBuilder stringBuilder = generateCommonHtmlHeadImmobilier();

        stringBuilder.append("<tbody>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>").append(immobilier.getId()).append("</td>");
        stringBuilder.append("<td>").append(immobilier.getType()).append("</td>");
        stringBuilder.append("<td>").append(immobilier.getBien()).append("</td>");
        stringBuilder.append("<td>").append(immobilier.getLocalisation()).append("</td>");
        stringBuilder.append("<td>").append(immobilier.getMontantMin()).append("</td>");
        stringBuilder.append("<td>").append(immobilier.getMontantMax()).append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</tbody>");
        generateCommonFooterImmobilier(stringBuilder);
        return stringBuilder.toString();
    }


    private StringBuilder generateCommonHtmlHeadImmobilier() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("<head>")
                .append("<h1>Immobilier<h1>")
                .append("</head>")
                .append("<body>")
                .append("<table border=1 class=\"table\">")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Demande id</th><th>Type</th><th>Bien</th><th>Localisation</th><th>Montant min</th><th>Montant max</th>")
                .append("</tr>")
                .append("</thead>");
    }

    private void generateCommonFooterImmobilier(StringBuilder stringBuilder) {
        stringBuilder.append("</table></body>");
    }

    public String generateReportMessageImmobilier(Long idImmobilier) {
        Optional<Immobilier> immobilier = immobilierRepository.findById(idImmobilier);
        return generateReportMessageImmobilier(immobilier.get());
    }


    ///paimenent facture
    public String generateReportMessagePaiement(PaiementFacture paiementFacture) {
        StringBuilder stringBuilder = generateCommonHtmlHeadPaiement();

        stringBuilder.append("<tbody>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>").append(paiementFacture.getId()).append("</td>");
        stringBuilder.append("<td>").append(paiementFacture.getTypeFacture()).append("</td>");
        stringBuilder.append("<td>").append(paiementFacture.getMontant()).append(" FCFA</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</tbody>");
        generateCommonFooterPaiement(stringBuilder);
        return stringBuilder.toString();
    }


    private StringBuilder generateCommonHtmlHeadPaiement() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("<head>")
                .append("<h1>Paiement de facture<h1>")
                .append("</head>")
                .append("<body>")
                .append("<table border=1 class=\"table\">")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Demande id</th><th>Type</th><th>Montant</th>")
                .append("</tr>")
                .append("</thead>");
    }

    private void generateCommonFooterPaiement(StringBuilder stringBuilder) {
        stringBuilder.append("</table></body>");
    }

    public String generateReportMessagePaiement(Long idPaiement) {
        Optional<PaiementFacture> immobilier = paiementFactureRepository.findById(idPaiement);
        return generateReportMessagePaiement(immobilier.get());
    }


    ///voyage
    public String generateReportMessageVoyage(Voyage voyage) {
        StringBuilder stringBuilder = generateCommonHtmlHeadVoyage();

        stringBuilder.append("<tbody>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>").append(voyage.getId()).append("</td>");
        stringBuilder.append("<td>").append(voyage.getTypeVoyage()).append("</td>");
        stringBuilder.append("<td>").append(voyage.getDepart()).append("</td>");
        stringBuilder.append("<td>").append(voyage.getDestination()).append("</td>");
        stringBuilder.append("</tr>");
        stringBuilder.append("</tbody>");
        generateCommonFooterVoyage(stringBuilder);
        return stringBuilder.toString();
    }


    private StringBuilder generateCommonHtmlHeadVoyage() {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.append("<head>")
                .append("<h1>Demande de voyage<h1>")
                .append("</head>")
                .append("<body>")
                .append("<table border=1 class=\"table\">")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Demande id</th><th>Type de voyage</th><th>Départ</th><th>Destination</th>")
                .append("</tr>")
                .append("</thead>");
    }

    private void generateCommonFooterVoyage(StringBuilder stringBuilder) {
        stringBuilder.append("</table></body>");
    }

    public String generateReportMessageVoayage(Long idVoyage) {
        Optional<Voyage> voyage = voyageRepository.findById(idVoyage);
        return generateReportMessageVoyage(voyage.get());
    }
}
