package com.example.accessinsightsbackend.dto;

public class LintMessageDTO {
    private String ruleId;
    private Integer severity;
    private String message;
    private Integer line;
    private Integer column;
    private String nodeType;

    public LintMessageDTO() {
    }

    public LintMessageDTO(String ruleId, Integer severity, String message, Integer line, Integer column, String nodeType) {
        this.ruleId = ruleId;
        this.severity = severity;
        this.message = message;
        this.line = line;
        this.column = column;
        this.nodeType = nodeType;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}