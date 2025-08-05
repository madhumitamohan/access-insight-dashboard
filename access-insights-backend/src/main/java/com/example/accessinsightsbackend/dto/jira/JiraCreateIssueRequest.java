package com.example.accessinsightsbackend.dto.jira;

import java.util.Map;

public class JiraCreateIssueRequest {
    private Fields fields;

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public static class Fields {
        private Map<String, String> project;
        private String summary;
        private String description;
        private Map<String, String> issuetype;

        public Map<String, String> getProject() {
            return project;
        }

        public void setProject(Map<String, String> project) {
            this.project = project;
        }

        public String getSummary() {
            return summary;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, String> getIssuetype() {
            return issuetype;
        }

        public void setIssuetype(Map<String, String> issuetype) {
            this.issuetype = issuetype;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}