package com.leadiro.starter.controller.auth;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Get the claims from a JWT token, validating it at the same time.
 */
@Component
public class Auth0JwtDecoder implements JwtDecoder {
    private static final String AUTH0_TENANT = "https://leadiro.eu.auth0.com/";
    private final Map<String, Jwk> keys = new HashMap<>();

    /**
     * Get the Spring Security UserDetails from the token
     */
    public Authentication getAuthentication(String token) throws InvalidTokenException {
        if (token == null) {
            return null;
        }
        Map<String, Claim> claims = getClaims(token);
        AuthAccount account = lookupAccount(claims);
        return new UsernamePasswordAuthenticationToken(account, token, Collections.emptyList());
    }

    /**
     * Do whatever processing is necessary to return an AuthAccount.
     * This might be looking it up in a DB or simply returning claims from the JWT
     * TODO: Modify to suit the specific application
     */
    private AuthAccount lookupAccount(Map<String, Claim> claims) throws InvalidTokenException {
        //Auth0 stores the principal account identifier as the name
        if (! claims.containsKey("name")) {
            throw new InvalidTokenException("Token is missing a name");
        }
        AuthAccount account = new AuthAccount();
        account.setEmail(claims.get("name").asString());
        return account;
    }

    /**
     * Decode and validate the token, returning the claims.
     */
    public Map<String, Claim> getClaims(String token) throws InvalidTokenException {
        //Decode the token - this verifies that it is a well formed JWT, but not that the signature matches
        DecodedJWT jwt = JWT.decode(token);
        try {
            //Get the key id from the token, this identifies the id of the key that was used to sign it
            Jwk key = getKey(jwt.getKeyId());
            //Obtain the public key from Auth0's endpoint and no private key is required for RSA256
            Algorithm rs256 = Algorithm.RSA256((RSAPublicKey) key.getPublicKey(), null);
            //Verify the token by checking the signature and the claims
            JWTVerifier verifier = JWT.require(rs256).withIssuer(AUTH0_TENANT).build();
            return verifier.verify(token).getClaims();
        }
        catch (JwkException | JWTVerificationException e) {
            var invalidTokenException = new InvalidTokenException(e.getMessage(), e);
            if (e instanceof TokenExpiredException) {
                invalidTokenException.setExpired(true);
            }
            throw invalidTokenException;
        }
    }

    /**
     * See https://tools.ietf.org/html/rfc7517 for an explanation of JSON Web Key (JWK)
     */
    private Jwk getKey(String id) throws JwkException {
        //See if we have the key cached
        if (keys.containsKey(id)) {
            return keys.get(id);
        }
        //Get a provider that will go to the Auth0 tenant URL to obtain the public keys - https://leadiro.eu.auth0.com/.well-known/jwks.json
        JwkProvider provider = new UrlJwkProvider(AUTH0_TENANT);
        //Get the key, will throw a JwkException if it doesn't exist
        Jwk key = provider.get(id);
        keys.put(id, key);
        return key;
    }

}
