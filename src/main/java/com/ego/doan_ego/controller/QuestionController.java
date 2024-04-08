package com.ego.doan_ego.controller;

import com.ego.doan_ego.entities.Question;
import com.ego.doan_ego.entities.QuestionType;
import com.ego.doan_ego.request.question.EditOrAddQuestionRequest;
import com.ego.doan_ego.request.question.QuestionRequest;
import com.ego.doan_ego.service.interfaceService.QuestionService;
import com.ego.doan_ego.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Question API")
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController implements IBaseController<QuestionRequest, EditOrAddQuestionRequest, Long> {

    private final QuestionService questionService;

    @Operation(
            summary = "API for create new question",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Question.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping
    @Override
    public ResponseEntity<?> add(@RequestBody EditOrAddQuestionRequest entity) {
        try {
            return questionService.store(entity);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> edit(@RequestBody EditOrAddQuestionRequest entity, @PathVariable(name = "id") Long aLong) {
        try {
            return questionService.update(entity, aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/by-type/{typeId}")
    public ResponseEntity<?> getAllByType(@PathVariable(name = "typeId") Long Id, @PathVariable String typeId) {
        try {
            return questionService.getByType(Id);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long aLong) {
        try {
            return questionService.destroy(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return questionService.getAll(pageable);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long aLong) {
        try {
            return questionService.getById(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }
}
