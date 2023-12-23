package com.floristicboom.auth.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Size(max = 45, message = "name.size.max" )
        @NotBlank(message = "name.notBlank")
        String firstName,
        @Size(max = 45, message = "name.size.max")
        @NotBlank(message = "name.notBlank")
        String lastName,
        @Size(max = 12, message = "phone.size.max")
        @NotBlank(message = "phone.notBlank")
        String phone,
        @Size(max = 255, message = "email.size.max")
        @NotBlank(message = "email.notBlank")
        @Email(message = "email.email")
        String email,
        @NotBlank(message = "password.notBlank")
        String password) {
}