package com.csgp.backend.security;

import java.io.IOException;

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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
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

        String token = jwtUtilities.getToken(request) ;

        if (token!=null && jwtUtilities.validateToken(token))
        {
            String username = jwtUtilities.extractUsername(token);

            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername() ,null , userDetails.getAuthorities());
            //log.info("authenticated user with email :{}", username);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        }
            filterChain.doFilter(request,response);
    }
}
