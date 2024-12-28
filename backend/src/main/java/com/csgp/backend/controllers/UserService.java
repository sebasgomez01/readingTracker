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
import com.csgp.backend.model.User;
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

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository,
    PasswordEncoder passwordEncoder, JwtUtilities jwtUtilities) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilities = jwtUtilities;
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
            return new ResponseEntity<> ("Usuario creado exitósamente", HttpStatus.OK);
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
        //user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(),rolesNames);
        //return "User login successful! Token: " + token;
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).header(HttpHeaders.
        ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
    }

}
