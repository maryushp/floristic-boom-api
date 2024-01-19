package com.floristicboom.jwt.service;


import com.floristicboom.auth.models.TokenDTO;
import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.jwt.TokenType;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.CacheException;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import static com.floristicboom.utils.Constants.*;


@Component
@RequiredArgsConstructor
public class TokenGenerator {
    private final CacheManager cacheManager;
    private final JwtService jwtService;

    public String createAccessToken(Credentials credentials) {
        jwtService.invalidateTokenIfExist(credentials.getId(), TokenType.ACCESS);
        String jwt = jwtService.createSignedJwt(credentials, TokenType.ACCESS);
        return putTokenInCache(ACCESS_TOKENS, credentials, jwt);
    }

    public String createRefreshToken(Credentials credentials) {
        jwtService.invalidateTokenIfExist(credentials.getId(), TokenType.REFRESH);
        String jwt = jwtService.createSignedJwt(credentials, TokenType.REFRESH);
        return putTokenInCache(REFRESH_TOKENS, credentials, jwt);
    }

    public TokenDTO createToken(Credentials credentials) {
        return TokenDTO.builder()
                .userId(credentials.getId())
                .accessToken(createAccessToken(credentials))
                .refreshToken(createRefreshToken(credentials)).build();
    }

    private String putTokenInCache(String refreshTokens, Credentials credentials, String jwt) {
        var cache = cacheManager.getCache(refreshTokens);
        if (cache == null) throw new CacheException(CACHE_NOT_FOUND);
        cache.put(credentials.getId(), jwt);
        return jwt;
    }

}