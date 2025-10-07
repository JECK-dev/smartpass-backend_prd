package com.smartpass.smartpassbackend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailTestController {
    private final JavaMailSender mailSender;
    @Value("${MAIL_FROM}") String from;
    public MailTestController(JavaMailSender mailSender){ this.mailSender = mailSender; }

    @GetMapping("/test")
    public String send(@RequestParam String to) {
        var msg = new org.springframework.mail.SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject("Prueba SmartPass");
        msg.setText("Correo OK desde Render 👍");
        mailSender.send(msg);
        return "Enviado a " + to;
    }
}
