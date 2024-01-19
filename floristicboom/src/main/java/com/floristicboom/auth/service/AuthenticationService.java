package com.floristicboom.auth.service;

import com.floristicboom.auth.models.AuthenticationRequest;
import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.auth.models.TokenDTO;

public interface AuthenticationService {
    TokenDTO register(RegisterRequest request);

    TokenDTO authenticate(AuthenticationRequest request);

    String refreshToken(String jwt);
}