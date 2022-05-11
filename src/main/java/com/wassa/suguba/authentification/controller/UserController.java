package com.wassa.suguba.authentification.controller;

import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import com.wassa.suguba.authentification.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    Map<String, Object> createUser(@RequestBody ApplicationUser user) {
        return authService.createUser(user);
    }

    @PostMapping("/user-phone")
    public ResponseEntity<Map<String, Object>> signupMobile(@RequestParam("oneSignalUserId") String oneSignalUserId) {
        return authService.signupMobileUser(oneSignalUserId);
    }

    @PostMapping("/check_user")
    Map<String, Object> checkUser(@RequestBody ApplicationUser user) {
        return authService.chackUser(user);
    }


}
