package com.leadiro.starter.controller.auth;

import org.springframework.security.core.Authentication;

/**
 * Get the claims from a JWT token, validating it at the same time.
 */
public interface JwtDecoder {

    /**
     * Get the Spring Security UserDetails from the token
     */
    Authentication getAuthentication(String token) throws InvalidTokenException;

}
