package com.pantsinc.FoodReactionTracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok("Hello, " + userEmail + "! This is a secured endpoint.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/secret")
    public ResponseEntity<String> secretAdminStuff() {
        return ResponseEntity.ok("Welcome, boss.");
    }

}
