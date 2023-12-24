package com.floristicboom.credentials.service;

import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.credentials.repository.CredentialsRepository;
import com.floristicboom.utils.exceptionhandler.exceptions.UserAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class DefaultCredentialsService implements CredentialsService {
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Credentials create(RegisterRequest request) {
        credentialsRepository.findByLogin(request.email()).ifPresent(req -> {
            throw new UserAlreadyRegisteredException(String.format(ALREADY_REGISTERED, request.email()));
        });
        Credentials credentials = Credentials.builder()
                .login(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        return credentialsRepository.save(credentials);
    }

    @Override
    public Credentials findByLogin(String login) {
        return credentialsRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_WITH_LOGIN_NOT_FOUND, login)));
    }

    @Override
    public boolean existsByLogin(String login) {
        return credentialsRepository.existsByLogin(login);
    }

    @Override
    public void delete(Long id) {
        credentialsRepository.findById(id).ifPresentOrElse(credentialsRepository::delete,
                () -> {
                    throw new UsernameNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id));
                });
    }
}