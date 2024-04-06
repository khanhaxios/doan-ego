package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.Exam;
import com.ego.doan_ego.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByTests(List<Test> tests);
}
