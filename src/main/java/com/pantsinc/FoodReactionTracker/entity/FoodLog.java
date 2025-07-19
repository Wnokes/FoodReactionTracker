package com.pantsinc.FoodReactionTracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "food_log")
public class FoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String foodType;
    private Date timeEaten;
    private Long quantity;
    private String quantityUnit;
    private String note;
    private boolean isDeleted;
}
