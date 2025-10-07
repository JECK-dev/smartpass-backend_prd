package com.smartpass.smartpassbackend.repository;

import com.smartpass.smartpassbackend.model.Cliente;
import com.smartpass.smartpassbackend.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Query("SELECT MAX(f.correlativo) FROM Factura f WHERE f.serie = :serie")
    String findMaxCorrelativoBySerie(@Param("serie") Integer serie);

    @Query("SELECT f FROM Factura f WHERE f.cliente.idCliente = :idCliente")
    List<Factura> findByClienteId(Long idCliente);

}