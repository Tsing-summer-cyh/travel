package com.example.tour.service;

import com.example.tour.entity.User;
import com.example.tour.entity.TourGroup;
import com.example.tour.entity.TourApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileStorageService {

    private final Map<Long, User> userRepository;
    private final Map<Long, TourGroup> tourGroupRepository;
    private final Map<Long, TourApplication> tourApplicationRepository;
    private final ObjectMapper objectMapper;

    @Value("${app.storage.users-file:./data/users.json}")
    private String usersFile;

    @Value("${app.storage.tour-groups-file:./data/tour-groups.json}")
    private String tourGroupsFile;

    @Value("${app.storage.tour-applications-file:./data/tour-applications.json}")
    private String tourApplicationsFile;

    public FileStorageService(
            Map<Long, User> userRepository,
            Map<Long, TourGroup> tourGroupRepository,
            Map<Long, TourApplication> tourApplicationRepository) {
        this.userRepository = userRepository;
        this.tourGroupRepository = tourGroupRepository;
        this.tourApplicationRepository = tourApplicationRepository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8日期时间类型
    }

    @PostConstruct
    public void init() {
        loadData(usersFile, User.class, userRepository);
        loadData(tourGroupsFile, TourGroup.class, tourGroupRepository);
        loadData(tourApplicationsFile, TourApplication.class, tourApplicationRepository);
    }

    @PreDestroy
    public void saveData() {
        saveData(usersFile, new ArrayList<>(userRepository.values()));
        saveData(tourGroupsFile, new ArrayList<>(tourGroupRepository.values()));
        saveData(tourApplicationsFile, new ArrayList<>(tourApplicationRepository.values()));
    }

    private <T> void loadData(String filePath, Class<T> type, Map<Long, T> repository) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                List<T> items = objectMapper.readValue(file,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, type));

                items.forEach(item -> {
                    try {
                        Long id = (Long) item.getClass().getMethod("getId").invoke(item);
                        repository.put(id, item);
                    } catch (Exception e) {
                        System.err.println("Error getting ID from item: " + e.getMessage());
                    }
                });

                System.out.println("Loaded " + items.size() + " " + type.getSimpleName() + " from file");
            } catch (IOException e) {
                System.err.println("Error loading " + type.getSimpleName() + " from file: " + e.getMessage());
            }
        }
    }

    private <T> void saveData(String filePath, List<T> items) {
        try {
            File file = new File(filePath);
            File directory = file.getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            objectMapper.writeValue(file, items);
            System.out.println("Saved " + items.size() + " items to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving to " + filePath + ": " + e.getMessage());
        }
    }
}
