package com.smartpass.smartpassbackend.repository;

import com.smartpass.smartpassbackend.model.Recarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface RecargaRepository extends JpaRepository<Recarga, Integer> {

    @Query("SELECT SUM(r.monto) FROM Recarga r WHERE r.idContrato = :idContrato")
    Optional<BigDecimal> sumByContrato(@Param("idContrato") Integer idContrato);

}
