package com.example.tour.controller;

import com.example.tour.entity.TourApplication;
import com.example.tour.service.TourApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tour-applications")
public class TourApplicationController {

    private final TourApplicationService tourApplicationService;

    public TourApplicationController(TourApplicationService tourApplicationService) {
        this.tourApplicationService = tourApplicationService;
    }

    @GetMapping
    public List<TourApplication> getAllApplications() {
        return tourApplicationService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<TourApplication> getApplicationsByUser(@PathVariable Long userId) {
        return tourApplicationService.findByUserId(userId);
    }

    @GetMapping("/tour-group/{tourGroupId}")
    public List<TourApplication> getApplicationsByTourGroup(@PathVariable Long tourGroupId) {
        return tourApplicationService.findByTourGroupId(tourGroupId);
    }

    @GetMapping("/{id}")
    public TourApplication getApplicationById(@PathVariable Long id) {
        return tourApplicationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour application not found with id: " + id));
    }

    @PostMapping
    public TourApplication createApplication(@RequestBody TourApplication application) {
        return tourApplicationService.createApplication(application);
    }

    @PutMapping("/{id}/status")
    public TourApplication updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        TourApplication.ApplicationStatus status =
                TourApplication.ApplicationStatus.valueOf(statusUpdate.get("status"));
        return tourApplicationService.updateStatus(id, status);
    }

    @PutMapping("/{id}/pay-deposit")
    public TourApplication payDeposit(@PathVariable Long id) {
        return tourApplicationService.markDepositAsPaid(id);
    }

    @DeleteMapping("/{id}")
    public void cancelApplication(@PathVariable Long id) {
        tourApplicationService.cancelApplication(id);
    }
}
