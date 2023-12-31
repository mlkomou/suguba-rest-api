package com.wassa.suguba.authentification.security;

import com.wassa.suguba.authentification.entity.ApplicationUser;
import com.wassa.suguba.authentification.repo.ApplicationUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(1)
public class InitUsers implements ApplicationRunner {

    private final ApplicationUserRepository applicationUser;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitUsers(ApplicationUserRepository applicationUser, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUser = applicationUser;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public void run(ApplicationArguments args) {
//
//        List<Produit> produits = produitRepository.findAll();
//        produits.forEach(produit -> {
//            if (produit.getFournisseur() == null) {
//                Fournisseur fournisseur = fournisseurRepository.getById(1L);
//                produit.setFournisseur(fournisseur);
//                produitRepository.save(produit);
//            }
//        });
        Optional<ApplicationUser> applicationUserOptional = applicationUser.findByUsername("suguba@gmail.com");
        if (applicationUserOptional.isPresent()) return;
        ApplicationUser user = new  ApplicationUser();

        user.setUsername("suguba@gmail.com");
        user.setPassword(bCryptPasswordEncoder.encode("suguba"));
        user.setType("Admin");
        applicationUser.save(user);

            }
}
