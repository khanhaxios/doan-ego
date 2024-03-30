package com.ego.doan_ego.request.question_type;

import lombok.Data;

@Data
public class EditOrAddQuestionTypeRequest extends QuestionTypeRequest {
    private String name;
}
