package com.example.accessinsightsbackend.repository;

import com.example.accessinsightsbackend.model.LintFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LintFileRepository extends JpaRepository<LintFile, Long> {
}
