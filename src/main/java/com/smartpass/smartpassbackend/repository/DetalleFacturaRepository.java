package com.smartpass.smartpassbackend.repository;

import com.smartpass.smartpassbackend.model.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Integer> {
}