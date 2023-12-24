package com.floristicboom.credentials.service;

import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.credentials.model.Credentials;

public interface CredentialsService {
    Credentials create(RegisterRequest request);

    Credentials findByLogin(String login);

    boolean existsByLogin(String email);

    void delete(Long id);
}