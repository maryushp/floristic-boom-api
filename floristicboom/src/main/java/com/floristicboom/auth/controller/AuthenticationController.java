package com.floristicboom.auth.controller;


import com.floristicboom.auth.models.AuthenticationRequest;
import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.auth.models.TokenDTO;
import com.floristicboom.auth.service.AuthenticationService;
import com.floristicboom.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.floristicboom.utils.Constants.AUTHENTICATION_BEARER_TOKEN;
import static com.floristicboom.utils.Constants.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TokenDTO> register(@Valid @RequestPart("request") RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(@RequestHeader(AUTHORIZATION_HEADER) String bearerToken,
                                             HttpServletResponse response) {
        jwtService.validateToken(bearerToken);
        String newAccessToken = authenticationService.refreshToken(bearerToken.substring(7));
        response.setHeader(AUTHORIZATION_HEADER, AUTHENTICATION_BEARER_TOKEN + newAccessToken);
        return ResponseEntity.noContent().build();
    }
}