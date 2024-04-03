package com.ego.doan_ego.controller;

import com.ego.doan_ego.request.test.EditOrAddTestRequest;
import com.ego.doan_ego.request.test.TestRequest;
import com.ego.doan_ego.service.interfaceService.TestService;
import com.ego.doan_ego.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasRole('ROLE_TEACHER')")
public class TestController implements IBaseController<TestRequest, EditOrAddTestRequest, String> {

    private final TestService testService;

    @PostMapping
    @Override
    public ResponseEntity<?> add(@RequestBody EditOrAddTestRequest entity) {
        try {
            return testService.store(entity);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> edit(@RequestBody EditOrAddTestRequest entity, @PathVariable(name = "id") String aLong) {
        try {
            return testService.update(entity, aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable(name = "id") String aLong) {
        try {
            return testService.destroy(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return testService.getAll(pageable);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable(name = "id") String aLong) {
        try {
            return testService.getById(aLong);
        } catch (Exception e) {
            return ApiResponse.fallback(e.getMessage());
        }
    }
}
