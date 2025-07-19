package com.pantsinc.FoodReactionTracker.repository;

import com.pantsinc.FoodReactionTracker.entity.FoodLog;
import com.pantsinc.FoodReactionTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodLogRepository extends JpaRepository<FoodLog, Long> {
    List<FoodLog> getAllByUser(User user);
}
