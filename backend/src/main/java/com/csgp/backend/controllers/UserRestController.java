package com.csgp.backend.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csgp.backend.model.User;


@RestController
@RequestMapping("/login")
public class UserRestController {
    private final UserService userService ;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody User userData) { 
        return userService.register(userData);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody User userLoginData) { 
        return userService.authenticate(userLoginData);
    }
}
