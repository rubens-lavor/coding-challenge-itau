package com.security.controller;

import com.security.data.User;
import com.security.repository.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ReviewerRepository reviewerRepository;
//
//    public UserController(ReviewerRepository reviewerRepository) {
//        this.reviewerRepository = reviewerRepository;
//    }
//
//    public UserController() {
//    } TODO: deletar este controller

    @GetMapping("/listAll")
    public ResponseEntity<List<User>> listAll(){
        return ResponseEntity.ok(reviewerRepository.findAll().stream()
                .map(reviewer -> User.of(
                        reviewer.getId(),
                        reviewer.getUsername(),
                        reviewer.getPassword()))
                .collect(Collectors.toList()));
    }
}
