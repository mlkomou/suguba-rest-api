package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.entity.LigneCommande;
import com.wassa.suguba.app.repository.CommandeRepository;
import com.wassa.suguba.app.repository.LigneCommendeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
//    Model model;
    private final LigneCommendeRepository ligneCommendeRepository;
    private final TableGenerationService tableGenerationService;
    private final CommandeRepository commandeRepository;

    public SendEmailService(LigneCommendeRepository ligneCommendeRepository, TableGenerationService tableGenerationService, CommandeRepository commandeRepository) {
        this.ligneCommendeRepository = ligneCommendeRepository;
        this.tableGenerationService = tableGenerationService;
        this.commandeRepository = commandeRepository;
    }

    public void sendEmailWithAttachment(String mail, String nom, String message, String objet, Long commandId) {
        try {
            Optional<Commande> commande = commandeRepository.findById(commandId);
            List<LigneCommande> ligneCommandes = ligneCommendeRepository.findAllByCommandeId(commandId);
            System.out.println("commande id: " + commandId);
            System.out.println("items: " + ligneCommandes.size());

//            model.addAttribute("paniers", ligneCommandes);

            MimeMessage msg = javaMailSender.createMimeMessage();
//            String messageTable = tableGenerationService.generateReportMessage(commandId);
            //src=\"https://electionuniverse.org:8443/suguba/commandes/logo/icon.png\"
            String messageFormated = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "\t<meta charset=\"utf-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "\n" +
                    "\t<title>Invoice1</title>\n" +
                    "\n" +
                    "\t<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
                    "\n" +
                    "\t<link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,500,600,700\" rel=\"stylesheet\">\n" +
                    "\n" +
                    "\t\t<style>\n" +
                    "\t\t.back{}\n" +
                    "\t\t.invoice-wrapper{\n" +
                    "\t\t\tmargin: 20px auto;\n" +
                    "\t\t\tmax-width: 700px;\n" +
                    "\t\t\tbox-shadow: 0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23);\n" +
                    "\t\t}\n" +
                    "\t\t.invoice-top{\n" +
                    "\t\t\tbackground-color: #fafafa;\n" +
                    "\t\t\tpadding: 40px 60px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.invoice-top-left{\n" +
                    "\t\t\tmargin-top: 60px;\n" +
                    "\t\t}\n" +
                    "\t\t.invoice-top-left h2 , .invoice-top-left h6{\n" +
                    "\t\t\tline-height: 1.5;\n" +
                    "\t\t\tfont-family: 'Montserrat', sans-serif;\n" +
                    "\t\t}\n" +
                    "\t\t.invoice-top-left h4{\n" +
                    "\t\t\tmargin-top: 30px;\n" +
                    "\t\t\tfont-family: 'Montserrat', sans-serif;\n" +
                    "\t\t}\n" +
                    "\t\t.invoice-top-left h5{\n" +
                    "\t\t\tline-height: 1.4;\n" +
                    "\t\t\tfont-family: 'Montserrat', sans-serif;\n" +
                    "\t\t\tfont-weight: 400;\n" +
                    "\t\t}\n" +
                    "\t\t.client-company-name{\n" +
                    "\t\t\tfont-size: 20px;\n" +
                    "\t\t\tfont-weight: 600;\n" +
                    "\t\t\tmargin-bottom: 0;\n" +
                    "\t\t}\n" +
                    "\t\t.client-address{\n" +
                    "\t\t\tfont-size: 14px;\n" +
                    "\t\t\tmargin-top: 5px;\n" +
                    "\t\t\tcolor: rgba(0,0,0,0.75);\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.invoice-top-right h2 , .invoice-top-right h6{\n" +
                    "\t\t\ttext-align: right;\n" +
                    "\t\t\tline-height: 1.5;\n" +
                    "\t\t\tfont-family: 'Montserrat', sans-serif;\n" +
                    "\t\t}\n" +
                    "\t\t.invoice-top-right h5{\n" +
                    "\t\t\tline-height: 1.4;\n" +
                    "\t\t    font-family: 'Montserrat', sans-serif;\n" +
                    "\t\t    font-weight: 400;\n" +
                    "\t\t    text-align: right;\n" +
                    "\t\t    margin-top: 0;\n" +
                    "\t\t}\n" +
                    "\t\t.our-company-name{\n" +
                    "\t\t\tfont-size: 16px;\n" +
                    "\t\t    font-weight: 600;\n" +
                    "\t\t    margin-bottom: 0;\n" +
                    "\t\t}\n" +
                    "\t\t.our-address{\n" +
                    "\t\t\tfont-size: 13px;\n" +
                    "\t\t\tmargin-top: 0;\n" +
                    "\t\t\tcolor: rgba(0,0,0,0.75);\n" +
                    "\t\t}\n" +
                    "\t\t.logo-wrapper{ overflow: auto; }\n" +
                    "\n" +
                    "\t\t.invoice-bottom{\n" +
                    "\t\t\tbackground-color: #ffffff;\n" +
                    "\t\t\tpadding: 40px 60px;\n" +
                    "\t\t\tposition: relative;\n" +
                    "\t\t}\n" +
                    "\t\t.title{\n" +
                    "\t\t\tfont-size: 30px;\n" +
                    "\t\t\tfont-family: 'Montserrat', sans-serif;\n" +
                    "\t\t\tfont-weight: 600;\n" +
                    "\t\t\tmargin-bottom: 30px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.invoice-bottom-left h5, .invoice-bottom-left h4{\n" +
                    "\t\t\tfont-family: 'Montserrat', sans-serif;\n" +
                    "\t\t}\n" +
                    "\t\t.invoice-bottom-left h4{\n" +
                    "\t\t\tfont-weight: 400;\n" +
                    "\t\t}\n" +
                    "\t\t.terms{\n" +
                    "\t\t\tfont-family: 'Montserrat', sans-serif;\n" +
                    "\t\t\tfont-size: 14px;\n" +
                    "\t\t\tmargin-top: 40px;\n" +
                    "\t\t}\n" +
                    "\t\t.divider{\n" +
                    "\t\t\tmargin-top: 50px;\n" +
                    "\t\t    margin-bottom: 5px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.bottom-bar{\n" +
                    "\t\t\tposition: absolute;\n" +
                    "\t\t\tbottom: 0;\n" +
                    "\t\t\tleft: 0;\n" +
                    "\t\t\tright: 0;\n" +
                    "\t\t\theight: 26px;\n" +
                    "\t\t\tbackground-color: #323149;\n" +
                    "\t\t}\n" +
                    "\t\t</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "\t<section class=\"back\">\n" +
                    "\t\t<div class=\"container\">\n" +
                    "\t\t\t<div class=\"row\">\n" +
                    "\t\t\t\t<div class=\"col-xs-12\">\n" +
                    "\t\t\t\t\t<div class=\"invoice-wrapper\">\n" +
                    "\t\t\t\t\t\t<div class=\"invoice-top\">\n" +
                    "\t\t\t\t\t\t\t<div class=\"row\">\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-sm-6\">\n" +
                    "\t\t\t\t\t\t\t\t\t<div class=\"invoice-top-left\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h2 class=\"client-company-name\">SUGUBA.</h2>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h6 class=\"client-address\">Bamako, <br>Delaware, AC 987869 <br>Mali</h6>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h4>Reference</h4>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h5>Commerce et assistance de service &amp;</h5>\n" +
                    "\t\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-sm-6\">\n" +
                    "\t\t\t\t\t\t\t\t\t<div class=\"invoice-top-right\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h2 class=\"our-company-name\">"+commande.get().getClient().getNom()+"</h2>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h6 class=\"our-address\">477 Blackwell Street, <br>Dry Creek, Alaska <br>India</h6>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<div class=\"logo-wrapper\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<img style=\"width: 100px; height: 100px;\" src=\"https://electionuniverse.org:8443/suguba/commandes/logo/icon.png\" class=\"img-responsive pull-right logo\" />\n" +
                    "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h5>06 September 2017</h5>\n" +
                    "\t\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t<div class=\"invoice-bottom\">\n" +
                    "\t\t\t\t\t\t\t<div class=\"row\">\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-xs-12\">\n" +
                    "\t\t\t\t\t\t\t\t\t<h2 class=\"title\">Facture</h2>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"clearfix\"></div>\n" +
                    "\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-sm-3 col-md-3\">\n" +
                    "\t\t\t\t\t\t\t\t\t<div class=\"invoice-bottom-left\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h5>Facture No.</h5>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h4>"+commande.get().getId()+"</h4>\n" +
                    "\t\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-md-offset-1 col-md-8 col-sm-9\">\n" +
                    "\t\t\t\t\t\t\t\t\t<div class=\"invoice-bottom-right\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t"+tableGenerationService.generateReportMessage(commandId)+"\n" +
                    "\t\t\t\t\t\t\t\t\t\t<!-- <table class=\"table\">\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<thead>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<th>Qty</th>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<th>Description</th>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<th>Price</th>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t</thead>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>1</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>Initial research</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>₹10,000</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>1</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>UX design</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>₹35,000</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>1</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>Web app development</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<td>₹50,000</td>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<tr style=\"height: 40px;\"></tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<thead>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<th>Total</th>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<th></th>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t<th>₹95,000</th>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t</thead>\n" +
                    "\t\t\t\t\t\t\t\t\t\t</table> -->\n" +
                    "\t\t\t\t\t\t\t\t\t\t<h4 class=\"terms\">Terms</h4>\n" +
                    "\t\t\t\t\t\t\t\t\t\t<ul>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<li>Commande payée à la livraison.</li>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<!-- <li>Make payment in 2,3 business days.</li> -->\n" +
                    "\t\t\t\t\t\t\t\t\t\t</ul>\n" +
                    "\t\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"clearfix\"></div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-xs-12\">\n" +
                    "\t\t\t\t\t\t\t\t\t<hr class=\"divider\">\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-sm-4\">\n" +
                    "\t\t\t\t\t\t\t\t\t<h6 class=\"text-left\">acme.com</h6>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-sm-4\">\n" +
                    "\t\t\t\t\t\t\t\t\t<h6 class=\"text-center\">contact@acme.com</h6>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t\t<div class=\"col-sm-4\">\n" +
                    "\t\t\t\t\t\t\t\t\t<h6 class=\"text-right\">+91 8097678988</h6>\n" +
                    "\t\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t\t\t<div class=\"bottom-bar\"></div>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\t</section>\n" +
                    "\n" +
                    "\n" +
                    "\t<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha256-k2WSCIexGzOj3Euiig+TlR8gA0EmPjuc79OEeY5L45g=\" crossorigin=\"anonymous\"></script>\n" +
                    "\n" +
                    "\t<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n";
//            String messageFormated = ""
//          String  messageFormated = "<!DOCTYPE html>\n" +
//                    "\n" +
//                    "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
//                    "<head>\n" +
//                    "<title></title>\n" +
//                    "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
//                    "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
//                    "<!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->\n" +
//                    "<!--[if !mso]><!-->\n" +
//                    "<link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
//                    "<!--<![endif]-->\n" +
//                    "<style>\n" +
//                    "\t\t* {\n" +
//                    "\t\t\tbox-sizing: border-box;\n" +
//                    "\t\t}\n" +
//                    "\n" +
//                    "\t\tbody {\n" +
//                    "\t\t\tmargin: 0;\n" +
//                    "\t\t\tpadding: 0;\n" +
//                    "\t\t}\n" +
//                    "\n" +
//                    "\t\ta[x-apple-data-detectors] {\n" +
//                    "\t\t\tcolor: inherit !important;\n" +
//                    "\t\t\ttext-decoration: inherit !important;\n" +
//                    "\t\t}\n" +
//                    "\n" +
//                    "\t\t#MessageViewBody a {\n" +
//                    "\t\t\tcolor: inherit;\n" +
//                    "\t\t\ttext-decoration: none;\n" +
//                    "\t\t}\n" +
//                    "\n" +
//                    "\t\tp {\n" +
//                    "\t\t\tline-height: inherit\n" +
//                    "\t\t}\n" +
//                    "\n" +
//                    "\t\t@media (max-width:670px) {\n" +
//                    "\t\t\t.icons-inner {\n" +
//                    "\t\t\t\ttext-align: center;\n" +
//                    "\t\t\t}\n" +
//                    "\n" +
//                    "\t\t\t.icons-inner td {\n" +
//                    "\t\t\t\tmargin: 0 auto;\n" +
//                    "\t\t\t}\n" +
//                    "\n" +
//                    "\t\t\t.row-content {\n" +
//                    "\t\t\t\twidth: 100% !important;\n" +
//                    "\t\t\t}\n" +
//                    "\n" +
//                    "\t\t\t.column .border {\n" +
//                    "\t\t\t\tdisplay: none;\n" +
//                    "\t\t\t}\n" +
//                    "\n" +
//                    "\t\t\t.stack .column {\n" +
//                    "\t\t\t\twidth: 100%;\n" +
//                    "\t\t\t\tdisplay: block;\n" +
//                    "\t\t\t}\n" +
//                    "\t\t}\n" +
//                    "\t</style>\n" +
//                    "</head>\n" +
//                    "<body style=\"background-color: #F5F5F5; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #F5F5F5;\" width=\"100%\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 650px;\" width=\"650\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
//                    "<div class=\"spacer_block\" style=\"height:30px;line-height:30px;font-size:1px;\"> </div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; color: #333; width: 650px;\" width=\"650\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-left: 25px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"50%\">\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td style=\"width:100%;padding-right:0px;padding-left:0px;padding-top:25px;padding-bottom:25px;\">\n" +
//                    "<div style=\"line-height:10px\"><img alt=\"Logo SUGUBA\" src=\"https://electionuniverse.org:8443/suguba/commandes/logo/icon.png\" style=\"display: block; height: auto; border: 0; width: 150px; max-width: 100%;\" title=\"Image\" width=\"195\"/></div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "<td class=\"column column-2\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-right: 25px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"50%\">\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"button_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td style=\"padding-bottom:35px;padding-left:10px;padding-top:35px;text-align:right;\">\n" +
//                    "<div align=\"right\">\n" +
//                    "<!--&lt;!&ndash;[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"#\" style=\"height:42px;width:111px;v-text-anchor:middle;\" arcsize=\"34%\" stroke=\"false\" fillcolor=\"#e3edfe\"><w:anchorlock/><v:textbox inset=\"0px,0px,0px,0px\"><center style=\"color:#052d3d; font-family:Tahoma, Verdana, sans-serif; font-size:14px\"><![endif]&ndash;&gt;<a href=\"#\" style=\"text-decoration:none;display:inline-block;color:#052d3d;background-color:#e3edfe;border-radius:14px;width:auto;border-top:1px solid #e3edfe;border-right:1px solid #e3edfe;border-bottom:1px solid #e3edfe;border-left:1px solid #e3edfe;padding-top:5px;padding-bottom:5px;font-family:Lato, Tahoma, Verdana, Segoe, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\" target=\"_blank\"><span style=\"padding-left:20px;padding-right:20px;font-size:14px;display:inline-block;letter-spacing:normal;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><span data-mce-style=\"font-size: 14px; line-height: 28px;\" style=\"font-size: 14px; line-height: 28px;\">My account</span></span></span></a>-->\n" +
//                    "<!--[if mso]></center></v:textbox></v:roundrect><![endif]-->\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #D6E7F0; color: #000000; width: 650px;\" width=\"650\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-left: 25px; padding-right: 25px; padding-top: 5px; padding-bottom: 60px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td style=\"padding-top:45px;width:100%;padding-right:0px;padding-left:0px;\">\n" +
//                    "<div align=\"center\" style=\"line-height:10px\"><img alt=\"Image\" src=\"https://electionuniverse.org:8443/suguba/commandes/logo/commande.png\" style=\"display: block; height: auto; border: 0; width: 540px; max-width: 100%;\" title=\"Image\" width=\"540\"/></div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td style=\"padding-left:15px;padding-right:10px;padding-top:20px;\">\n" +
//                    "<div style=\"font-family: sans-serif\">\n" +
//                    "<div style=\"font-size: 12px; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 18px; color: #052d3d; line-height: 1.5;\">\n" +
//                    "<p style=\"margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 75px;\"><span style=\"font-size:50px;\"><strong><span style=\"font-size:50px;\"><span style=\"font-size:38px;\">BONJOUR</span></span></strong></span></p>\n" +
//                    "<p style=\"margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 51px;\"><span style=\"font-size:34px;\"><strong><span style=\"font-size:34px;\"><span style=\"color:#662483;font-size:34px;\">"+nom+"</span></span></strong></span></p>\n" +
//                    "</div>\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<div style=\"font-family: sans-serif\">\n" +
//                    "<div style=\"font-size: 12px; mso-line-height-alt: 14.399999999999999px; color: #555555; line-height: 1.2; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif;\">\n" +
//                    "<p style=\"margin: 0; font-size: 14px; text-align: center;\"><span style=\"font-size:18px;color:#000000;\">\n" +
//                    "\t"+message+"\n" +
//                    "</span></p>\n" +
//                    "</div>\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"button_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td style=\"padding-bottom:10px;padding-left:10px;padding-right:10px;padding-top:20px;text-align:center;\">\n" +
//                    "<div align=\"center\">\n" +
//                    "<!--&lt;!&ndash;[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"#\" style=\"height:52px;width:211px;v-text-anchor:middle;\" arcsize=\"29%\" stroke=\"false\" fillcolor=\"#fc7318\"><w:anchorlock/><v:textbox inset=\"0px,0px,0px,0px\"><center style=\"color:#ffffff; font-family:Tahoma, Verdana, sans-serif; font-size:16px\"><![endif]&ndash;&gt;<a href=\"#\" style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#fc7318;border-radius:15px;width:auto;border-top:1px solid #fc7318;border-right:1px solid #fc7318;border-bottom:1px solid #fc7318;border-left:1px solid #fc7318;padding-top:10px;padding-bottom:10px;font-family:Lato, Tahoma, Verdana, Segoe, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\" target=\"_blank\"><span style=\"padding-left:40px;padding-right:40px;font-size:16px;display:inline-block;letter-spacing:normal;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><strong>START SHOPPING</strong></span></span></a>-->\n" +
//                    "<!--[if mso]></center></v:textbox></v:roundrect><![endif]-->\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 650px;\" width=\"650\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 20px; padding-bottom: 60px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
//                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"social_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social-table\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"188px\">\n" +
//                    "<tr>\n" +
//                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://www.facebook.com/\" target=\"_blank\"><img alt=\"Facebook\" height=\"32\" src=\"images/facebook2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Facebook\" width=\"32\"/></a></td>-->\n" +
//                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://twitter.com/\" target=\"_blank\"><img alt=\"Twitter\" height=\"32\" src=\"images/twitter2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Twitter\" width=\"32\"/></a></td>-->\n" +
//                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://instagram.com/\" target=\"_blank\"><img alt=\"Instagram\" height=\"32\" src=\"images/instagram2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Instagram\" width=\"32\"/></a></td>-->\n" +
//                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://www.pinterest.com/\" target=\"_blank\"><img alt=\"Pinterest\" height=\"32\" src=\"images/pinterest2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Pinterest\" width=\"32\"/></a></td>-->\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<div style=\"font-family: sans-serif\">\n" +
//                    "<div style=\"font-size: 12px; mso-line-height-alt: 18px; color: #555555; line-height: 1.5; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif;\">\n" +
//                    "<p style=\"margin: 0; font-size: 14px; text-align: center;\">SUGUBA - Lorem ipsum dolor sit amet hasellus sagittis aliquam luctus. </p>\n" +
//                    "<p style=\"margin: 0; font-size: 14px; text-align: center;\">329 California St, San Francisco, CA 94118</p>\n" +
//                    "</div>\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"divider_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<div align=\"center\">\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"60%\">\n" +
//                    "<tr>\n" +
//                    "<!--<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 1px dotted #C4C4C4;\"><span> </span></td>-->\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<div style=\"font-family: sans-serif\">\n" +
//                    "<div style=\"font-size: 12px; mso-line-height-alt: 14.399999999999999px; color: #4F4F4F; line-height: 1.2; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif;\">\n" +
//                    "<!--<p style=\"margin: 0; font-size: 12px; text-align: center;\"><span style=\"font-size:14px;\"><a href=\"#\" rel=\"noopener\" style=\"text-decoration: none; color: #2190E3;\" target=\"_blank\"><strong>Help& FAQ's</strong></a> |  <strong><a href=\"#\" rel=\"noopener\" style=\"text-decoration: none; color: #2190E3;\" target=\"_blank\">Returns</a> </strong> |  <span style=\"background-color:transparent;font-size:14px;\">1-998-9283-19832</span></span></p>-->\n" +
//                    "</div>\n" +
//                    "</div>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td>\n" +
//                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 650px;\" width=\"650\">\n" +
//                    "<tbody>\n" +
//                    "<tr>\n" +
//                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
//                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\n" +
//                    "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
//                    "<tr>\n" +
//                    "<td style=\"vertical-align: middle; text-align: center;\">\n" +
//                    "<!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\n" +
//                    "<!--[if !vml]><!-->\n" +
//                    "<table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\">\n" +
//                    "<!--<![endif]-->\n" +
//                    "<tr>\n" +
//                    "<!--<td style=\"vertical-align: middle; text-align: center; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 6px;\"><a href=\"https://www.designedwithbee.com/\" style=\"text-decoration: none;\" target=\"_blank\"><img align=\"center\" alt=\"Designed with BEE\" class=\"icon\" height=\"32\" src=\"images/bee.png\" style=\"display: block; height: auto; margin: 0 auto; border: 0;\" width=\"34\"/></a></td>-->\n" +
//                    "<!--<td style=\"font-family: Lato, Tahoma, Verdana, Segoe, sans-serif; font-size: 15px; color: #9d9d9d; vertical-align: middle; letter-spacing: undefined; text-align: center;\"><a href=\"https://www.designedwithbee.com/\" style=\"color: #9d9d9d; text-decoration: none;\" target=\"_blank\">Designed with BEE</a></td>-->\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table>\n" +
//                    "</td>\n" +
//                    "</tr>\n" +
//                    "</tbody>\n" +
//                    "</table><!-- End -->\n" +
//                    "</body>\n" +
//                    "</html>";

            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(mail);

            helper.setSubject(objet);
//            helper.setSubject("SUGUBA RECEPTION DE COMMANDE");

            // default = text/plain
            //helper.setText("Check attachment for image!");

            helper.setText(messageFormated, true);
            javaMailSender.send(msg);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
