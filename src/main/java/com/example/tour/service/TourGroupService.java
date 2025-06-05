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

    // 删除指定ID的旅游团
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

    // 修改旅游团信息
    public TourGroup updateTourGroupInfo(Long id, TourGroup updatedGroup) {
        TourGroup tourGroup = tourGroupRepository.get(id);
        if (tourGroup == null) {
            throw new RuntimeException("Tour group not found with id: " + id);
        }
        tourGroup.setName(updatedGroup.getName());
        tourGroup.setPrice(updatedGroup.getPrice());
        tourGroup.setStartDate(updatedGroup.getStartDate());
        tourGroup.setEndDate(updatedGroup.getEndDate());
        tourGroup.setMaxParticipants(updatedGroup.getMaxParticipants());
        tourGroup.setDepositRate(updatedGroup.getDepositRate());
        tourGroup.setStatus(updatedGroup.getStatus());
        tourGroup.setRemainingSpots(updatedGroup.getRemainingSpots());
        tourGroupRepository.put(id, tourGroup);
        return tourGroup;
    }

    // 查看旅游团信息
    public TourGroup getTourGroupDetails(Long id) {
        TourGroup tourGroup = tourGroupRepository.get(id);
        if (tourGroup == null) {
            throw new RuntimeException("Tour group not found with id: " + id);
        }
        int currentParticipants = tourGroup.getApplications().stream()
                .filter(app -> app.getStatus() == com.example.tour.entity.TourApplication.ApplicationStatus.APPROVED)
                .mapToInt(app -> app.getNumberOfPeople())
                .sum();
        tourGroup.setRemainingSpots(tourGroup.getMaxParticipants() - currentParticipants);
        return tourGroup;
    }

    // 设置旅游团状态
    public TourGroup setTourGroupStatus(Long id, String status) {
        TourGroup tourGroup = tourGroupRepository.get(id);
        if (tourGroup == null) {
            throw new RuntimeException("Tour group not found with id: " + id);
        }
        tourGroup.setStatus(status);
        tourGroupRepository.put(id, tourGroup);
        return tourGroup;
    }
}