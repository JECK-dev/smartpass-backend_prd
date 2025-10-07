package com.smartpass.smartpassbackend.repository;


import com.smartpass.smartpassbackend.model.BlockchainRecarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockchainRecargaRepository extends JpaRepository<BlockchainRecarga, Integer> {

    Optional<BlockchainRecarga> findTopByOrderByIdBloqueDesc(); // último bloque
}
