package com.floristicboom.user.service;

import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.user.model.User;
import com.floristicboom.user.model.UserDTO;
import com.floristicboom.user.repository.UserRepository;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.exceptionhandler.exceptions.UserAlreadyRegisteredException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public User create(RegisterRequest request, Credentials credentials) {
        if (userRepository.existsByEmail(request.email()) || userRepository.existsByPhone(request.phone()))
            throw new UserAlreadyRegisteredException(USER_ALREADY_REGISTERED);
        User user = entityToDtoMapper.toUser(request);
        user.setCredentials(credentials);
        return userRepository.save(user);
    }

    @Override
    public Page<UserDTO> readAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(entityToDtoMapper::toUserDTO);
    }

    @Override
    public UserDTO readByEmail(String email) {
        return userRepository.findByEmail(email).map(entityToDtoMapper::toUserDTO).orElseThrow(
                () -> new NoSuchItemException(String.format(USER_WITH_EMAIL_NOT_FOUND, email)));
    }

    @Override
    public void delete(String email) {
        userRepository.findByEmail(email).ifPresentOrElse(
                user -> userRepository.deleteById(user.getId()),
                () -> {
                    throw new NoSuchItemException(String.format(USER_WITH_EMAIL_NOT_FOUND, email));
                }
        );
    }
}