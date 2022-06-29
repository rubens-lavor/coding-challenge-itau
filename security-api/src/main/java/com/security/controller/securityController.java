package com.security.controller;

import com.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class securityController {

    private final UserService userService; //authenticationService

    public securityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<String> get(@PathVariable String username) {
        var user = userService.loadUserByUsername(username).getUsername();
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/{username}")
    public ResponseEntity<UserDetails> authentication(@PathVariable String username) {
        return ResponseEntity.ok(userService.loadUserByUsername(username));
    }
}
