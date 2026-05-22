package com.gitpulse.gitpulse.controller;

import com.gitpulse.gitpulse.dto.ProfileDTO;
import com.gitpulse.gitpulse.exception.UserNotFoundException;
import com.gitpulse.gitpulse.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final GitHubService gitHubService;

    // full profile JSON
    @GetMapping("/profile/{username}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable String username) {
        return ResponseEntity.ok(gitHubService.buildProfile(username));
    }

    // stats only
    @GetMapping("/profile/{username}/stats")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable String username) {
        ProfileDTO p = gitHubService.buildProfile(username);
        return ResponseEntity.ok(Map.of(
                "totalStars", p.getTotalStars(),
                "totalForks", p.getTotalForks(),
                "totalRepos", p.getTotalRepos(),
                "followers",  p.getFollowers()
        ));
    }

    // languages only
    @GetMapping("/profile/{username}/languages")
    public ResponseEntity<Map<String, Double>> getLanguages(@PathVariable String username) {
        return ResponseEntity.ok(
                gitHubService.buildProfile(username).getLanguagePercentages()
        );
    }

    // top repos only
    @GetMapping("/profile/{username}/repos")
    public ResponseEntity<?> getRepos(@PathVariable String username) {
        return ResponseEntity.ok(
                gitHubService.buildProfile(username).getTopRepos()
        );
    }

    // error handler
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(Map.of("error", ex.getMessage()));
    }
}