package com.ego.doan_ego.controller;

import com.ego.doan_ego.request.answer.AnswerRequest;
import com.ego.doan_ego.request.answer.EditOrAddAnswerRequest;
import com.ego.doan_ego.service.interfaceService.AnswerService;
import com.ego.doan_ego.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/answers")
@Tag(name = "Answer")
@PreAuthorize("hasRole('ROLE_TEACHER')")
public class AnswerController implements IBaseController<AnswerRequest, EditOrAddAnswerRequest, Long> {

    private final AnswerService answerService;

    @PostMapping
    @Override
    public ResponseEntity<?> add(@RequestBody EditOrAddAnswerRequest entity) {
        try {
            return answerService.store(entity);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> edit(@RequestBody EditOrAddAnswerRequest entity, @PathVariable(name = "id") Long aLong) {
        try {
            return answerService.update(entity, aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long aLong) {
        try {
            return answerService.destroy(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return answerService.getAll(pageable);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long aLong) {
        try {
            return answerService.getById(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }
}
