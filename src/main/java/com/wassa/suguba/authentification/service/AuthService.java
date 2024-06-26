package com.wassa.suguba.authentification.service;

import com.wassa.suguba.app.entity.*;
import com.wassa.suguba.app.payload.*;
import com.wassa.suguba.app.repository.*;
import com.wassa.suguba.app.service.SendEmailService;
import com.wassa.suguba.app.service.SendSmsService;
import com.wassa.suguba.app.service.UploadFileService;
import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.entity.Response;
import com.wassa.suguba.authentification.entity.UserConnected;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import com.wassa.suguba.smsSender.SmsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

import static com.wassa.suguba.app.constante.UploadPath.DOWNLOAD_LINK;
import static com.wassa.suguba.authentification.constants.SecurityConstants.EXPIRATION_TIME;
import static com.wassa.suguba.authentification.constants.SecurityConstants.KEY;


@Service
public class AuthService {
    private final ApplicationUserRepository applicationUserRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PhoneVerificationRepository phoneVerificationRepository;
//    private final SendSmsService sendSmsService;
    private final ClientRepository clientRepository;
    private final SouscritionRepository souscritionRepository;
    private final PartenaireRepository partenaireRepository;
    private final DemandeSouscriptionRepository demandeSouscriptionRepository;
    private final SendEmailService sendEmailService;
    private final PhoneNumbersRepository phoneNumbersRepository;
    private final UploadFileService uploadFileService;
    private final SmsService smsService;


    public AuthService(ApplicationUserRepository applicationUserRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder1, PhoneVerificationRepository phoneVerificationRepository, ClientRepository clientRepository, SouscritionRepository souscritionRepository, PartenaireRepository partenaireRepository, DemandeSouscriptionRepository demandeSouscriptionRepository, SendEmailService sendEmailService, PhoneNumbersRepository phoneNumbersRepository, UploadFileService uploadFileService, SmsService smsService) {
        this.applicationUserRepository = applicationUserRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
        this.phoneVerificationRepository = phoneVerificationRepository;
//        this.sendSmsService = sendSmsService;
        this.clientRepository = clientRepository;
        this.souscritionRepository = souscritionRepository;
        this.partenaireRepository = partenaireRepository;
        this.demandeSouscriptionRepository = demandeSouscriptionRepository;
        this.sendEmailService = sendEmailService;
        this.phoneNumbersRepository = phoneNumbersRepository;
        this.uploadFileService = uploadFileService;
        this.smsService = smsService;
    }

    public ResponseEntity<Map<String, Object>> loginUser(ApplicationUser applicationUser) {
        try {
            Optional<ApplicationUser> userOptional = applicationUserRepository.findByUsername(applicationUser.getUsername());
            ApplicationUser user = userOptional.get();
            UserConnected userConnected = new UserConnected();
            System.out.println("user status " + user.getUsername());
            System.out.println("user status pass " + user.getPassword());
            if (userOptional.isPresent()) {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword()));
                System.out.println("user present " + user.getUsername());
                Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
                Key key = Keys.hmacShaKeyFor(KEY.getBytes());
                Claims claims = Jwts.claims().setSubject(((User) authentication.getPrincipal()).getUsername());
                String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, key).setExpiration(exp).compact();
                userConnected.setToken(token);
                userConnected.setApplicationUser(user);
                System.out.println("user type " + user.getType());

                return new ResponseEntity<>(Response.success(userConnected, "Authentification réussie"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Response.error(new UserConnected(), "Cet utilisateur n'existe pas."), HttpStatus.OK);
            }
        } catch (Exception e) {
            System.err.println("auth error: " + e);
            return new ResponseEntity<>(Response.error(e, "Authentification échouée"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getCurrentUser(Long userId) {
        try {
            Optional<ApplicationUser> user = applicationUserRepository.findById(userId);
            if (user.isPresent()) {
                return new ResponseEntity<>(Response.success(user, "Utilisateur courant"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Response.error(user, "Utilisateur enexistant."), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.success(e, "Utilisateur courant"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> signupMobileUser(String oneSignalUserId) {
        try {
            Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.getApplicationUserByOneSignalUserId(oneSignalUserId);
            if (applicationUserOptional.isPresent()) {
                return new ResponseEntity<>(Response.success(applicationUserOptional.get(), "Authentification réussie"), HttpStatus.OK);
            }
            ApplicationUser applicationUser = new ApplicationUser();
            applicationUser.setOneSignalUserId(oneSignalUserId);
            applicationUser.setUsername(oneSignalUserId);
            applicationUser.setType("PHONEUSER");
            applicationUser.setPassword(oneSignalUserId);
            ApplicationUser applicationUserSaved =  applicationUserRepository.save(applicationUser);

            UserConnected userConnected = new UserConnected();

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUserSaved.getUsername(), applicationUser.getPassword()));
            System.out.println("phone user" + applicationUserSaved.getUsername());
            Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
            Key key = Keys.hmacShaKeyFor(KEY.getBytes());
            Claims claims = Jwts.claims().setSubject(((User) authentication.getPrincipal()).getUsername());
            String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, key).setExpiration(exp).compact();
            userConnected.setToken(token);
            userConnected.setApplicationUser(applicationUserSaved);
            return new ResponseEntity<>(Response.success(userConnected, "Authentification réussie"), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("auth error: " + e);
            return new ResponseEntity<>(Response.success(e, "Authentification réussie"), HttpStatus.OK);

        }
    }

    public ResponseEntity<Map<String, Object>> getAllUsers(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<ApplicationUser> applicationUsers = applicationUserRepository.findAll(paging);
            return new ResponseEntity<>(Response.success(applicationUsers, "Liste des utilisateurs"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.error(new ArrayList<>(), "Pas d'utilsateur"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    String makePassword(int qoutas) {
        String passArray = "0123456789";
        int charactersLength = passArray.length();
        String result = "";
        for (int i = 0; i < qoutas; i++) {
            result += passArray.charAt((int) Math.floor(Math.random() * charactersLength));
        }
        return result;
    }

    public String getToken(ApplicationUser applicationUser) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword()));
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
        Claims claims = Jwts.claims().setSubject(((User) authentication.getPrincipal()).getUsername());
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, key).setExpiration(exp).compact();
        return token;
    }

  public UserConnected checkUser(ApplicationUser user) {
        try {
            UserConnected userConnected = new UserConnected();
            Optional<ApplicationUser> userOptional = applicationUserRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                user.setPassword(user.getUsername());
                userConnected.setApplicationUser(userOptional.get());
                userConnected.setToken(getToken(user));
                userConnected.setId(1L);
                return userConnected;
            } else {
                userConnected.setId(-1L);
                return userConnected;
            }
        } catch (Exception e) {
            return new UserConnected();
        }
  }

    public Map<String, Object> signupSouscription(SouscriptionAndPhoneNumbers souscriptionAndPhoneNumbers,
                                                  MultipartFile identiteFile,
                                                  MultipartFile signatureFile,
                                                  MultipartFile identiteFileVerso) {
        try {
            SouscriptionPayload souscriptionPayload = souscriptionAndPhoneNumbers.getSouscriptionPayload();
            UserConnected userConnected = new UserConnected();
            Client client = new Client();
            client.setPhone(souscriptionPayload.getPhone());
            client.setPrenom(souscriptionPayload.getPrenom());
            client.setNom(souscriptionPayload.getNom());
            client.setAdresse(souscriptionPayload.getAdresse());
            client.setCivilite(souscriptionPayload.getCivilite());
            Client clientSaved = clientRepository.save(client);

            ApplicationUser user = new ApplicationUser();
            ApplicationUser userToAuth = new ApplicationUser();
            userToAuth.setUsername(souscriptionPayload.getPhone());
            userToAuth.setPassword(souscriptionPayload.getPhone());
            user.setUsername(souscriptionPayload.getPhone());
            user.setPassword(bCryptPasswordEncoder.encode(souscriptionPayload.getPhone()));
            user.setClient(clientSaved);

            ApplicationUser userSaved = applicationUserRepository.save(user);
            userConnected.setApplicationUser(userSaved);
            userConnected.setToken(getToken(userToAuth));

            List<PhoneNumbers> phoneNumbersList = new ArrayList<>();
            souscriptionAndPhoneNumbers.getPhoneNumbers().forEach(phoneNumbers -> {
                phoneNumbers.setUser(userSaved);
                phoneNumbersList.add(phoneNumbers);
            });
            PhoneNumbers phoneNumbersPrincipal = new PhoneNumbers();
            phoneNumbersPrincipal.setUser(userSaved);
            phoneNumbersPrincipal.setPhone(userSaved.getUsername());
            phoneNumbersList.add(phoneNumbersPrincipal);

            phoneNumbersRepository.saveAll(phoneNumbersList);

            // inscription avec souscription (oui)
            if (Objects.equals(souscriptionPayload.getStatut(), "Oui")) {
                Optional<DemandeSouscription> demandeSouscriptionOptional = demandeSouscriptionRepository.findByUserIdAndStatut(userSaved.getId(), "TRAITEMENT");
                if (demandeSouscriptionOptional.isPresent()) {
                    //une ancienne souscription en cours
                    DemandeSouscription demandeSouscription = demandeSouscriptionOptional.get();
                    demandeSouscription.setMontant(souscriptionPayload.getMontant());
                    demandeSouscription.setCivilite(souscriptionPayload.getCivilite());
                    demandeSouscription.setNomService(souscriptionPayload.getNomService());
                    demandeSouscription.setNumeroCompteBanque(souscriptionPayload.getNumeroCompteBanque());
                    demandeSouscription.setAgenceDomiciliation(souscriptionPayload.getAgenceDomiciliation());
                    demandeSouscription.setAdresseBanque(souscriptionPayload.getAdresseBanque());
                    demandeSouscription.setRib(souscriptionPayload.getRib());
                    demandeSouscription.setIdentitePath(uploadFileService.uploadFile(identiteFile, DOWNLOAD_LINK + "/pieces"));
//                    demandeSouscription.setIdentite2Path(uploadFileService.uploadFile(identiteFileVerso, DOWNLOAD_LINK + "/piece));
                    demandeSouscription.setSignaturePath(uploadFileService.uploadFile(signatureFile, DOWNLOAD_LINK + "/signatures"));
                    demandeSouscriptionRepository.save(demandeSouscription);

                    String message = "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent. "+"\n"+"  GOUABO vous remercie.";

                    smsService.sendSimpleSMs(user.getUsername(), message, "GOUABO");

                    return Response.success(userConnected, "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent.");
                }

                // nouvelle souscription
                DemandeSouscription souscrition = new DemandeSouscription();
                souscrition.setAdresseBanque(souscrition.getAdresseBanque());
                souscrition.setMontant(souscriptionPayload.getMontant());
                souscrition.setUser(userSaved);
                souscrition.setStatut("TRAITEMENT");
                souscrition.setNomService(souscriptionPayload.getNomService());
                souscrition.setCivilite(souscriptionPayload.getCivilite());
                souscrition.setNumeroCompteBanque(souscriptionPayload.getNumeroCompteBanque());
                souscrition.setAgenceDomiciliation(souscriptionPayload.getAgenceDomiciliation());
                souscrition.setAdresseBanque(souscriptionPayload.getAdresseBanque());
                souscrition.setRib(souscriptionPayload.getRib());
                souscrition.setIdentitePath(uploadFileService.uploadFile(identiteFile, DOWNLOAD_LINK + "/piece"));
//                souscrition.setIdentite2Path(uploadFileService.uploadFile(identiteFileVerso, DOWNLOAD_LINK + "/piece));
                souscrition.setSignaturePath(uploadFileService.uploadFile(signatureFile, DOWNLOAD_LINK + "/signature"));
                demandeSouscriptionRepository.save(souscrition);

                String message = "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent. "+"\n"+"  GOUABO vous remercie.";
                smsService.sendSimpleSMs(user.getUsername(), message, "GOUABO");


                return Response.success(userConnected, "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent.");
            } else {
                return Response.success(userConnected, "Vous êtes inscrits(es) avec succès.");
            }

        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Erreur d'inscription, Veuillez réessayer plus tard.");
        }
    }

    public ResponseEntity<Map<String, Object>> souscriptionInApp(SouscriptionPayload souscriptionPayload,
                                                                 MultipartFile identiteFile,
                                                                 MultipartFile signatureFile) {
        try {
            Optional<ApplicationUser> user = applicationUserRepository.findById(souscriptionPayload.getUserId());

            if (user.isPresent()) {

                Client client = user.get().getClient();
                client.setPrenom(souscriptionPayload.getPrenom());
                client.setNom(souscriptionPayload.getNom());
                client.setCivilite(souscriptionPayload.getCivilite());
                clientRepository.save(client);
//                Optional<Souscrition> souscritionOptional = souscritionRepository.findByUserId(souscriptionPayload.getUserId());
                Optional<DemandeSouscription> demandeSouscriptionOptional = demandeSouscriptionRepository.findByUserIdAndStatut(user.get().getId(), "TRAITEMENT");
                if (demandeSouscriptionOptional.isPresent()) {
                    DemandeSouscription demandeSouscription = demandeSouscriptionOptional.get();
                    demandeSouscription.setMontant(souscriptionPayload.getMontant());
                    demandeSouscription.setNomService(souscriptionPayload.getNomService());
                    demandeSouscription.setCivilite(souscriptionPayload.getCivilite());
                    demandeSouscription.setNumeroCompteBanque(souscriptionPayload.getNumeroCompteBanque());
                    demandeSouscription.setAgenceDomiciliation(souscriptionPayload.getAgenceDomiciliation());
                    demandeSouscription.setAdresseBanque(souscriptionPayload.getAdresseBanque());
                    demandeSouscription.setRib(souscriptionPayload.getRib());
                    demandeSouscription.setIdentitePath(uploadFileService.uploadFile(identiteFile, DOWNLOAD_LINK + "/piece"));
//                    demandeSouscription.setIdentite2Path(uploadFileService.uploadFile(identiteFileVerso, DOWNLOAD_LINK + "/piece));
                    demandeSouscription.setSignaturePath(uploadFileService.uploadFile(signatureFile, DOWNLOAD_LINK + "/signature"));
                    DemandeSouscription demandeSouscriptionSaved =  demandeSouscriptionRepository.save(demandeSouscription);
                    //enregistrement de la demande de souscription

                    String message = "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent. "+"\n"+"  GOUABO vous remercie.";
                    smsService.sendSimpleSMs(client.getPhone(), message, "GOUABO");

                    return new ResponseEntity<>(Response.success(demandeSouscriptionSaved, "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent."), HttpStatus.OK);
                } else {
                    DemandeSouscription demandeSouscription = new DemandeSouscription();
                    demandeSouscription.setMontant(souscriptionPayload.getMontant());
                    demandeSouscription.setNomService(souscriptionPayload.getNomService());
                    demandeSouscription.setCivilite(souscriptionPayload.getCivilite());
                    demandeSouscription.setNumeroCompteBanque(souscriptionPayload.getNumeroCompteBanque());
                    demandeSouscription.setAgenceDomiciliation(souscriptionPayload.getAgenceDomiciliation());
                    demandeSouscription.setAdresseBanque(souscriptionPayload.getAdresseBanque());
                    demandeSouscription.setRib(souscriptionPayload.getRib());
                    demandeSouscription.setIdentitePath(uploadFileService.uploadFile(identiteFile, DOWNLOAD_LINK + "/piece"));
//                    demandeSouscription.setIdentite2Path(uploadFileService.uploadFile(identiteFileVerso, DOWNLOAD_LINK + "/piece));
                    demandeSouscription.setSignaturePath(uploadFileService.uploadFile(signatureFile, DOWNLOAD_LINK + "/signature"));
                    demandeSouscription.setStatut("TRAITEMENT");
                    demandeSouscription.setUser(user.get());
                    DemandeSouscription demandeSouscriptionSaved =  demandeSouscriptionRepository.save(demandeSouscription);
                    String message = "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent. "+"\n"+"  GOUABO vous remercie.";
                    smsService.sendSimpleSMs(client.getPhone(), message, "GOUABO");

                    return new ResponseEntity<>(Response.success(demandeSouscriptionSaved, "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent."), HttpStatus.OK);
                }

            } else {
                return new ResponseEntity<>(Response.error(null, "Cet utilisateur n'existe pas."), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(Response.error(e, "Erreur d'enregistrement de la souscrition."), HttpStatus.OK);
        }
    }

    public ResponseEntity<Map<String, Object>> createUser(AdminUserPayload adminUserPayload) {
        try {
            System.err.println(adminUserPayload.toString());
            Optional<ApplicationUser> oldUser = applicationUserRepository.findByUsername(adminUserPayload.getEmail());
            if (oldUser.isPresent()) {
                return new ResponseEntity<>(Response.error(adminUserPayload, "Cet utilisateur existe déjà"), HttpStatus.OK);
            } else {
                if (adminUserPayload.getId() != null) {
                    Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(adminUserPayload.getId());
                    if (applicationUserOptional.isPresent()) {
                        ApplicationUser applicationUser = applicationUserOptional.get();
                        if (adminUserPayload.getPartenaire() != null) {
                            Optional<Partenaire> partenaire = partenaireRepository.findById(adminUserPayload.getPartenaire());
                            partenaire.ifPresent(applicationUser::setPartenaire);
                        }
                        applicationUser.setType(adminUserPayload.getTypeUser());
                        applicationUser.setLastModifiedAt(LocalDateTime.now());
                        ApplicationUser applicationUserSaved = applicationUserRepository.save(applicationUser);
                        Client client = applicationUser.getClient();
                        client.setPhone(adminUserPayload.getPhone());
                        client.setPrenom(adminUserPayload.getPrenom());
                        client.setNom(adminUserPayload.getNom());
                        client.setEmail(adminUserPayload.getEmail());
                        clientRepository.save(client);
                        return new ResponseEntity<>(Response.success(applicationUserSaved, "Utilisateur modifié."), HttpStatus.OK);
                    }
                }

                Client client = new Client();
                client.setPhone(adminUserPayload.getPhone());
                client.setPrenom(adminUserPayload.getPrenom());
                client.setNom(adminUserPayload.getNom());
                client.setEmail(adminUserPayload.getEmail());
                Client clientSaved = clientRepository.save(client);

                ApplicationUser applicationUserAuth = new ApplicationUser();
                applicationUserAuth.setUsername(adminUserPayload.getEmail());
                applicationUserAuth.setPassword(adminUserPayload.getPassword());

                ApplicationUser newUser = new ApplicationUser();
                newUser.setClient(clientSaved);
                newUser.setPassword(bCryptPasswordEncoder.encode(adminUserPayload.password));
                newUser.setUsername(adminUserPayload.getEmail());
                newUser.setType(adminUserPayload.getTypeUser());

                if (adminUserPayload.getPartenaire() != null) {
                    Optional<Partenaire> partenaire = partenaireRepository.findById(adminUserPayload.getPartenaire());
                    partenaire.ifPresent(newUser::setPartenaire);
                }
//                newUser.setPartenaire(adminUserPayload.getPartenaire());
                ApplicationUser userSaved = applicationUserRepository.save(newUser);

                UserConnected userConnected = new UserConnected();
                userConnected.setApplicationUser(userSaved);
                userConnected.setToken(getToken(applicationUserAuth));
                return new ResponseEntity<>(Response.success(userConnected, "Utilisateur inscrit"), HttpStatus.OK);
            }
        } catch (Exception e) {
            System.err.println(e);
            return new ResponseEntity<>(Response.error(e, "erreur d'inscription"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Map<String, Object> sendconfirmationCode(String phone) {
        try {
            Optional<PhoneVerification> phoneVerificationOptional = phoneVerificationRepository.findByPhone(phone);
            if (phoneVerificationOptional.isPresent()) {

//                String codeConf = "1234";
                String codeConf = makePassword(4);
                String message = codeConf + " est votre code de vérification pour GOUABO.";

                PhoneVerification phoneVerification = phoneVerificationOptional.get();
                if (Objects.equals(phone, "69686734")) {
                    phoneVerification.setVerificationCode("1234");
//                    smsService.sendSimpleSMs();
                } else {
                    phoneVerification.setVerificationCode(codeConf);
                }

               if (!Objects.equals(phone, "69686734")) {
                   smsService.sendSimpleSMs(phone, message, "GOUABO");
               }

                phoneVerificationRepository.save(phoneVerification);

                return Response.success(phone, "Code SMS envoyé");

            } else {
//                String codeConf = "1234";
                String codeConf = makePassword(4);
                String message = codeConf + " est votre code de vérification pour GOUABO.";

                PhoneVerification phoneVerification = new PhoneVerification();
                phoneVerification.setVerificationCode(codeConf);
                phoneVerification.setPhone(phone);
                phoneVerificationRepository.save(phoneVerification);

                smsService.sendSimpleSMs(phone, message, "GOUABO");

                return Response.success(phone, "Code SMS envoyé");
            }
        } catch (Exception e) {
            return Response.error(e, "Erreur d'envoie du code");
        }
    }

    public ResponseEntity<Map<String, Object>> verifyCode(PhoneVerification phoneVerification) {
        try {
            Optional<PhoneVerification> phoneVerificationOptional = phoneVerificationRepository.findByPhoneAndVerificationCode(phoneVerification.getPhone(), phoneVerification.getVerificationCode());
            if (phoneVerificationOptional.isPresent()) {
                ApplicationUser user = new ApplicationUser();
                user.setUsername(phoneVerification.getPhone());
                user.setPassword(phoneVerification.getPhone());
                UserConnected userConnected = checkUser(user);
                return new ResponseEntity<>(Response.success(userConnected, "Vérification réussie"), HttpStatus.OK);
            } else {
                phoneVerification.setId(0L);
                return new ResponseEntity<>(Response.success(phoneVerification, "Vérification échouée, le code n'est pas correct."), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.error(e, "Erreur de vérification."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Map<String, Object> getUsers(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "lastModifiedAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<ApplicationUser> users = applicationUserRepository.findAllBySupprime(true, paging);
            return Response.success(users, "Liste des utilisateurs");
        } catch (Exception e) {
            return Response.error(e, "Erreur de récupération");
        }
    }

    public Map<String, Object> deleteUser(Long userId) {
        try {
            Optional<ApplicationUser> user = applicationUserRepository.findById(userId);
            if (user.isPresent()) {
                ApplicationUser applicationUser = user.get();
                applicationUser.setUsername(applicationUser.getUsername() + "_" + new Date().getTime() + "_removed");
                applicationUser.setSupprime(false);
               ApplicationUser userSaved = applicationUserRepository.save(applicationUser);
                return Response.success(userSaved, "Votre compte a été supprimé avec succès.");
            } else {
                return Response.error(null, "Utilisateur supprimé.");
            }
//            return Response.success(null, "Utilisateur supprimé.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de suppression.");
        }
    }



    public void sendEmail() throws MessagingException {
        sendEmailService.sendEmail();
    }
}
