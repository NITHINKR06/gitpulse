package com.gitpulse.gitpulse.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoDTO {
    private String name;
    private String description;
    private String html_url;
    private String language;
    private int stargazers_count;
    private int forks_count;
    private boolean fork;
}