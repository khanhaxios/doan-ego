package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.entities.Exam;
import com.ego.doan_ego.entities.Test;
import com.ego.doan_ego.repository.ExamRepository;
import com.ego.doan_ego.repository.TestRepository;
import com.ego.doan_ego.request.exam.EditOrAddExamRequest;
import com.ego.doan_ego.service.interfaceService.ExamService;
import com.ego.doan_ego.utils.ApiResponse;
import com.ego.doan_ego.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final TestRepository testRepository;
    private final ExamRepository examRepository;

    @Override
    public ResponseEntity<?> store(EditOrAddExamRequest entity) throws Exception {
        Exam exam = new ModelMapper().map(entity, Exam.class);
        Instant instant = Instant.ofEpochMilli(entity.getStartTime());
        Instant instant1 = Instant.ofEpochMilli(entity.getEndTime());
        exam.setTimeStart(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDateTime());
        exam.setTimeEnd(ZonedDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalDateTime());
        return processExamRelation(entity, examRepository.save(exam));
    }

    private ResponseEntity<?> processExamRelation(EditOrAddExamRequest request, Exam exam) {
        if (request.getTestIds() != null) {
            Set<String> testIds = request.getTestIds();
            List<Test> tests = new ArrayList<>();
            for (String id : testIds) {
                tests.add(testRepository.findById(id).orElse(null));
            }
            exam.setTests(tests);
        }
        return ApiResponse.success(examRepository.save(exam));
    }

    @Override
    public ResponseEntity<?> update(EditOrAddExamRequest entity, Long aLong) throws Exception {
        Exam exam = examRepository.findById(aLong).orElseThrow(() -> new Exception("Exam not found by id " + aLong));
        CustomBeanUtils.copyNonNullProperties(entity, exam);
        return processExamRelation(entity, exam);
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        examRepository.deleteById(aLong);
        return ApiResponse.success();
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable) throws Exception {
        return ApiResponse.success(examRepository.findAll(pageable));
    }

    @Override
    public ResponseEntity<?> getById(Long aLong) throws Exception {
        return ApiResponse.success(examRepository.findById(aLong).orElseThrow(() -> new Exception("Exam not found by id " + aLong)));
    }
}
