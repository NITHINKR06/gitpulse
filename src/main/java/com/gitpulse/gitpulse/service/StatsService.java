package com.gitpulse.gitpulse.service;

import com.gitpulse.gitpulse.dto.RepoDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    public int totalStars(List<RepoDTO> repos) {
        return repos.stream()
                .mapToInt(RepoDTO::getStargazers_count)
                .sum();
    }

    public int totalForks(List<RepoDTO> repos) {
        return repos.stream()
                .mapToInt(RepoDTO::getForks_count)
                .sum();
    }

    public String mostUsedLanguage(List<RepoDTO> repos) {
        return repos.stream()
                .filter(r -> r.getLanguage() != null)
                .collect(Collectors.groupingBy(RepoDTO::getLanguage, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public int originalRepoCount(List<RepoDTO> repos) {
        return (int) repos.stream()
                .filter(r -> !r.isFork())
                .count();
    }

    public List<RepoDTO> topRepos(List<RepoDTO> repos) {
        return repos.stream()
                .filter(r -> !r.isFork())
                .sorted(Comparator.comparingInt(RepoDTO::getStargazers_count).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public Map<String, Double> languagePercentages(List<RepoDTO> repos) {
        Map<String, Long> counts = repos.stream()
                .filter(r -> r.getLanguage() != null)
                .collect(Collectors.groupingBy(RepoDTO::getLanguage, Collectors.counting()));

        long total = counts.values().stream().mapToLong(Long::longValue).sum();

        if (total == 0) return Collections.emptyMap();

        return counts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Math.round((e.getValue() * 100.0 / total) * 10) / 10.0
                ));
    }
}