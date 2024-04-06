package com.ego.doan_ego.service.interfaceService;

import com.ego.doan_ego.request.user_exam.EditOrAddUserExamRequest;
import com.ego.doan_ego.request.user_exam.UserExamRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserExamService extends IBaseService<UserExamRequest, EditOrAddUserExamRequest, Long> {
    ResponseEntity<?> getByExam(Pageable pageable, Long examId) throws Exception;

    ResponseEntity<?> getByUser(Pageable pageable, Long userId) throws Exception;
}
