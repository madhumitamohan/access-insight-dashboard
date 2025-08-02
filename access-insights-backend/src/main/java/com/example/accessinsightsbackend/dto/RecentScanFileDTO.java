package com.example.accessinsightsbackend.dto;

import java.util.List;

public class RecentScanFileDTO {
    private String filePath;
    private List<LintMessageDTO> messages;
    private Integer errorCount;
    private Integer warningCount;
    private Integer fixableErrorCount;
    private Integer fixableWarningCount;
    private String source;

    public RecentScanFileDTO() {
    }

    public RecentScanFileDTO(String filePath, List<LintMessageDTO> messages, Integer errorCount, Integer warningCount, Integer fixableErrorCount, Integer fixableWarningCount, String source) {
        this.filePath = filePath;
        this.messages = messages;
        this.errorCount = errorCount;
        this.warningCount = warningCount;
        this.fixableErrorCount = fixableErrorCount;
        this.fixableWarningCount = fixableWarningCount;
        this.source = source;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<LintMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<LintMessageDTO> messages) {
        this.messages = messages;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(Integer warningCount) {
        this.warningCount = warningCount;
    }

    public Integer getFixableErrorCount() {
        return fixableErrorCount;
    }

    public void setFixableErrorCount(Integer fixableErrorCount) {
        this.fixableErrorCount = fixableErrorCount;
    }

    public Integer getFixableWarningCount() {
        return fixableWarningCount;
    }

    public void setFixableWarningCount(Integer fixableWarningCount) {
        this.fixableWarningCount = fixableWarningCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
