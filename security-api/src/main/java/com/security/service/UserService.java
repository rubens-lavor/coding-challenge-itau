package com.security.service;

import com.security.data.User;
import com.security.repository.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var reviewer = reviewerRepository.findByUsername(username);
        if (reviewer.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return User.of(reviewer.get().getId(), reviewer.get().getUsername(), reviewer.get().getPassword());
    }
}
