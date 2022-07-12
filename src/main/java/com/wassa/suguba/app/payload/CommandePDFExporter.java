package com.wassa.suguba.app.payload;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.entity.Produit;

public class CommandePDFExporter {
    private List<Commande> commandeList;

    public CommandePDFExporter(List<Commande> commandeList) {
        this.commandeList = commandeList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID COMMANDE", font));

        table.addCell(cell);

//        cell.setPhrase(new Phrase("CLIENT", font));
//        table.addCell(cell);

        cell.setPhrase(new Phrase("STATUT", font));
        table.addCell(cell);

//        cell.setPhrase(new Phrase("TOTAL", font));
//        table.addCell(cell);

        cell.setPhrase(new Phrase("DATE", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Commande commande : commandeList) {

            ArrayList<Double> montantArr = new ArrayList();
//            commande.getLigneCommandes().forEach(ligneQuantite -> {
//                Produit produit = ligneQuantite.getProduit();
//                Double prixProd = produit.getPrix();
//                Double prodMontant = ligneQuantite.getQuantite() * prixProd;
//                montantArr.add(prodMontant);
//            });

//                System.err.println("totalMonantCommandes: " + calculSum(montantArr));
//            montantTotalArr.add(calculSum(montantArr));

            table.addCell(String.valueOf(commande.getId()));
//            table.addCell(commande.getUser().getUsername());
            table.addCell(commande.getStatut());
//            table.addCell(String.valueOf(calculSum(montantArr)));
            table.addCell(String.valueOf(commande.getCreatedAt()));
//            table.addCell(String.valueOf(user.isEnabled()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Liste des commandes", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

    double calculSum(ArrayList<Double> montantArr) {
        double sum = 0;
        for(int i = 0; i < montantArr.size(); i++)
        {
            sum = sum + montantArr.get(i);
        }
        return sum;
    }
}
