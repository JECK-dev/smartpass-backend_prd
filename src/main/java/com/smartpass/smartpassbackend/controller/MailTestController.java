package com.smartpass.smartpassbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.smartpass.smartpassbackend.service.EmailService;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*") // puedes limitarlo luego a tu frontend
public class MailTestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public String enviarCorreoPrueba(@RequestParam String to) {
        try {
            String asunto = "Prueba de envío SmartPass";
            String mensaje = "Correo enviado correctamente desde el backend desplegado en Render ✅";
            emailService.enviarCorreo(to, asunto, mensaje);
            return "Correo de prueba enviado a: " + to;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar correo: " + e.getMessage();
        }
    }
}
