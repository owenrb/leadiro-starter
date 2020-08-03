package com.leadiro.starter.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Decode a JWT token held in the Authorization header and place the associated Authentication in the SecurityContextHolder.
 */
@Component
@Slf4j
@SuppressWarnings("NullableProblems")
public class JwtBearerTokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired private JwtDecoder decoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException {
        try {
            String token = defaultString(request.getHeader("Authorization"), request.getParameter("Authorization"));
            log.trace("Filtering endpoint {} {} token: {}", request.getMethod(), request.getRequestURI(), token == null ? "none" : StringUtils.left(token, 20) + "...");
            if (token != null && token.startsWith("Bearer ")) {
                //Remove the Bearer from the start of the token
                token = StringUtils.substringAfter(token, " ");
            }
            try {
                Authentication authorised = decoder.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authorised);
                //Add the email to the logging context
                if (authorised != null) {
                    AuthAccount account = (AuthAccount) authorised.getPrincipal();
                    MDC.put("email", account.getEmail());
                }
                else {
                    MDC.put("email", "anonymous");
                }
            }
            catch (InvalidTokenException e) {
                //Set the response status to unauthorised
                log.warn("Invalid token for access to {}: {}", request.getRequestURI(), e.getMessage());
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            log.error("Error filtering request", e);
            throw new ServletException(e);
        }
    }

}

