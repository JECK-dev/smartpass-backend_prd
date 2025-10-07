package com.smartpass.smartpassbackend.repository;

import com.smartpass.smartpassbackend.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, String> {
    Optional<Login> findByDniAndPassword(String dni, String password);
}
