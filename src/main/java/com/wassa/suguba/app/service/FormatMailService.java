package com.wassa.suguba.app.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FormatMailService {
    String formatEmail(String arrayList, String name, Long itemId) {
        return "<!DOCTYPE html>\n" +
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
                "\t\t\t\t\t\t\t\t\t\t<h2 class=\"our-company-name\">"+name+"</h2>\n" +
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
                "\t\t\t\t\t\t\t\t\t\t<h4>"+itemId+"</h4>\n" +
                "\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"col-md-offset-1 col-md-8 col-sm-9\">\n" +
                "\t\t\t\t\t\t\t\t\t<div class=\"invoice-bottom-right\">\n" +
                "\t\t\t\t\t\t\t\t\t\t"+arrayList+"\n" +
                "\t\t\t\t\t\t\t\t\t\t<!-- <table class=\"table\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<thead>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
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
    }
}
