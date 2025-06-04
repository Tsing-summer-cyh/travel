package com.example.tour.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
    private String applicantName; // 新增：申请责任人姓名
    private Integer adultNumber; // 新增：大人人数
    private Integer childNumber; // 新增：小孩人数
    private Boolean balancePaid; // 新增：余款支付情况
    private LocalDate balanceDueDate; // 新增：余款支付截止日期

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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Integer getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(Integer adultNumber) {
        this.adultNumber = adultNumber;
    }

    public Integer getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(Integer childNumber) {
        this.childNumber = childNumber;
    }

    public Boolean getBalancePaid() {
        return balancePaid;
    }

    public void setBalancePaid(Boolean balancePaid) {
        this.balancePaid = balancePaid;
    }

    public LocalDate getBalanceDueDate() {
        return balanceDueDate;
    }

    public void setBalanceDueDate(LocalDate balanceDueDate) {
        this.balanceDueDate = balanceDueDate;
    }
}