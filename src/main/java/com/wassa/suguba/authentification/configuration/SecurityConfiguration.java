package com.wassa.suguba.authentification.configuration;


import com.wassa.suguba.authentification.security.AuthenticationFilter;
import com.wassa.suguba.authentification.security.AuthorizationFilter;
import com.wassa.suguba.authentification.service.ApplicationUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

import static com.wassa.suguba.authentification.constants.SecurityConstants.*;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final ApplicationUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public SecurityConfiguration(ApplicationUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(SIGN_UP_URL,
                        LOGIN_UP_URL,
                        DOWNLOAD_CATEGORIE_URL,
                        DOWNLOAD_PRODUIT_URL,
                        DOWNLOAD_CATEGORIE_URL,
                        PAIEMENT_URL,
                        APP_VERSION_URL,
                        PHARMACIE_VERSION_URL,
                        PAIEMENT_FACTURE_VERSION_URL,
                        PUB_URL,

                        CHECK_UP_URL,
                        CREATE_USERU_URL,
                        SEND_SMS_URL,
                        SIGN_UP_SOUSCRIPTION_URL,
                        FILE_DOWNLOAD_URL,
                        PUB_URL_LISTE,
                        PRODUIT_URL_LISTE,
                        CATEGORIE_URL_LISTE,
                        PRODUIT_URL_CATEGORIE,
                        DOWNLOAD_PHARMACIE,
                        DOWNLOAD_VOYAGE,
                        DOWNLOAD_URL,
                        DOWNLOAD_PAIEMENTFACTURE,
                        DOWNLOAD_IMMOBILIER,
                        BANQUE_IMMOBILIER,
                        AEROPORT


//                        AEROPORT
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
