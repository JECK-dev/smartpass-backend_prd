package com.smartpass.smartpassbackend.controller;

import com.smartpass.smartpassbackend.model.TransaccionSaldo;
import com.smartpass.smartpassbackend.service.TransaccionSaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionSaldoController {

    @Autowired
    private TransaccionSaldoService transaccionService;

    @GetMapping("/contrato/{id}")
    public ResponseEntity<List<TransaccionSaldo>> obtenerPorContrato(@PathVariable("id") Integer idContrato) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesPorContrato(idContrato));
    }
}
