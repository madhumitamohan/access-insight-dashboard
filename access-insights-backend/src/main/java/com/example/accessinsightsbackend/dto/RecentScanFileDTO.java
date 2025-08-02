package com.example.accessinsightsbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentScanFileDTO {
    private String filePath;
    private LintFileContentDTO fileReport;
}