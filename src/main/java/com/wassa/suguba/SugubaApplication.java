package com.wassa.suguba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@SpringBootApplication
public class SugubaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SugubaApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSenderImpl customJavaMailSenderImpl(){

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("vmi920689.contaboserver.net");
		mailSender.setPort(587);

		mailSender.setUsername("admin@suguba.online");
		mailSender.setPassword("%xyos_e*PcqN");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.properties.mail.smtp.starttls", "true");
		props.put("mail.properties.mail.smtp.starttls.required", "true");
		props.put("mail.debug", "true");
//		#spring.mail.properties.mail.smtp.auth=true
//#spring.mail.properties.mail.smtp.starttls.enable=true
//#spring.mail.properties.mail.smtp.starttls.required=true

		return mailSender;

	}
}
