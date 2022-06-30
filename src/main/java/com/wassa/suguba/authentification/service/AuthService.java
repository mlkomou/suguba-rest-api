package com.wassa.suguba.authentification.service;

import com.wassa.suguba.app.entity.Client;
import com.wassa.suguba.app.entity.PhoneVerification;
import com.wassa.suguba.app.entity.Souscrition;
import com.wassa.suguba.app.payload.SmsMessageResponse;
import com.wassa.suguba.app.payload.SmsObject;
import com.wassa.suguba.app.payload.Smses;
import com.wassa.suguba.app.payload.SouscriptionPayload;
import com.wassa.suguba.app.repository.ClientRepository;
import com.wassa.suguba.app.repository.PhoneVerificationRepository;
import com.wassa.suguba.app.repository.SouscritionRepository;
import com.wassa.suguba.app.service.SendSmsService;
import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.entity.Response;
import com.wassa.suguba.authentification.entity.UserConnected;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

import static com.wassa.suguba.authentification.constants.SecurityConstants.EXPIRATION_TIME;
import static com.wassa.suguba.authentification.constants.SecurityConstants.KEY;


@Service
public class AuthService {
    private final ApplicationUserRepository applicationUserRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PhoneVerificationRepository phoneVerificationRepository;
    private final SendSmsService sendSmsService;
    private final ClientRepository clientRepository;
    private final SouscritionRepository souscritionRepository;

    public AuthService(ApplicationUserRepository applicationUserRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder1, PhoneVerificationRepository phoneVerificationRepository, SendSmsService sendSmsService, ClientRepository clientRepository, SouscritionRepository souscritionRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
        this.phoneVerificationRepository = phoneVerificationRepository;
        this.sendSmsService = sendSmsService;
        this.clientRepository = clientRepository;
        this.souscritionRepository = souscritionRepository;
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

//    public Map<String, Object> verifyPhone(String phone) {
//        try {
//            String phoneCode = makePassword(4);
////            Optional<PhoneVerification> phoneVerificationOptional = phoneVerificationRepository.findByPhone(phone);
//            PhoneVerification phoneVerification = new PhoneVerification();
//            phoneVerification.setPhone(phone);
//
//            phoneVerification.setVerificationCode(phoneCode);
////            PhoneVerification phoneVerificationSaved = phoneVerificationRepository.save(phoneVerification);
//            //send sms
////                SmsObject smsObject = new SmsObject();
////                String text = phoneCode + " " + "est votre code de vérification pour SUGUBA.";
////                smsObject.setAccountid("Jonathan");
////                smsObject.setPassword("mdq1372dLZ");
////                smsObject.setRet_id(makePassword(10));
////                smsObject.setSender("SUGUBA");
////                smsObject.setText(text);
////                smsObject.setRet_url("https://suguba.com");
////                smsObject.setTo("223" + phoneVerification.getPhone());
//////                sendSmsService.sendSms(smsObject);
//                   String responseSms = sendSmsService.sendJson(phone);
//                    return Response.success(responseSms, "Sms envoyé.");
//
////            if (phoneVerificationOptional.isPresent()) {
////
////
////            }
////            else {
////                phoneVerification.setVerificationCode(phoneCode);
////                PhoneVerification phoneVerificationNew = phoneVerificationRepository.save(phoneVerification);
////                SmsObject smsObject = new SmsObject();
////                String text = phoneCode + " " + "est votre code de vérification pour SUGUBA.";
////                smsObject.setAccountid("Jonathan");
////                smsObject.setPassword("mdq1372dLZ");
////                smsObject.setRet_id(makePassword(10));
////                smsObject.setSender("SUGUBA");
////                smsObject.setText(text);
////                smsObject.setRet_url("https://suguba.com");
////                smsObject.setTo("223" + phoneVerification.getPhone());
////                sendSmsService.sendSms(smsObject);
////                sendSmsService.sendJson();
////                return Response.success(phoneVerificationNew, "Sms envoyé.");
//         //   }
//
//        } catch (Exception e) {
//            System.err.println(e);
//            return Response.error(e, "Erreur de vérification");
//        }
//    }

    public Map<String, Object> signupSouscription(SouscriptionPayload souscriptionPayload) {
        try {
            UserConnected userConnected = new UserConnected();
            Client client = new Client();
            client.setPhone(souscriptionPayload.getPhone());
            Client clientSaved = clientRepository.save(client);

            ApplicationUser user = new ApplicationUser();
            ApplicationUser userToAuth = new ApplicationUser();
            userToAuth.setUsername(souscriptionPayload.getPhone());
            userToAuth.setPassword(souscriptionPayload.getPhone());
            user.setUsername(souscriptionPayload.getPhone());
            user.setPassword(bCryptPasswordEncoder.encode(souscriptionPayload.getPhone()));
            user.setClient(clientSaved);

            ApplicationUser userSaved = applicationUserRepository.save(user);
            userConnected.setApplicationUser(user);
            userConnected.setToken(getToken(userToAuth));

            if (Objects.equals(souscriptionPayload.getStatut(), "Oui")) {
                Souscrition souscrition = new Souscrition();
                souscrition.setNomService(souscriptionPayload.getNomService());
                souscrition.setMontant(souscriptionPayload.getMontant());
                souscrition.setUser(userSaved);
                souscritionRepository.save(souscrition);

                return Response.success(userConnected, "Le traitement de votre souscription est en cours de validation. Nous vous appellerons dans les heures qui suivent.");
            } else {
                return Response.success(userConnected, "Vous êtes inscrits(es) avec succès.");
            }

        } catch (Exception e) {
            return Response.error(e, "Erreur dinscription, Veuillez réessayer plus tard.");
        }
    }

    public Map<String, Object> createUser(ApplicationUser user) {
        try {
            ApplicationUser lastUser = new ApplicationUser();
            user.setPassword(user.getUsername());
            lastUser.setEntreprise(user.getEntreprise());
            lastUser.setUsername(user.getUsername());
            lastUser.setPassword(user.getUsername());
            lastUser.setPassword(bCryptPasswordEncoder.encode(lastUser.getPassword()));
            ApplicationUser userSaved = applicationUserRepository.save(lastUser);

            UserConnected userConnected = new UserConnected();
            userConnected.setApplicationUser(userSaved);
            userConnected.setToken(getToken(user));
            return Response.success(userConnected, "Utilisateur inscrit");
        } catch (Exception e) {
            return Response.error(e, "erreur d'inscription");
        }
    }

    public Map<String, Object> sendconfirmationCode(String phone) {
        try {
            Optional<PhoneVerification> phoneVerificationOptional = phoneVerificationRepository.findByPhone(phone);
            if (phoneVerificationOptional.isPresent()) {
                String codeConf = makePassword(4);
                SmsObject smsObject = new SmsObject();
                List<Smses> smses = new ArrayList<>();
                Smses smses1 = new Smses();
                smses1.setText(codeConf + " " + "est votre de vérification pour SUGUBA.");
                smses1.setPhoneNumber("+223" + phone);
                smses.add(smses1);

                smsObject.setLogin("kamara");
                smsObject.setPassword("Bengaly2021!");
                smsObject.setSenderId("WASSA PAY");
                smsObject.setSmses(smses);

                PhoneVerification phoneVerification = phoneVerificationOptional.get();
                phoneVerification.setVerificationCode(codeConf);
                phoneVerificationRepository.save(phoneVerification);
                SmsMessageResponse smsMessageResponse = sendSmsService.sendSms(smsObject);
                if (Objects.equals(smsMessageResponse.getData().get(0).getStatus(), "OK")) {
                    return Response.success(smsMessageResponse, "Code envoyé");
                } else {
                    return Response.error(smsMessageResponse, "Erreur d'envoie du code");
                }
            } else {
                String codeConf = makePassword(4);
                SmsObject smsObject = new SmsObject();
                List<Smses> smses = new ArrayList<>();
                Smses smses1 = new Smses();
                smses1.setText(codeConf + " " + "est votre de vérification pour SUGUBA.");
                smses1.setPhoneNumber("+223" + phone);
                smses.add(smses1);

                smsObject.setLogin("kamara");
                smsObject.setPassword("Bengaly2021!");
                smsObject.setSenderId("WASSA PAY");
                smsObject.setSmses(smses);

                PhoneVerification phoneVerification = new PhoneVerification();
                phoneVerification.setVerificationCode(codeConf);
                phoneVerification.setPhone(phone);
                phoneVerificationRepository.save(phoneVerification);
                SmsMessageResponse smsMessageResponse = sendSmsService.sendSms(smsObject);
                if (Objects.equals(smsMessageResponse.getData().get(0).getStatus(), "OK")) {
                    return Response.success(smsMessageResponse, "Code envoyé");
                } else {
                    return Response.error(smsMessageResponse, "Erreur d'envoie du code");
                }
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
                return new ResponseEntity<>(Response.success(phoneVerification, "Vérification échouée."), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Response.error(e, "Erreur de vérification."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
