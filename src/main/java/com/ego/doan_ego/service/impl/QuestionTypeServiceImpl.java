package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.entities.Question;
import com.ego.doan_ego.entities.QuestionType;
import com.ego.doan_ego.repository.QuestionRepository;
import com.ego.doan_ego.repository.QuestionTypeRepository;
import com.ego.doan_ego.request.question_type.EditOrAddQuestionTypeRequest;
import com.ego.doan_ego.service.interfaceService.QuestionTypeService;
import com.ego.doan_ego.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class QuestionTypeServiceImpl implements QuestionTypeService {

    private final QuestionTypeRepository questionTypeRepository;

    private final QuestionRepository questionRepository;

    @Override
    public ResponseEntity<?> store(EditOrAddQuestionTypeRequest entity) throws Exception {
        return ApiResponse.success(questionTypeRepository.save(new ModelMapper().map(entity, QuestionType.class)));
    }

    @Override
    public ResponseEntity<?> update(EditOrAddQuestionTypeRequest entity, Long aLong) throws Exception {
        QuestionType questionType = questionTypeRepository.findById(aLong).orElseThrow(() -> new Exception("Question type not found by id" + aLong));
        BeanUtils.copyProperties(entity, questionType);
        return ApiResponse.success(questionTypeRepository.save(questionType));
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        QuestionType questionType = questionTypeRepository.findById(aLong).orElseThrow(() -> new Exception("Question type not found by id" + aLong));
        List<Question> questions = questionRepository.findAllByQuestionType(questionType);
        questions.forEach((q) -> {
            q.setQuestionType(null);
            questionRepository.save(q);
        });
        questionTypeRepository.deleteById(aLong);
        return ApiResponse.success();
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable) throws Exception {
        return ApiResponse.success(questionTypeRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> getById(Long aLong) throws Exception {
        return ApiResponse.success(questionTypeRepository.findById(aLong).orElseThrow(() -> new Exception("Question type not found by id" + aLong)));
    }
}
