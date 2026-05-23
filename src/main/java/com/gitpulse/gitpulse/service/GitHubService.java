package com.gitpulse.gitpulse.service;

import com.gitpulse.gitpulse.client.GitHubClient;
import com.gitpulse.gitpulse.dto.GithubUserDTO;
import com.gitpulse.gitpulse.dto.ProfileDTO;
import com.gitpulse.gitpulse.dto.RepoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;
    private final StatsService statsService;

    @Async
    public CompletableFuture<List<RepoDTO>> fetchReposAsync(String username) {
        return CompletableFuture.completedFuture(gitHubClient.getRepos(username));
    }
    @Cacheable(value = "profiles", key = "#username")
    public ProfileDTO buildProfile(String username) {
        GithubUserDTO user = gitHubClient.getUser(username);
        CompletableFuture<List<RepoDTO>> reposFuture = fetchReposAsync(username);

        List<RepoDTO> repos = reposFuture.join();

        return ProfileDTO.builder()
                .username(user.getLogin())
                .name(user.getName())
                .bio(user.getBio())
                .avatarUrl(user.getAvatar_url())
                .githubUrl(user.getHtml_url())
                .totalRepos(user.getPublic_repos())
                .followers(user.getFollowers())
                .totalStars(statsService.totalStars(repos))
                .totalForks(statsService.totalForks(repos))
                .topRepos(statsService.topRepos(repos))
                .languagePercentages(statsService.languagePercentages(repos))
                .mostUsedLanguage(statsService.mostUsedLanguage(repos))  // add this
                .originalRepos(statsService.originalRepoCount(repos))    // add this
                .build();
    }
}