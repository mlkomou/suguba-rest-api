package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.LigneCommande;
import com.wassa.suguba.app.repository.LigneCommendeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableGenerationService {

    @Autowired
    private LigneCommendeRepository ligneCommendeRepository;

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
}
