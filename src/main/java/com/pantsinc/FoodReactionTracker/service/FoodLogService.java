package com.pantsinc.FoodReactionTracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pantsinc.FoodReactionTracker.dto.FoodLogRequest;
import com.pantsinc.FoodReactionTracker.entity.FoodLog;
import com.pantsinc.FoodReactionTracker.entity.User;
import com.pantsinc.FoodReactionTracker.exception.ValidationException;
import com.pantsinc.FoodReactionTracker.repository.FoodLogRepository;
import com.pantsinc.FoodReactionTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FoodLogService {

    private final UserRepository userRepository;

    private final FoodLogRepository foodLogRepository;

    private final ObjectMapper objectMapper;

    public List<FoodLog> getAllForUser(String userEmail, boolean includeDeleted) throws ValidationException {

        Optional<User> user = userRepository.findByEmail(userEmail);

        return foodLogRepository.getAllByUser(user.orElseThrow(() -> new ValidationException("User Not Found")));
    }

    public Long addLog(String userEmail, FoodLogRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ValidationException("User Not Found"));

        FoodLog foodLog = objectMapper.convertValue(request, FoodLog.class);
        foodLog.setUser(user);

        FoodLog saved = foodLogRepository.save(foodLog);

        return saved.getId();
    }

    public void deleteLog(Long logId){
        FoodLog foodLog = foodLogRepository.findById(logId).orElseThrow(() -> new ValidationException("Food Log Not Found"));

        foodLog.setDeleted(true);

        foodLogRepository.save(foodLog);
    }
}
