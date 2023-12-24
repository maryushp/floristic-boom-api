package com.floristicboom.credentials.repository;

import com.floristicboom.credentials.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findByLogin(String login);

    boolean existsByLogin(String login);
}