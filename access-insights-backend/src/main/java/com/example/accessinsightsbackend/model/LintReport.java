package com.example.accessinsightsbackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
public class LintReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "lintReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LintFile> files = new ArrayList<>();

    public LintReport() {
    }

    public LintReport(Long id, LocalDateTime timestamp, List<LintFile> files) {
        this.id = id;
        this.timestamp = timestamp;
        this.files = files;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<LintFile> getFiles() {
        return files;
    }

    public void setFiles(List<LintFile> files) {
        this.files = files;
    }
}