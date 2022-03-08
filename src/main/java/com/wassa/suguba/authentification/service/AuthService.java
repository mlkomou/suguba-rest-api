package com.wassa.suguba.authentification.service;

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

    public AuthService(ApplicationUserRepository applicationUserRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.authenticationManager = authenticationManager;
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
                userConnected.setUsername(applicationUser.getUsername());
                userConnected.setApplicationUser(user);
                System.out.println("user connected " + userConnected.getUsername());
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
        String passArray = "qwertyuiopasdfgghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM!@#$%^&*0123456789";
        int charactersLength = passArray.length();
        String result = "";
        for (int i = 0; i < qoutas; i++) {
            result += passArray.charAt((int) Math.floor(Math.random() * charactersLength));
        }
        return result;
    }


}
