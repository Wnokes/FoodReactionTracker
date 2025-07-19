package com.pantsinc.FoodReactionTracker.controller;

import com.pantsinc.FoodReactionTracker.dto.FoodLogRequest;
import com.pantsinc.FoodReactionTracker.entity.FoodLog;
import com.pantsinc.FoodReactionTracker.exception.ValidationException;
import com.pantsinc.FoodReactionTracker.service.FoodLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/foodlog")
public class FoodLogController {

    private final FoodLogService foodLogService;

    @GetMapping("/all")
    public ResponseEntity<List<FoodLog>> getFoodLogs(Authentication authentication){
        try {
            return ResponseEntity.ok(foodLogService.getAllForUser(authentication.getName()));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Long> addNewLog(@RequestBody FoodLogRequest request, Authentication authentication){
        return ResponseEntity.ok(foodLogService.addLog(authentication.getName(), request)).build();
    }
}
