package com.ego.doan_ego.request.answer;

import lombok.Data;

@Data
public class EditOrAddAnswerRequest extends AnswerRequest {
    private String content;

    private boolean isCorrect;

    private boolean isDefaultAnswer;

    private Long questionId;
}
