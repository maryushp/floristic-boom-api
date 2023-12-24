package com.floristicboom.user.service;

import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.user.model.User;
import com.floristicboom.user.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User create(RegisterRequest request, Credentials credentials);

    Page<UserDTO> readAll(Pageable pageable);

    UserDTO findByEmail(String email);

    UserDTO findById(Long id);

    void delete(String email);
}