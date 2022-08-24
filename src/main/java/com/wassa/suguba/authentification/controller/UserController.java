package com.wassa.suguba.authentification.controller;

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

import javax.mail.MessagingException;
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
    Map<String, Object> signupSouscription(@RequestBody SouscriptionAndPhoneNumbers souscriptionPayload) {
        return authService.signupSouscription(souscriptionPayload);
    }

    @GetMapping("/current-user/{userId}")
    ResponseEntity<Map<String, Object>> getCurrentUser(@PathVariable Long userId) {
        return authService.getCurrentUser(userId);
    }

    @PostMapping("/souscriptionInApp")
    ResponseEntity<Map<String, Object>> souscriptionInApp(@RequestBody SouscriptionPayload souscriptionPayload) {
        return authService.souscriptionInApp(souscriptionPayload);
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
