package com.pantsinc.FoodReactionTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pantsinc.FoodReactionTracker")
public class FoodReactionTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodReactionTrackerApplication.class, args);
	}
}
