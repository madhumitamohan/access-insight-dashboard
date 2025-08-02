package com.example.accessinsightsbackend.model;

import jakarta.persistence.*;

@Entity
public class LintFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @Column(columnDefinition = "CLOB")
    private String fileReportJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lint_report_id")
    private LintReport lintReport;

    public LintFile() {
    }

    public LintFile(Long id, String filePath, String fileReportJson, LintReport lintReport) {
        this.id = id;
        this.filePath = filePath;
        this.fileReportJson = fileReportJson;
        this.lintReport = lintReport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileReportJson() {
        return fileReportJson;
    }

    public void setFileReportJson(String fileReportJson) {
        this.fileReportJson = fileReportJson;
    }

    public LintReport getLintReport() {
        return lintReport;
    }

    public void setLintReport(LintReport lintReport) {
        this.lintReport = lintReport;
    }
}