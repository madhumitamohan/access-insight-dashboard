package com.example.accessinsightsbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LintMessageDTO {
    private String ruleId;
    private Integer severity;
    private String message;
    private Integer line;
    private Integer column;
    private String nodeType;
}
