package com.bookstore.auth.service;

import com.bookstore.auth.model.jpa.User;
import com.bookstore.auth.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    public Optional<User> getUser(String email) {
        return userRepo.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
