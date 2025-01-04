package com.csgp.backend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtilities jwtUtilities;
    private final CustomerUserDetailsService customerUserDetailsService;

    public JwtAuthenticationFilter(JwtUtilities jwtUtilities, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtUtilities = jwtUtilities;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {

        log.info("Starting JWT authentication for request: {}", request.getRequestURI());

        try {
            String token = jwtUtilities.getToken(request);

            if (token != null) {
                log.debug("Token extracted: {}", token);

                if (jwtUtilities.validateToken(token)) {
                    log.debug("Token is valid");

                    String username = jwtUtilities.extractUsername(token);
                    log.info("Extracted username from token: {}", username);

                    UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

                    if (userDetails != null && jwtUtilities.validateToken(token, userDetails)) {
                        log.debug("UserDetails loaded: {}", userDetails.getUsername());

                        UsernamePasswordAuthenticationToken authentication = 
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Authentication set for user: {}", userDetails.getUsername());
                    } else {
                        log.warn("UserDetails is null for username: {}", username);
                    }
                } else {
                    log.warn("Invalid token provided");
                }
            } else {
                log.debug("No token found in request");
            }
        } catch (Exception e) {
            log.error("Error occurred during JWT authentication", e);
        }

        filterChain.doFilter(request, response);
        log.info("Completed processing request: {}", request.getRequestURI());
    }
}