package com.filmreview.controller;

import com.filmreview.dto.LoginDTO;
import com.filmreview.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login-account")
    public ResponseEntity<String> authentication(@RequestBody @Valid LoginDTO body) {
        return loginService.authentication(body);
    }

}
