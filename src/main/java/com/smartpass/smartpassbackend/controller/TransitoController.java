package com.smartpass.smartpassbackend.controller;

import com.smartpass.smartpassbackend.model.Transito;
import com.smartpass.smartpassbackend.service.TransitoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transitos")
@CrossOrigin(origins = "http://localhost:4200")
public class TransitoController {

    private final TransitoService service;

    public TransitoController(TransitoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transito> listarTransitos() {
        return service.obtenerTodos();
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Transito> obtenerPorCliente(@PathVariable Long idCliente) {
        return service.obtenerPorCliente(idCliente);
    }
}