package com.floristicboom.credentials.service;

import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.credentials.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.USER_WITH_LOGIN_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final CredentialsRepository credentialsRepository;

    @Override
    public Credentials loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_WITH_LOGIN_NOT_FOUND, username)));
    }
}