package com.ego.doan_ego.controller;

import com.ego.doan_ego.request.user_exam.EditOrAddUserExamRequest;
import com.ego.doan_ego.request.user_exam.UserExamRequest;
import com.ego.doan_ego.service.interfaceService.UserExamService;
import com.ego.doan_ego.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-exams")
@CrossOrigin
@RequiredArgsConstructor
public class UserExamController implements IBaseController<UserExamRequest, EditOrAddUserExamRequest, Long> {

    private final UserExamService userExamService;

    @PostMapping
    @Override
    public ResponseEntity<?> add(@RequestBody EditOrAddUserExamRequest entity) {
        try {
            return userExamService.store(entity);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> edit(@RequestBody EditOrAddUserExamRequest entity, @PathVariable(name = "id") Long aLong) {
        try {
            return userExamService.update(entity, aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long aLong) {
        try {
            return userExamService.destroy(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return userExamService.getAll(pageable);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long aLong) {
        try {
            return userExamService.getById(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/by-exam/{id}")
    public ResponseEntity<?> getByExam(@PathVariable(name = "id") Long examId, Pageable pageable) {
        try {
            return userExamService.getByExam(pageable, examId);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<?> getByUser(@PathVariable(name = "id") Long userId, Pageable pageable) {
        try {
            return userExamService.getByUser(pageable, userId);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }
}
