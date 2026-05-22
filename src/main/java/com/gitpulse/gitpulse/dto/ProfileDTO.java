package com.gitpulse.gitpulse.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProfileDTO {
    private String username;
    private String name;
    private String bio;
    private String avatarUrl;
    private String githubUrl;
    private int totalRepos;
    private int followers;
    private int totalStars;
    private int totalForks;
    private List<RepoDTO> topRepos;
    private Map<String, Double> languagePercentages;
}