package com.example.accessinsightsbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LintFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @Column(columnDefinition = "CLOB") // Use CLOB for large text/JSON
    private String fileReportJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lint_report_id")
    private LintReport lintReport;
}
