package com.example.accessinsightsbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LintFileContentDTO {
    private String filePath;
    private List<LintMessageDTO> messages;
    private Integer errorCount;
    private Integer warningCount;
    private Integer fixableErrorCount;
    private Integer fixableWarningCount;
    private String source; // Assuming 'source' is a string
}
