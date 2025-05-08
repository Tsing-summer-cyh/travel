package com.example.tour.controller;

import com.example.tour.entity.User;
import com.example.tour.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> list() {
        return repo.findAll();
    }

    @PostMapping
    public User add(@RequestBody User user) {
        return repo.save(user);
    }
}
