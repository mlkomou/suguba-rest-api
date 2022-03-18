package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Banque;
import com.wassa.suguba.app.entity.Immobilier;
import com.wassa.suguba.app.entity.LigneCommande;
import com.wassa.suguba.app.repository.BanqueRepository;
import com.wassa.suguba.app.repository.ImmobilierRepository;
import com.wassa.suguba.app.repository.LigneCommendeRepository;
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

    public String generateReportMessage(List<LigneCommande> ligneCommandes) {
        StringBuilder stringBuilder = generateCommonHtmlHead();

        for (LigneCommande ligneCommande : ligneCommandes) {

            stringBuilder.append("<tbody>");
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>").append(ligneCommande.getId()).append("</td>");
            stringBuilder.append("<td>").append(ligneCommande.getProduit().getName()).append("</td>");
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
                .append("<th>Ligne commande id</th><th>Libéllé</th><th>Quantité</th><th>Prix</th>")
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
}
