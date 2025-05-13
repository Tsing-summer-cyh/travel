package com.example.tour.config;

import com.example.tour.entity.User;
import com.example.tour.entity.TourGroup;
import com.example.tour.entity.TourApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class DataConfig {

    @Bean
    public Map<Long, User> userRepository() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Long, TourGroup> tourGroupRepository() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Long, TourApplication> tourApplicationRepository() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public AtomicLong userIdGenerator() {
        return new AtomicLong(1);
    }

    @Bean
    public AtomicLong tourGroupIdGenerator() {
        return new AtomicLong(1);
    }

    @Bean
    public AtomicLong tourApplicationIdGenerator() {
        return new AtomicLong(1);
    }
}
