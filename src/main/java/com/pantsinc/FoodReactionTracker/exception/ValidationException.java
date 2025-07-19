package com.pantsinc.FoodReactionTracker.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationException extends RuntimeException {
    String message;
}
