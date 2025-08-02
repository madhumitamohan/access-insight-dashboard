package com.example.accessinsightsbackend.repository;

import com.example.accessinsightsbackend.model.LintReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LintReportRepository extends JpaRepository<LintReport, Long> {
}
