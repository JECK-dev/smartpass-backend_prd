package com.smartpass.smartpassbackend.controller;

import com.smartpass.smartpassbackend.model.Cliente;
import com.smartpass.smartpassbackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/pordocumento/{documento}")
    public ResponseEntity<Cliente> buscarClientePorDocumento(@PathVariable String documento) {
        return clienteService.buscarPorDocumento(documento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}