package com.example.accessinsightsbackend.service;

import com.example.accessinsightsbackend.dto.jira.JiraCreateIssueRequest;
import com.example.accessinsightsbackend.dto.jira.JiraCreateIssueResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


import java.io.Console;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JiraService {

    private final WebClient webClient;

    public JiraService(@Value("${jira.api.base-url}") String jiraApiBaseUrl,
                       @Value("${jira.api.email}") String jiraApiEmail,
                       @Value("${jira.api.token}") String jiraApiToken) {
        String auth = jiraApiEmail + ":" + jiraApiToken;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpClient httpClient = HttpClient.create().followRedirect(true);


        this.webClient = WebClient.builder()
                .baseUrl(jiraApiBaseUrl + "/rest/api/3") // Append the API version to the base URL
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public Mono<JiraCreateIssueResponse> createJiraIssue(JiraCreateIssueRequest request) {
        System.out.println("Creating Jira issue with request: " + request.toString());
         Map<String, Object> requestBody = Map.of(
                "fields", Map.of(
                        "project", Map.of("key", request.getFields().getProject().get("key")),
                        "summary", request.getFields().getSummary(),
                        "description", Map.of(
                                "type", "doc",
                                "version", 1,
                                "content", request.getFields().getDescription()
                        ),
                        "issuetype", Map.of("name", request.getFields().getIssuetype().get("name"))
                )
        );

        return webClient.post()
                .uri("/issue") // The URI is appended to the WebClient's base URL
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JiraCreateIssueResponse.class); // Specify the response DTO
    }
    
}
