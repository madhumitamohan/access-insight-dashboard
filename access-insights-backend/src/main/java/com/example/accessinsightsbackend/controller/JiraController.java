package com.example.accessinsightsbackend.controller;

import com.example.accessinsightsbackend.dto.jira.JiraCreateIssueRequest;
import com.example.accessinsightsbackend.dto.jira.JiraCreateIssueResponse;
import com.example.accessinsightsbackend.service.JiraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/jira")
public class JiraController {

    private final JiraService jiraService;

    public JiraController(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    @PostMapping("/create-issue")
    public Mono<ResponseEntity<JiraCreateIssueResponse>> createJiraIssue(@RequestBody JiraCreateIssueRequest request) {
        System.out.println("Received request to create Jira issue: " +
                request.getFields().getDescription() +
                request.getFields().getIssuetype().get("name") +
                request.getFields().getProject().get("key") +
                request.getFields().getSummary());
        return jiraService.createJiraIssue(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
