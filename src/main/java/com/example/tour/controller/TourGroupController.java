package com.example.tour.controller;

import com.example.tour.entity.TourGroup;
import com.example.tour.service.TourGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tour-groups")
public class TourGroupController {

    private final TourGroupService tourGroupService;

    public TourGroupController(TourGroupService tourGroupService) {
        this.tourGroupService = tourGroupService;
    }

    @GetMapping
    public List<TourGroup> getAllTourGroups() {
        return tourGroupService.findAll();
    }

    @GetMapping("/{id}")
    public TourGroup getTourGroupById(@PathVariable Long id) {
        return tourGroupService.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour group not found with id: " + id));
    }

    @PostMapping
    public TourGroup createTourGroup(@RequestBody TourGroup tourGroup) {
        return tourGroupService.save(tourGroup);
    }

    @PutMapping("/{id}")
    public TourGroup updateTourGroup(@PathVariable Long id, @RequestBody TourGroup tourGroup) {
        tourGroup.setId(id);
        return tourGroupService.save(tourGroup);
    }

    // 删除旅游团
    @DeleteMapping("/{id}")
    public void deleteTourGroup(@PathVariable Long id) {
        tourGroupService.deleteById(id);
    }

    @GetMapping("/{id}/deposit")
    public Map<String, Double> calculateDeposit(@PathVariable Long id) {
        Double deposit = tourGroupService.calculateDeposit(id);
        return Map.of("depositAmount", deposit);
    }

    @GetMapping("/{id}/check-availability")
    public Map<String, Boolean> checkAvailability(
            @PathVariable Long id,
            @RequestParam Integer numberOfPeople) {
        boolean available = tourGroupService.hasAvailableSpots(id, numberOfPeople);
        return Map.of("available", available);
    }

    // 修改旅游团信息
    @PutMapping("/{id}/info")
    public TourGroup updateTourGroupInfo(@PathVariable Long id, @RequestBody TourGroup updatedGroup) {
        return tourGroupService.updateTourGroupInfo(id, updatedGroup);
    }

    // 查看旅游团信息
    @GetMapping("/{id}/details")
    public TourGroup getTourGroupDetails(@PathVariable Long id) {
        return tourGroupService.getTourGroupDetails(id);
    }

    // 设置旅游团状态
    @PutMapping("/{id}/status")
    public TourGroup setTourGroupStatus(@PathVariable Long id, @RequestParam String status) {
        return tourGroupService.setTourGroupStatus(id, status);
    }
}