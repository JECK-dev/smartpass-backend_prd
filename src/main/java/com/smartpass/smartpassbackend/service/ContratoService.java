package com.smartpass.smartpassbackend.service;

import com.smartpass.smartpassbackend.model.Contrato;
import com.smartpass.smartpassbackend.repository.ContratoInfo;
import com.smartpass.smartpassbackend.repository.ContratoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    public List<Contrato> listarContratos() {
        return contratoRepository.findAll();
    }

    public List<Contrato> obtenerContratosPorCliente(Integer idCliente) {
        return contratoRepository.findByIdCliente(idCliente);
    }

    public Optional<Contrato> obtenerContratoPorId(Integer id) {
        return contratoRepository.findById(id);
    }

    public Contrato guardarContrato(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    public void eliminarContrato(Integer id) {
        contratoRepository.deleteById(id);
    }

    public List<ContratoInfo> obtenerContratosPrepagoPorCliente(Integer idCliente) {
        return contratoRepository.obtenerContratosPrepagoPorCliente(idCliente);
    }

    public List<Contrato> getContratosPorTipo(String tipo) {
        return contratoRepository.findByTipo(tipo);
    }

    public List<Contrato> getContratosPorTipoYCliente(String tipo, Integer idCliente) {
        return contratoRepository.findByTipoAndCliente(tipo, idCliente);
    }

    @Transactional
    public Contrato darDeBaja( Integer IdContrato){
        Contrato contrato = contratoRepository.findById(IdContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        contrato.setIdEstado(2); //2  es dar de baja
        contrato.setFechaModificacion(LocalDateTime.now());
        contrato.setSaldo(BigDecimal.ZERO);

        return contratoRepository.save(contrato);
    }

    @Transactional
    public Contrato crearContrato(Contrato contrato) {
        // setear fecha de modificación
        contrato.setFechaModificacion(LocalDateTime.now());

        // saldo inicial en 0 si es nulo
        if (contrato.getSaldo() == null) {
            contrato.setSaldo(BigDecimal.ZERO);
        }

        // estado inicial: activo
        if (contrato.getIdEstado() == null) {
            contrato.setIdEstado(1);
        }

        return contratoRepository.save(contrato);
    }

    // Cambiar modalidad con validaciones
    public Contrato cambiarModalidad(Integer idContrato, Integer nuevoEstado) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado."));

        // 🔒 Validaciones de restricción (CP019)
        if (contrato.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("No es posible cambiar modalidad por saldo negativo.");
        }

        if (contrato.getIdEstado() == 2) { // Baja
            throw new RuntimeException("No se puede cambiar modalidad porque el contrato ya está dado de baja.");
        }

        // Si el nuevo estado es el mismo
        if (contrato.getIdEstado().equals(nuevoEstado)) {
            throw new RuntimeException("El contrato ya se encuentra en esa modalidad.");
        }

        contrato.setIdEstado(nuevoEstado);
        contrato.setFechaModificacion(LocalDateTime.now());

        return contratoRepository.save(contrato);
    }

}
