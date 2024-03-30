package com.ego.doan_ego.controller;

import com.ego.doan_ego.request.question_type.EditOrAddQuestionTypeRequest;
import com.ego.doan_ego.request.question_type.QuestionTypeRequest;
import com.ego.doan_ego.service.interfaceService.QuestionTypeService;
import com.ego.doan_ego.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question-types")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_TEACHER')")
public class QuestionTypeController implements IBaseController<QuestionTypeRequest, EditOrAddQuestionTypeRequest, Long> {

    private final QuestionTypeService questionTypeService;

    @PostMapping
    @Override
    public ResponseEntity<?> add(@RequestBody EditOrAddQuestionTypeRequest entity) {
        try {
            return questionTypeService.store(entity);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> edit(@RequestBody EditOrAddQuestionTypeRequest entity, @PathVariable(name = "id") Long aLong) {
        try {
            return questionTypeService.update(entity, aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long aLong) {
        try {
            return questionTypeService.destroy(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return questionTypeService.getAll(pageable);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long aLong) {
        try {
            return questionTypeService.getById(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }
}
