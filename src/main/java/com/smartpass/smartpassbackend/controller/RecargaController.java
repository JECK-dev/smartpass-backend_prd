package com.smartpass.smartpassbackend.controller;

import com.smartpass.smartpassbackend.service.RecargaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/recargas")
@CrossOrigin(origins = "http://localhost:4200")
public class RecargaController {

    @Autowired
    private RecargaService recargaService;

    @PostMapping("/realizar")
    public ResponseEntity<String> realizarRecarga(
            @RequestParam Integer idContrato,
            @RequestParam BigDecimal monto,
            @RequestParam Integer medioPago,
            @RequestParam String descripcion) {

        String respuesta = recargaService.realizarRecarga(idContrato, monto, medioPago, descripcion);
        return ResponseEntity.ok(respuesta);
    }
}
