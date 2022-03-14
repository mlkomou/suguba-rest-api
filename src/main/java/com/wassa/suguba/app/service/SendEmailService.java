package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailWithAttachment(String mail, String nom, String message, String objet) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
          String  messageFormated = "<!DOCTYPE html>\n" +
                    "\n" +
                    "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                    "<head>\n" +
                    "<title></title>\n" +
                    "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                    "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                    "<!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->\n" +
                    "<!--[if !mso]><!-->\n" +
                    "<link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                    "<!--<![endif]-->\n" +
                    "<style>\n" +
                    "\t\t* {\n" +
                    "\t\t\tbox-sizing: border-box;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tbody {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ta[x-apple-data-detectors] {\n" +
                    "\t\t\tcolor: inherit !important;\n" +
                    "\t\t\ttext-decoration: inherit !important;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t#MessageViewBody a {\n" +
                    "\t\t\tcolor: inherit;\n" +
                    "\t\t\ttext-decoration: none;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tp {\n" +
                    "\t\t\tline-height: inherit\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t@media (max-width:670px) {\n" +
                    "\t\t\t.icons-inner {\n" +
                    "\t\t\t\ttext-align: center;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.icons-inner td {\n" +
                    "\t\t\t\tmargin: 0 auto;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.row-content {\n" +
                    "\t\t\t\twidth: 100% !important;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.column .border {\n" +
                    "\t\t\t\tdisplay: none;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.stack .column {\n" +
                    "\t\t\t\twidth: 100%;\n" +
                    "\t\t\t\tdisplay: block;\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    "<body style=\"background-color: #F5F5F5; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #F5F5F5;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 650px;\" width=\"650\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<div class=\"spacer_block\" style=\"height:30px;line-height:30px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; color: #333; width: 650px;\" width=\"650\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-left: 25px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"50%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"width:100%;padding-right:0px;padding-left:0px;padding-top:25px;padding-bottom:25px;\">\n" +
                    "<div style=\"line-height:10px\"><img alt=\"Logo SUGUBA\" src=\"https://electionuniverse.org:8443/suguba/commandes/logo/icon.png\" style=\"display: block; height: auto; border: 0; width: 150px; max-width: 100%;\" title=\"Image\" width=\"195\"/></div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "<td class=\"column column-2\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-right: 25px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"50%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"button_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"padding-bottom:35px;padding-left:10px;padding-top:35px;text-align:right;\">\n" +
                    "<div align=\"right\">\n" +
                    "<!--&lt;!&ndash;[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"#\" style=\"height:42px;width:111px;v-text-anchor:middle;\" arcsize=\"34%\" stroke=\"false\" fillcolor=\"#e3edfe\"><w:anchorlock/><v:textbox inset=\"0px,0px,0px,0px\"><center style=\"color:#052d3d; font-family:Tahoma, Verdana, sans-serif; font-size:14px\"><![endif]&ndash;&gt;<a href=\"#\" style=\"text-decoration:none;display:inline-block;color:#052d3d;background-color:#e3edfe;border-radius:14px;width:auto;border-top:1px solid #e3edfe;border-right:1px solid #e3edfe;border-bottom:1px solid #e3edfe;border-left:1px solid #e3edfe;padding-top:5px;padding-bottom:5px;font-family:Lato, Tahoma, Verdana, Segoe, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\" target=\"_blank\"><span style=\"padding-left:20px;padding-right:20px;font-size:14px;display:inline-block;letter-spacing:normal;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><span data-mce-style=\"font-size: 14px; line-height: 28px;\" style=\"font-size: 14px; line-height: 28px;\">My account</span></span></span></a>-->\n" +
                    "<!--[if mso]></center></v:textbox></v:roundrect><![endif]-->\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #D6E7F0; color: #000000; width: 650px;\" width=\"650\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-left: 25px; padding-right: 25px; padding-top: 5px; padding-bottom: 60px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"padding-top:45px;width:100%;padding-right:0px;padding-left:0px;\">\n" +
                    "<div align=\"center\" style=\"line-height:10px\"><img alt=\"Image\" src=\"https://electionuniverse.org:8443/suguba/commandes/logo/commande.png\" style=\"display: block; height: auto; border: 0; width: 540px; max-width: 100%;\" title=\"Image\" width=\"540\"/></div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"padding-left:15px;padding-right:10px;padding-top:20px;\">\n" +
                    "<div style=\"font-family: sans-serif\">\n" +
                    "<div style=\"font-size: 12px; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 18px; color: #052d3d; line-height: 1.5;\">\n" +
                    "<p style=\"margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 75px;\"><span style=\"font-size:50px;\"><strong><span style=\"font-size:50px;\"><span style=\"font-size:38px;\">BONJOUR</span></span></strong></span></p>\n" +
                    "<p style=\"margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 51px;\"><span style=\"font-size:34px;\"><strong><span style=\"font-size:34px;\"><span style=\"color:#662483;font-size:34px;\">"+nom+"</span></span></strong></span></p>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"font-family: sans-serif\">\n" +
                    "<div style=\"font-size: 12px; mso-line-height-alt: 14.399999999999999px; color: #555555; line-height: 1.2; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif;\">\n" +
                    "<p style=\"margin: 0; font-size: 14px; text-align: center;\"><span style=\"font-size:18px;color:#000000;\">\n" +
                    "\t"+message+"\n" +
                    "</span></p>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"button_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"padding-bottom:10px;padding-left:10px;padding-right:10px;padding-top:20px;text-align:center;\">\n" +
                    "<div align=\"center\">\n" +
                    "<!--&lt;!&ndash;[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"#\" style=\"height:52px;width:211px;v-text-anchor:middle;\" arcsize=\"29%\" stroke=\"false\" fillcolor=\"#fc7318\"><w:anchorlock/><v:textbox inset=\"0px,0px,0px,0px\"><center style=\"color:#ffffff; font-family:Tahoma, Verdana, sans-serif; font-size:16px\"><![endif]&ndash;&gt;<a href=\"#\" style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#fc7318;border-radius:15px;width:auto;border-top:1px solid #fc7318;border-right:1px solid #fc7318;border-bottom:1px solid #fc7318;border-left:1px solid #fc7318;padding-top:10px;padding-bottom:10px;font-family:Lato, Tahoma, Verdana, Segoe, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\" target=\"_blank\"><span style=\"padding-left:40px;padding-right:40px;font-size:16px;display:inline-block;letter-spacing:normal;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\"><strong>START SHOPPING</strong></span></span></a>-->\n" +
                    "<!--[if mso]></center></v:textbox></v:roundrect><![endif]-->\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 650px;\" width=\"650\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 20px; padding-bottom: 60px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"social_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social-table\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"188px\">\n" +
                    "<tr>\n" +
                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://www.facebook.com/\" target=\"_blank\"><img alt=\"Facebook\" height=\"32\" src=\"images/facebook2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Facebook\" width=\"32\"/></a></td>-->\n" +
                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://twitter.com/\" target=\"_blank\"><img alt=\"Twitter\" height=\"32\" src=\"images/twitter2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Twitter\" width=\"32\"/></a></td>-->\n" +
                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://instagram.com/\" target=\"_blank\"><img alt=\"Instagram\" height=\"32\" src=\"images/instagram2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Instagram\" width=\"32\"/></a></td>-->\n" +
                    "<!--<td style=\"padding:0 15px 0 0px;\"><a href=\"https://www.pinterest.com/\" target=\"_blank\"><img alt=\"Pinterest\" height=\"32\" src=\"images/pinterest2x.png\" style=\"display: block; height: auto; border: 0;\" title=\"Pinterest\" width=\"32\"/></a></td>-->\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"font-family: sans-serif\">\n" +
                    "<div style=\"font-size: 12px; mso-line-height-alt: 18px; color: #555555; line-height: 1.5; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif;\">\n" +
                    "<p style=\"margin: 0; font-size: 14px; text-align: center;\">SUGUBA - Lorem ipsum dolor sit amet hasellus sagittis aliquam luctus. </p>\n" +
                    "<p style=\"margin: 0; font-size: 14px; text-align: center;\">329 California St, San Francisco, CA 94118</p>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"divider_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div align=\"center\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"60%\">\n" +
                    "<tr>\n" +
                    "<!--<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 1px dotted #C4C4C4;\"><span> </span></td>-->\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<div style=\"font-family: sans-serif\">\n" +
                    "<div style=\"font-size: 12px; mso-line-height-alt: 14.399999999999999px; color: #4F4F4F; line-height: 1.2; font-family: Lato, Tahoma, Verdana, Segoe, sans-serif;\">\n" +
                    "<!--<p style=\"margin: 0; font-size: 12px; text-align: center;\"><span style=\"font-size:14px;\"><a href=\"#\" rel=\"noopener\" style=\"text-decoration: none; color: #2190E3;\" target=\"_blank\"><strong>Help& FAQ's</strong></a> |  <strong><a href=\"#\" rel=\"noopener\" style=\"text-decoration: none; color: #2190E3;\" target=\"_blank\">Returns</a> </strong> |  <span style=\"background-color:transparent;font-size:14px;\">1-998-9283-19832</span></span></p>-->\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 650px;\" width=\"650\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\n" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: middle; text-align: center;\">\n" +
                    "<!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\n" +
                    "<!--[if !vml]><!-->\n" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\">\n" +
                    "<!--<![endif]-->\n" +
                    "<tr>\n" +
                    "<!--<td style=\"vertical-align: middle; text-align: center; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 6px;\"><a href=\"https://www.designedwithbee.com/\" style=\"text-decoration: none;\" target=\"_blank\"><img align=\"center\" alt=\"Designed with BEE\" class=\"icon\" height=\"32\" src=\"images/bee.png\" style=\"display: block; height: auto; margin: 0 auto; border: 0;\" width=\"34\"/></a></td>-->\n" +
                    "<!--<td style=\"font-family: Lato, Tahoma, Verdana, Segoe, sans-serif; font-size: 15px; color: #9d9d9d; vertical-align: middle; letter-spacing: undefined; text-align: center;\"><a href=\"https://www.designedwithbee.com/\" style=\"color: #9d9d9d; text-decoration: none;\" target=\"_blank\">Designed with BEE</a></td>-->\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table><!-- End -->\n" +
                    "</body>\n" +
                    "</html>";

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
