package com.gitpulse.gitpulse.service;

import com.gitpulse.gitpulse.client.GitHubClient;
import com.gitpulse.gitpulse.dto.GithubUserDTO;
import com.gitpulse.gitpulse.dto.ProfileDTO;
import com.gitpulse.gitpulse.dto.RepoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;
    private final StatsService statsService;

    public ProfileDTO buildProfile(String username) {
        GithubUserDTO user = gitHubClient.getUser(username);
        List<RepoDTO> repos = gitHubClient.getRepos(username);

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
                .build();
    }
}