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

    // 录入顾客申请信息
    @PostMapping("/record")
    public TourApplication recordCustomerApplication(@RequestBody TourApplication application) {
        return tourApplicationService.recordCustomerApplication(application);
    }

    // 录入和打印旅游申请书
    @GetMapping("/{id}/pdf")
    public String generateTourApplicationPdf(@PathVariable Long id) {
        return tourApplicationService.generateTourApplicationPdf(id);
    }

    // 收取订金并录入支付信息
    @PutMapping("/{id}/collect-deposit")
    public TourApplication collectDeposit(@PathVariable Long id) {
        return tourApplicationService.collectDeposit(id);
    }

    // 生成余额交款单和打印旅行确认书
    @GetMapping("/{id}/balance-slip")
    public String generateBalancePaymentSlipAndConfirmation(@PathVariable Long id) {
        return tourApplicationService.generateBalancePaymentSlipAndConfirmation(id);
    }

    // 录入参加者信息
    @PutMapping("/{id}/participants")
    public TourApplication recordParticipants(@PathVariable Long id, @RequestParam Integer adultNumber, @RequestParam Integer childNumber) {
        return tourApplicationService.recordParticipants(id, adultNumber, childNumber);
    }

    // 变更/取消参加者信息
    @PutMapping("/{id}/update-participants")
    public TourApplication updateParticipants(@PathVariable Long id, @RequestParam Integer adultNumber, @RequestParam Integer childNumber) {
        return tourApplicationService.updateParticipants(id, adultNumber, childNumber);
    }

    // 取消整个申请
    @DeleteMapping("/{id}/cancel-whole")
    public void cancelWholeApplication(@PathVariable Long id) {
        tourApplicationService.cancelWholeApplication(id);
    }

    // 生成余额交款单
    @GetMapping("/{id}/generate-balance-slip")
    public TourApplication generateBalancePaymentSlip(@PathVariable Long id) {
        return tourApplicationService.generateBalancePaymentSlip(id);
    }

    // 余款支付录入
    @PutMapping("/{id}/pay-balance")
    public TourApplication recordBalancePayment(@PathVariable Long id) {
        return tourApplicationService.recordBalancePayment(id);
    }
}