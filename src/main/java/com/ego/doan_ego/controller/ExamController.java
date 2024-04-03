package com.ego.doan_ego.controller;

import com.ego.doan_ego.request.exam.EditOrAddExamRequest;
import com.ego.doan_ego.request.exam.ExamRequest;
import com.ego.doan_ego.service.interfaceService.ExamService;
import com.ego.doan_ego.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasRole('ROLE_TEACHER')")
public class ExamController implements IBaseController<ExamRequest, EditOrAddExamRequest, Long> {
    private final ExamService examService;

    @PostMapping
    @Override
    public ResponseEntity<?> add(@RequestBody EditOrAddExamRequest entity) {
        try {
            return examService.store(entity);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> edit(@RequestBody EditOrAddExamRequest entity, @PathVariable(name = "id") Long aLong) {
        try {
            return examService.update(entity, aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long aLong) {
        try {
            return examService.destroy(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return examService.getAll(pageable);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long aLong) {
        try {
            return examService.getById(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }
}
