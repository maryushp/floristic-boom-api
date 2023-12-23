package com.floristicboom.auth.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @Email(message = "email.email" )
        @NotBlank(message ="email.notBlank")
        String email,
        @NotBlank(message = "password.notBlank")
        String password) {
}