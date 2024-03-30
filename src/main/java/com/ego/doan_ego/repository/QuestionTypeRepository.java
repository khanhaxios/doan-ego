package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.Question;
import com.ego.doan_ego.entities.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
