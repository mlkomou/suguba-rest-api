package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Banque;
import com.wassa.suguba.app.entity.Commande;
import com.wassa.suguba.app.entity.Immobilier;
import com.wassa.suguba.app.repository.BanqueRepository;
import com.wassa.suguba.app.repository.CommandeRepository;
import com.wassa.suguba.app.repository.ImmobilierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private final TableGenerationService tableGenerationService;
    private final CommandeRepository commandeRepository;
    private final FormatMailService formatMailService;
    private final BanqueRepository banqueRepository;
    private final ImmobilierRepository immobilierRepository;

    public SendEmailService(TableGenerationService tableGenerationService, CommandeRepository commandeRepository, FormatMailService formatMailService, BanqueRepository banqueRepository, ImmobilierRepository immobilierRepository) {
        this.banqueRepository = banqueRepository;
        this.tableGenerationService = tableGenerationService;
        this.commandeRepository = commandeRepository;
        this.formatMailService = formatMailService;
        this.immobilierRepository = immobilierRepository;
    }

    public void sendEmailWithAttachment(String mail, String objet, Long commandId) {
        try {
            Optional<Commande> commande = commandeRepository.findById(commandId);
            MimeMessage msg = javaMailSender.createMimeMessage();
            String messageFormated = formatMailService.formatEmail(tableGenerationService.generateReportMessage(commandId), commande.get().getClient().getNom(), commandId);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(mail);
            helper.setSubject(objet);
            helper.setText(messageFormated, true);
            javaMailSender.send(msg);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void sendEmailBanque(String mail, String objet, Long banqueId) {
        try {
            Optional<Banque> banque = banqueRepository.findById(banqueId);
            MimeMessage msg = javaMailSender.createMimeMessage();
            String messageFormated = formatMailService.formatEmail(tableGenerationService.generateReportMessageBanque(banqueId), banque.get().getPrenom() + " " + banque.get().getNom(), banqueId);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(mail);
            helper.setSubject(objet);
            helper.setText(messageFormated, true);
            javaMailSender.send(msg);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void sendEmailImmobilier(String mail, String objet, Long immobilierId) {
        try {
            Optional<Immobilier> immobilier = immobilierRepository.findById(immobilierId);
            MimeMessage msg = javaMailSender.createMimeMessage();
            String messageFormated = formatMailService.formatEmail(tableGenerationService.generateReportMessageImmobilier(immobilierId), immobilier.get().getMail(), immobilierId);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(mail);
            helper.setSubject(objet);
            helper.setText(messageFormated, true);
            javaMailSender.send(msg);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
