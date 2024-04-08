package com.ego.doan_ego.service.interfaceService;

import com.ego.doan_ego.request.question.EditOrAddQuestionRequest;
import com.ego.doan_ego.request.question.QuestionRequest;
import org.springframework.http.ResponseEntity;

public interface QuestionService extends IBaseService<QuestionRequest, EditOrAddQuestionRequest, Long> {

    ResponseEntity<?> getByType(Long typeId) throws Exception;
}
