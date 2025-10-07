package com.smartpass.smartpassbackend.service;

import com.smartpass.smartpassbackend.model.Transito;
import com.smartpass.smartpassbackend.repository.TransitoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransitoService {

    private final TransitoRepository repository;

    public TransitoService(TransitoRepository repository) {
        this.repository = repository;
    }

    public List<Transito> obtenerTodos() {
        return repository.findAll();
    }

    public List<Transito> obtenerPorCliente(Long idCliente) {
        return repository.findByIdCliente(idCliente);
    }


}