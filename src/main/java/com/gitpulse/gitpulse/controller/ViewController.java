package com.gitpulse.gitpulse.controller;

import com.gitpulse.gitpulse.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final GitHubService gitHubService;

    @GetMapping("/profile/{username}")
    public String profilePage(@PathVariable String username, Model model) {
        model.addAttribute("profile", gitHubService.buildProfile(username));
        return "profile";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}