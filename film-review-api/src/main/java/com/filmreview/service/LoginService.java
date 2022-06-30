package com.filmreview.service;

import com.filmreview.dto.LoginDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final RestTemplateBuilder restTemplateBuilder;

    public LoginService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    public ResponseEntity<String> authentication(LoginDTO body) {
        var token = restTemplateBuilder.build().postForEntity("http://localhost:8081/login", body, String.class);
        return token;
    }
}
