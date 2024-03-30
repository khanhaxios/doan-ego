package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.entities.Answer;
import com.ego.doan_ego.entities.Question;
import com.ego.doan_ego.repository.AnswerRepository;
import com.ego.doan_ego.repository.QuestionRepository;
import com.ego.doan_ego.request.answer.EditOrAddAnswerRequest;
import com.ego.doan_ego.service.interfaceService.AnswerService;
import com.ego.doan_ego.utils.ApiResponse;
import com.ego.doan_ego.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    protected Answer findById(Long id) throws Exception {
        return answerRepository.findById(id).orElseThrow(() -> new Exception("Answer not found"));
    }

    @Override
    public ResponseEntity<?> store(EditOrAddAnswerRequest entity) throws Exception {
        Answer answer = new ModelMapper().map(entity, Answer.class);
        return ApiResponse.success(answerRepository.save(answer));
    }

    @Override
    public ResponseEntity<?> update(EditOrAddAnswerRequest entity, Long aLong) throws Exception {
        Answer answer = findById(aLong);
        CustomBeanUtils.copyNonNullProperties(entity, answer);
        return ApiResponse.success(answerRepository.save(answer));
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        Question question = questionRepository.findByAnswer(findById(aLong)).orElse(null);
        if (question != null) {
            question.setAnswer(null);
            questionRepository.save(question);
        }
        answerRepository.deleteById(aLong);
        return ApiResponse.success();
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable) throws Exception {
        return ApiResponse.success(answerRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> getById(Long aLong) throws Exception {
        return ApiResponse.success(findById(aLong));
    }
}
