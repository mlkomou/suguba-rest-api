package com.wassa.suguba.authentification.constants;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/users/record";
    public static final String SIGN_UP_SOUSCRIPTION_URL = "/users/signupSouscription";
    public static final String CHECK_UP_URL = "/users/check_user";
//    public static final String LISTE_ENTREPRISE_UP_URL = "/entreprises/page";
    public static final String LOGIN_UP_URL = "/users/login";
    public static final String CREATE_USERU_URL = "/users/create";
    public static final String SEND_SMS_URL = "/users/verifify_phone/**";
    public static final String DOWNLOAD_CATEGORIE_URL = "/categories/download/*";
    public static final String DOWNLOAD_PRODUIT_URL = "/produits/download/*";
    public static final String DOWNLOAD_PDF_URL = "/commandes/export/pdf";
    public static final String PAIEMENT_URL = "/paiements/payement_callback";
//    public static final String DOWNLOAD_PRODUIT = "/produits/**";
//    public static final String DOWNLOAD_CATEGORIE = "/categories/**";
//    public static final String DOWNLOAD_COMMANDE = "/commandes/**";
//    public static final String DOWNLOAD_PHARMACIE = "/pharmacies/**";
//    public static final String DOWNLOAD_VOYAGE = "/voyages/**";
//    public static final String DOWNLOAD_PAIEMENTFACTURE = "/paiement_factures/**";
//    public static final String DOWNLOAD_IMMOBILIER = "/immobiliers/**";
//    public static final String BANQUE_IMMOBILIER = "/banques/**";
//    public static final String AEROPORT = "/voyages/aeroport/**";


    public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh";
    public static final String HEADER_NAME = "Authorization";
    public static final Long EXPIRATION_TIME = 1000L*60*60*24*30;
}
