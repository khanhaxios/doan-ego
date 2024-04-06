package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.constant.UserExamStatus;
import com.ego.doan_ego.entities.*;
import com.ego.doan_ego.repository.*;
import com.ego.doan_ego.request.answer.EditOrAddAnswerRequest;
import com.ego.doan_ego.request.user_exam.EditOrAddUserExamRequest;
import com.ego.doan_ego.response.AnswerResp;
import com.ego.doan_ego.response.UserExamResp;
import com.ego.doan_ego.service.interfaceService.UserExamService;
import com.ego.doan_ego.utils.ApiResponse;
import com.ego.doan_ego.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserExamServiceImpl implements UserExamService {

    private final UserExamRepository userExamRepository;

    private final UserRepository userRepository;

    private final ExamRepository examRepository;
    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    @Override
    public ResponseEntity<?> store(EditOrAddUserExamRequest entity) throws Exception {
        UserExam userExam = new UserExam();
        UserDao user = userRepository.findById(entity.getUserId()).orElseThrow(() -> new Exception("User not found by id " + entity.getUserId()));
        Exam exam = examRepository.findById(entity.getExamId()).orElseThrow(() -> new Exception("Exam not found by id " + entity.getExamId()));
        // check exam time
        userExam.setPoint(entity.getPoint());
        userExam.setStatus(entity.getStatus() != null ? entity.getStatus() : UserExamStatus.DONE);
        Instant instant = Instant.ofEpochMilli(entity.getTimeEnd());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        if (exam.getTimeEnd().isBefore(zonedDateTime.toLocalDateTime())) {
            return ApiResponse.fallback("Exam is end.");
        }
        userExam.setExam(exam);
        userExam.setUserDao(user);
        return processRelation(entity, userExam);
    }

    private ResponseEntity<?> processRelation(EditOrAddUserExamRequest editOrAddUserExamRequest, UserExam userExam) {
        if (editOrAddUserExamRequest.getAnswerRequests() != null) {
            List<EditOrAddAnswerRequest> editOrAddAnswerRequests = editOrAddUserExamRequest.getAnswerRequests();
            userExam.clear();
            for (EditOrAddAnswerRequest request : editOrAddAnswerRequests) {
                Answer answer = new ModelMapper().map(request, Answer.class);
                userExam.addAnswer(answerRepository.save(answer));
            }
        }
        return ApiResponse.success(userExamRepository.save(userExam));
    }

    @Override
    public ResponseEntity<?> update(EditOrAddUserExamRequest entity, Long aLong) throws Exception {
        UserExam userExam = userExamRepository.findById(aLong).orElseThrow(() -> new Exception("User Exam not found by id " + aLong));
        CustomBeanUtils.copyNonNullProperties(entity, userExam);
        if (entity.getUserId() != null) {
            UserDao user = userRepository.findById(entity.getUserId()).orElseThrow(() -> new Exception("User not found by id " + entity.getUserId()));
            userExam.setUserDao(user);
        }
        if (entity.getExamId() != null) {
            Exam exam = examRepository.findById(entity.getExamId()).orElseThrow(() -> new Exception("Exam not found by id " + entity.getExamId()));
            userExam.setExam(exam);
        }
        return processRelation(entity, userExam);
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) throws Exception {
        UserExam userExam = userExamRepository.findById(aLong).orElseThrow(() -> new Exception("User Exam not found by id " + aLong));
        userExam.setStatus(UserExamStatus.CANCEL);
        userExamRepository.save(userExam);
        return ApiResponse.success();
    }


    @Override
    public ResponseEntity<?> getAll(Pageable pageable) throws Exception {
        Page<UserExam> userExamPage = userExamRepository.findAll(pageable);
        List<UserExamResp> userExamResps = userExamPage.getContent().stream().map((ue) -> {
            if (ue.getStatus().equals(UserExamStatus.DONE)) {
                return mapAnswer(ue);
            }
            return null;
        }).toList();
        return ApiResponse.success(new PageImpl<>(userExamResps, pageable, userExamPage.getTotalElements()));
    }

    private UserExamResp mapAnswer(UserExam ue) {
        UserExamResp resp = new ModelMapper().map(ue, UserExamResp.class);
        resp.setAnswerResps(ue.getAnswers().stream().map((as) -> {
            Question question = questionRepository.findById(as.getQuestionId()).orElse(null);
            AnswerResp answerResp = new ModelMapper().map(as, AnswerResp.class);
            answerResp.setQuestion(question);
            return answerResp;
        }).collect(Collectors.toList()));
        return resp;
    }

    @Override
    public ResponseEntity<?> getById(Long aLong) throws Exception {
        UserExam userExam = userExamRepository.findById(aLong).orElseThrow(() -> new Exception("User Exam not found by id" + aLong));
        if (userExam.getStatus().equals(UserExamStatus.CANCEL)) {
            return ApiResponse.fallback("User exam not found by id " + aLong);
        }
        return ApiResponse.success(mapAnswer(userExam));
    }

    @Override
    public ResponseEntity<?> getByExam(Pageable pageable, Long examId) throws Exception {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new Exception("Exam not found by id " + examId));
        Page<UserExam> userExamPage = userExamRepository.findAllByExam(pageable, exam);
        return getByResp(pageable, userExamPage);
    }

    @Override
    public ResponseEntity<?> getByUser(Pageable pageable, Long userId) throws Exception {
        UserDao userDao = userRepository.findById(userId).orElseThrow(() -> new Exception("User Dao not found by id " + userId));
        Page<UserExam> userExamPage = userExamRepository.findAllByUserDao(pageable, userDao);
        return getByResp(pageable, userExamPage);
    }

    private ResponseEntity<?> getByResp(Pageable pageable, Page<UserExam> userExamPage) {
        List<UserExamResp> userExamResps = userExamPage.getContent().stream().map((ue) -> {
            if (ue.getStatus().equals(UserExamStatus.DONE)) {
                return mapAnswer(ue);
            }
            return null;
        }).collect(Collectors.toList());
        return ApiResponse.success(new PageImpl<>(userExamResps, pageable, userExamPage.getTotalElements()));
    }
}