package com.floristicboom.auth.models;

import lombok.Builder;

@Builder
public record TokenDTO(
        Long userId,
        String accessToken,
        String refreshToken) {
}