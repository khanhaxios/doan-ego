package com.ego.doan_ego.request.question;

import com.ego.doan_ego.entities.QuestionType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EditOrAddQuestionRequest extends QuestionRequest {
    private String title;
    private String content;
    private int score;
    private Long answerId;
    private Long questionTypeId;

    private List<Long> childQuestionId = new ArrayList<>();
}
