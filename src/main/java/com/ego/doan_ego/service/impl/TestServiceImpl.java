package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.entities.Question;
import com.ego.doan_ego.entities.Test;
import com.ego.doan_ego.repository.QuestionRepository;
import com.ego.doan_ego.repository.TestRepository;
import com.ego.doan_ego.request.test.EditOrAddTestRequest;
import com.ego.doan_ego.service.interfaceService.TestService;
import com.ego.doan_ego.utils.ApiResponse;
import com.ego.doan_ego.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    @Override
    public ResponseEntity<?> store(EditOrAddTestRequest entity) throws Exception {
        Test test = new ModelMapper().map(entity, Test.class);
        return processTestQuestions(entity, test);
    }

    @Override
    public ResponseEntity<?> update(EditOrAddTestRequest entity, String aLong) throws Exception {
        entity.setId(null);
        Test test = testRepository.findById(aLong).orElseThrow(() -> new Exception("Test not found by id : " + aLong));
        CustomBeanUtils.copyNonNullProperties(entity, test);
        return processTestQuestions(entity, test);
    }

    private ResponseEntity<?> processTestQuestions(EditOrAddTestRequest entity, Test test) {
        if (entity.getQuestionIds() != null) {
            Set<Question> questions = new HashSet<>();
            for (Long questionId : entity.getQuestionIds()) {
                questions.add(questionRepository.findById(questionId).orElse(null));
            }
            test.setQuestions(questions);
        }
        return ApiResponse.success(testRepository.save(test));
    }

    @Override
    public ResponseEntity<?> destroy(String aLong) throws Exception {
        testRepository.deleteById(aLong);
        return ApiResponse.success();
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable) throws Exception {
        return ApiResponse.success(testRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> getById(String aLong) throws Exception {
        return ApiResponse.success(testRepository.findById(aLong).orElseThrow(() -> new Exception("Test not found by id : " + aLong)));
    }
}
