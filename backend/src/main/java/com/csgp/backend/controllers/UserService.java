package com.csgp.backend.controllers;

import java.lang.String;


import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.csgp.backend.dto.BearerToken;
import com.csgp.backend.model.Token;
import com.csgp.backend.model.User;
import com.csgp.backend.repositories.TokenRepository;
import com.csgp.backend.repositories.UserRepository;
import com.csgp.backend.security.JwtUtilities;

import java.util.List;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class UserService {
    private final AuthenticationManager authenticationManager ;
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtilities jwtUtilities ;
    private final TokenRepository tokenRepository;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository,
    PasswordEncoder passwordEncoder, JwtUtilities jwtUtilities, TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilities = jwtUtilities;
        this.tokenRepository = tokenRepository;
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }

    
    public ResponseEntity<?> register(User userData) {
        if(userRepository.existsByUsername(userData.getUsername())) { 
            return  new ResponseEntity<>("username is already taken!", HttpStatus.SEE_OTHER); 
        } else { 
            User user = new User();
            user.setUsername(userData.getUsername());
            user.setPassword(passwordEncoder.encode(userData.getPassword()));

            //String myrole = "ROLE_USER";


            //Role role = roleRepository.findByRoleName(RoleName.valueOf(myrole));

            //user.setUserRole(registerDto.getUserRole());

            //user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            //String token = jwtUtilities.generateToken(user.getUsername(), Collections.singletonList("ROLE_USER"));
            return new ResponseEntity<> ("Usuario creado exitósamente", HttpStatus.CREATED);
            //return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).header(HttpHeaders.
            //ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();

        }
        }

    public ResponseEntity<?> authenticate(User userData) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userData.getUsername(),
                        userData.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        List<String> rolesNames = Collections.singletonList("ROLE_USER");
        
        String token = jwtUtilities.generateToken(user.getUsername(),rolesNames);

        // primero revoco todos los tokens guardados hasta ahora
        revokeAllTokenByUser(user);
        // guardo el nuevo token generado, el único válido
        saveUserToken(token, user);
        
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).
                    header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
    }

    

    private void saveUserToken(String accessToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        //token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }


}
