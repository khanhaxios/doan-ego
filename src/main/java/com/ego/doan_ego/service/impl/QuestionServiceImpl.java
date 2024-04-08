package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.entities.Answer;
import com.ego.doan_ego.entities.Question;
import com.ego.doan_ego.repository.AnswerRepository;
import com.ego.doan_ego.repository.QuestionRepository;
import com.ego.doan_ego.repository.QuestionTypeRepository;
import com.ego.doan_ego.request.question.EditOrAddQuestionRequest;
import com.ego.doan_ego.service.interfaceService.AnswerService;
import com.ego.doan_ego.service.interfaceService.QuestionService;
import com.ego.doan_ego.utils.ApiResponse;
import com.ego.doan_ego.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionTypeRepository questionTypeRepository;

    private final AnswerRepository answerRepository;

    @Override
    public ResponseEntity<?> store(EditOrAddQuestionRequest entity) throws Exception {
        Question question = new ModelMapper().map(entity, Question.class);
        if (entity.getQuestionTypeId() != null) {
            question.setQuestionType(questionTypeRepository.findById(entity.getQuestionTypeId()).orElseThrow(() -> new Exception("Question type not found")));
        }
        if (entity.getAnswerId() != null) {
            Answer answer = answerRepository.findById(entity.getAnswerId()).orElseThrow(() -> new Exception("Answer not found"));
            Question ex = questionRepository.findByAnswer(answer).orElse(null);
            if (ex != null) {
                throw new Exception("Answer has exit on other question");
            }
            question.setAnswer(answer);
        }
        return editOrAddChildQuestion(entity, question);
    }

    @Override
    public ResponseEntity<?> update(EditOrAddQuestionRequest entity, Long aLong) throws Exception {
        Question question = questionRepository.findById(aLong).orElseThrow(() -> new Exception("Question not found"));
        CustomBeanUtils.copyNonNullProperties(entity, question);
        if (entity.getQuestionTypeId() != null) {
            question.setQuestionType(questionTypeRepository.findById(entity.getQuestionTypeId()).orElse(null));
        }
        if (entity.getAnswerId() != null) {
            question.setAnswer(answerRepository.findById(entity.getAnswerId()).orElseThrow(() -> new Exception("Answer not found")));
        }
        return editOrAddChildQuestion(entity, question);
    }

    private ResponseEntity<?> editOrAddChildQuestion(EditOrAddQuestionRequest entity, Question question) {
        Question savedQues = questionRepository.save(question);
        if (entity.getChildQuestionId().size() > 0) {
            question.getChildrenQuestion().clear();
            entity.getChildQuestionId().forEach((c) -> {
                if (!Objects.equals(c, savedQues.getId())) {
                    question.getChildrenQuestion().add(questionRepository.findById(c).orElse(null));
                }
            });
        }
        return ApiResponse.success(savedQues);
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        Question question = questionRepository.findById(aLong).orElseThrow(() -> new Exception("Question not found"));
        Answer answer = question.getAnswer();
        //remove question child
        List<Question> children = question.getChildrenQuestion();
        if (children.size() > 0) {
            question.setChildrenQuestion(new ArrayList<>());
            questionRepository.save(question);
        }
        questionRepository.delete(question);
        if (answer != null) {
            answerRepository.deleteById(answer.getId());
        }
        return ApiResponse.success();
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable) throws Exception {
        return ApiResponse.success(questionRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> getById(Long aLong) throws Exception {
        return ApiResponse.success(questionRepository.findById(aLong).orElseThrow(() -> new Exception("Question not found")));
    }

    @Override
    public ResponseEntity<?> getByType(Long typeId) throws Exception {
        return ApiResponse.success(questionRepository.findAllByQuestionType(questionTypeRepository.findById(typeId).orElseThrow(() -> new Exception("Question type not found by id " + typeId))));
    }
}
