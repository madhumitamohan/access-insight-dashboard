package com.example.accessinsightsbackend.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class LintReportRequest {
    private String projectName;
    private String projectId;
    private JsonNode report;

    // Getters and setters
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public JsonNode getReport() {
        return report;
    }

    public void setReport(JsonNode report) {
        this.report = report;
    }
}
