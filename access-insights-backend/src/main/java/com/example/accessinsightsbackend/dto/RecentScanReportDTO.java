package com.example.accessinsightsbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentScanReportDTO {
    private LocalDateTime timestamp;
    private List<RecentScanFileDTO> files;
}
