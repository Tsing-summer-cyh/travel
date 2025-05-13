package com.example.tour.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TourApplication implements Serializable {
    private Long id;
    private Long userId;
    private Long tourGroupId;
    private LocalDateTime applyTime;
    private Integer numberOfPeople;
    private String specialRequirements;
    private Double depositAmount;
    private Boolean depositPaid;
    private ApplicationStatus status;

    public enum ApplicationStatus {
        PENDING,    // 待处理
        APPROVED,   // 已批准
        REJECTED,   // 已拒绝
        CANCELLED   // 已取消
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTourGroupId() {
        return tourGroupId;
    }

    public void setTourGroupId(Long tourGroupId) {
        this.tourGroupId = tourGroupId;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Boolean getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(Boolean depositPaid) {
        this.depositPaid = depositPaid;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
