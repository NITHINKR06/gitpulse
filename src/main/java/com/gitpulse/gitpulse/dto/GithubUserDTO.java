package com.gitpulse.gitpulse.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUserDTO {
    private String login;
    private String name;
    private String bio;
    private String avatar_url;
    private String html_url;
    private int public_repos;
    private int followers;
    private int following;
}