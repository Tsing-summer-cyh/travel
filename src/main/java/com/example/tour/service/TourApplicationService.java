package com.example.tour.service;

import com.example.tour.entity.TourApplication;
import com.example.tour.entity.TourGroup;
import com.example.tour.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TourApplicationService {

    private final Map<Long, TourApplication> tourApplicationRepository;
    private final Map<Long, TourGroup> tourGroupRepository;
    private final Map<Long, User> userRepository;
    private final AtomicLong idGenerator;

    public TourApplicationService(
            Map<Long, TourApplication> tourApplicationRepository,
            Map<Long, TourGroup> tourGroupRepository,
            Map<Long, User> userRepository,
            AtomicLong tourApplicationIdGenerator) {
        this.tourApplicationRepository = tourApplicationRepository;
        this.tourGroupRepository = tourGroupRepository;
        this.userRepository = userRepository;
        this.idGenerator = tourApplicationIdGenerator;
    }

    public List<TourApplication> findAll() {
        return new ArrayList<>(tourApplicationRepository.values());
    }

    public List<TourApplication> findByUserId(Long userId) {
        return tourApplicationRepository.values().stream()
                .filter(app -> app.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<TourApplication> findByTourGroupId(Long tourGroupId) {
        return tourApplicationRepository.values().stream()
                .filter(app -> app.getTourGroupId().equals(tourGroupId))
                .collect(Collectors.toList());
    }

    public Optional<TourApplication> findById(Long id) {
        return Optional.ofNullable(tourApplicationRepository.get(id));
    }
    // 创建新的旅游团申请
    public TourApplication createApplication(TourApplication application) {
        // 验证用户存在
        if (!userRepository.containsKey(application.getUserId())) {
            throw new RuntimeException("User not found with id: " + application.getUserId());
        }

        // 验证旅游团存在
        TourGroup tourGroup = tourGroupRepository.get(application.getTourGroupId());
        if (tourGroup == null) {
            throw new RuntimeException("Tour group not found with id: " + application.getTourGroupId());
        }

        // 检查旅游团是否还有空位
        int currentParticipants = tourGroup.getApplications().stream()
                .filter(app -> app.getStatus() == TourApplication.ApplicationStatus.APPROVED)
                .mapToInt(app -> app.getNumberOfPeople())
                .sum();

        if ((currentParticipants + application.getNumberOfPeople()) > tourGroup.getMaxParticipants()) {
            throw new RuntimeException("Not enough available spots in the tour group");
        }

        // 设置申请ID和时间
        application.setId(idGenerator.getAndIncrement());
        application.setApplyTime(LocalDateTime.now());
        application.setStatus(TourApplication.ApplicationStatus.PENDING);

        // 计算订金
        Double depositPerPerson = tourGroup.calculateDeposit();
        application.setDepositAmount(depositPerPerson * application.getNumberOfPeople());
        application.setDepositPaid(false);

        // 保存申请
        tourApplicationRepository.put(application.getId(), application);

        // 更新旅游团的申请列表
        tourGroup.getApplications().add(application);
        tourGroupRepository.put(tourGroup.getId(), tourGroup);

        return application;
    }

    // 更新申请状态
    public TourApplication updateStatus(Long id, TourApplication.ApplicationStatus status) {
        TourApplication application = tourApplicationRepository.get(id);
        if (application == null) {
            throw new RuntimeException("Application not found with id: " + id);
        }

        application.setStatus(status);
        tourApplicationRepository.put(id, application);

        return application;
    }

    // 标记订金已支付
    public TourApplication markDepositAsPaid(Long id) {
        TourApplication application = tourApplicationRepository.get(id);
        if (application == null) {
            throw new RuntimeException("Application not found with id: " + id);
        }

        application.setDepositPaid(true);
        tourApplicationRepository.put(id, application);

        return application;
    }

    // 取消申请
    public void cancelApplication(Long id) {
        TourApplication application = tourApplicationRepository.get(id);
        if (application == null) {
            throw new RuntimeException("Application not found with id: " + id);
        }

        application.setStatus(TourApplication.ApplicationStatus.CANCELLED);
        tourApplicationRepository.put(id, application);

        // 从旅游团的申请列表中移除
        TourGroup tourGroup = tourGroupRepository.get(application.getTourGroupId());
        if (tourGroup != null) {
            tourGroup.getApplications().removeIf(app -> app.getId().equals(id));
            tourGroupRepository.put(tourGroup.getId(), tourGroup);
        }
    }
}
