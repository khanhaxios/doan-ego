package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.Answer;
import com.ego.doan_ego.entities.Question;
import com.ego.doan_ego.entities.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByAnswer(Answer answer);
    List<Question> findAllByQuestionType(QuestionType questionType);
}
