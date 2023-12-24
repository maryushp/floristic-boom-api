package com.floristicboom.auth.service;

import com.floristicboom.auth.models.AuthenticationRequest;
import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.auth.models.TokenDTO;
import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.credentials.service.CredentialsService;
import com.floristicboom.credentials.service.CustomUserDetailsService;
import com.floristicboom.jwt.service.JwtService;
import com.floristicboom.jwt.service.TokenGenerator;
import com.floristicboom.user.service.UserService;
import com.floristicboom.utils.exceptionhandler.exceptions.UserAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.floristicboom.utils.Constants.ALREADY_REGISTERED;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {
    private final CredentialsService credentialsService;
    private final UserService userService;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    @Override
    public TokenDTO register(RegisterRequest request) {
        if (credentialsService.existsByLogin(request.email())) {
            throw new UserAlreadyRegisteredException(String.format(ALREADY_REGISTERED, request.email()));
        }
        Credentials credentials = credentialsService.create(request);
        userService.create(request, credentials);
        return tokenGenerator.createToken(credentials);
    }

    @Override
    public TokenDTO authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),
                request.password()));
        Credentials credentials = credentialsService.findByLogin(request.email());
        return tokenGenerator.createToken(credentials);
    }

    @Override
    public String refreshToken(String jwt) {
        String email = jwtService.extractUsername(jwt);
        Credentials credentials = customUserDetailsService.loadUserByUsername(email);
        return tokenGenerator.createAccessToken(credentials);
    }
}