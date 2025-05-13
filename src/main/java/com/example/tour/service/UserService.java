package com.example.tour.service;

import com.example.tour.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> userRepository;
    private final AtomicLong idGenerator;

    public UserService(Map<Long, User> userRepository, AtomicLong userIdGenerator) {
        this.userRepository = userRepository;
        this.idGenerator = userIdGenerator;
    }

    public List<User> findAll() {
        return new ArrayList<>(userRepository.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.get(id));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    public void deleteById(Long id) {
        userRepository.remove(id);
    }
}
