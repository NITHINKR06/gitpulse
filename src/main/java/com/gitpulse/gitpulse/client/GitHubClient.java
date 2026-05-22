package com.gitpulse.gitpulse.client;

import com.gitpulse.gitpulse.dto.GithubUserDTO;
import com.gitpulse.gitpulse.dto.RepoDTO;
import com.gitpulse.gitpulse.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final WebClient gitHubWebClient;

    public GithubUserDTO getUser(String username) {
        return gitHubWebClient.get()
                .uri("/users/{username}", username)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        resp -> Mono.error(new UserNotFoundException(username)))
                .bodyToMono(GithubUserDTO.class)
                .block();
    }

    public List<RepoDTO> getRepos(String username) {
        return gitHubWebClient.get()
                .uri("/users/{username}/repos?per_page=100&sort=updated", username)
                .retrieve()
                .bodyToFlux(RepoDTO.class)
                .collectList()
                .block();
    }
}