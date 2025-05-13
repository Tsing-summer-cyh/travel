package com.example.tour.service;

import com.example.tour.entity.TourGroup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TourGroupService {

    private final Map<Long, TourGroup> tourGroupRepository;
    private final AtomicLong idGenerator;

    public TourGroupService(Map<Long, TourGroup> tourGroupRepository, AtomicLong tourGroupIdGenerator) {
        this.tourGroupRepository = tourGroupRepository;
        this.idGenerator = tourGroupIdGenerator;
    }

    public List<TourGroup> findAll() {
        return new ArrayList<>(tourGroupRepository.values());
    }

    public Optional<TourGroup> findById(Long id) {
        return Optional.ofNullable(tourGroupRepository.get(id));
    }

    public TourGroup save(TourGroup tourGroup) {
        if (tourGroup.getId() == null) {
            tourGroup.setId(idGenerator.getAndIncrement());
        }
        tourGroupRepository.put(tourGroup.getId(), tourGroup);
        return tourGroup;
    }

    public void deleteById(Long id) {
        tourGroupRepository.remove(id);
    }

    // 计算指定旅游团的订金
    public Double calculateDeposit(Long tourGroupId) {
        TourGroup tourGroup = tourGroupRepository.get(tourGroupId);
        if (tourGroup == null) {
            throw new RuntimeException("Tour group not found with id: " + tourGroupId);
        }
        return tourGroup.calculateDeposit();
    }

    // 检查旅游团是否还有空位
    public boolean hasAvailableSpots(Long tourGroupId, int numberOfPeople) {
        TourGroup tourGroup = tourGroupRepository.get(tourGroupId);
        if (tourGroup == null) {
            throw new RuntimeException("Tour group not found with id: " + tourGroupId);
        }

        int currentParticipants = tourGroup.getApplications().stream()
                .filter(app -> app.getStatus() == com.example.tour.entity.TourApplication.ApplicationStatus.APPROVED)
                .mapToInt(app -> app.getNumberOfPeople())
                .sum();

        return (currentParticipants + numberOfPeople) <= tourGroup.getMaxParticipants();
    }
}
