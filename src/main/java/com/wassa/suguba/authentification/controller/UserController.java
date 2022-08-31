package com.wassa.suguba.authentification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassa.suguba.app.entity.Banque;
import com.wassa.suguba.app.entity.PhoneVerification;
import com.wassa.suguba.app.payload.AdminUserPayload;
import com.wassa.suguba.app.payload.SouscriptionAndPhoneNumbers;
import com.wassa.suguba.app.payload.SouscriptionPayload;
import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import com.wassa.suguba.authentification.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthService authService;
    private final ApplicationUserRepository applicationUserRepository;

    public UserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthService authService) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authService = authService;
    }

    @PostMapping("/record")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }

    @PostMapping("/login")
    ResponseEntity<Map<String, Object>> loginUser(@RequestBody ApplicationUser user) {
        System.err.println(user.getUsername());
        System.err.println(user.getPassword());
        return authService.loginUser(user);
    }

    @PostMapping("/create")
    Map<String, Object> createUser(@RequestBody AdminUserPayload user) {
        return authService.createUser(user);
    }

    @PostMapping("/signupSouscription")
    Map<String, Object> signupSouscription(
                                           @RequestParam("souscriptionPayload") String souscriptionPayloadString,
                                           @RequestParam("identiteFile") MultipartFile identiteFile,
                                           @RequestParam("signatureFile") MultipartFile signatureFile) throws JsonProcessingException {

//        @RequestBody SouscriptionAndPhoneNumbers souscriptionPayload,
        SouscriptionAndPhoneNumbers souscriptionPayload = new ObjectMapper().readValue(souscriptionPayloadString, SouscriptionAndPhoneNumbers.class);
        return authService.signupSouscription(souscriptionPayload, identiteFile, signatureFile);
    }

    @GetMapping("/current-user/{userId}")
    ResponseEntity<Map<String, Object>> getCurrentUser(@PathVariable Long userId) {
        return authService.getCurrentUser(userId);
    }

    @PostMapping("/souscriptionInApp")
    ResponseEntity<Map<String, Object>> souscriptionInApp(@RequestParam("souscriptionPayload") String souscriptionPayloadString,
                                                          @RequestParam("identiteFile") MultipartFile identiteFile,
                                                          @RequestParam("signatureFile") MultipartFile signatureFile) throws JsonProcessingException {
        SouscriptionPayload souscriptionPayload = new ObjectMapper().readValue(souscriptionPayloadString, SouscriptionPayload.class);

        return authService.souscriptionInApp(souscriptionPayload, identiteFile, signatureFile);
    }

    @PostMapping("/page")
    public Map<String, Object> getUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return authService.getUsers(page, size);
    }

    @PostMapping("/user-phone")
    public ResponseEntity<Map<String, Object>> signupMobile(@RequestParam("oneSignalUserId") String oneSignalUserId) {
        return authService.signupMobileUser(oneSignalUserId);
    }

    @PostMapping("/check_user")
    ResponseEntity<Map<String, Object>> checkUser(@RequestBody PhoneVerification user) {
        return authService.verifyCode(user);
    }

    @GetMapping("/verifify_phone/{phone}")
    Map<String, Object> verifyPhone(@PathVariable String phone) {
        return authService.sendconfirmationCode(phone);
    }

    @GetMapping("/delete/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        return authService.deleteUser(userId);
    }

    @GetMapping("/sendEmnail")
    public void SendEmail() throws MessagingException {
     authService.sendEmail();
    }
}
