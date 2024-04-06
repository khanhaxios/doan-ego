package com.ego.doan_ego.request.user_exam;

import com.ego.doan_ego.constant.UserExamStatus;
import com.ego.doan_ego.request.answer.EditOrAddAnswerRequest;
import lombok.Data;

import java.util.List;

@Data
public class EditOrAddUserExamRequest extends UserExamRequest {
    private Long userId;
    private Long examId;
    private int point;

    private UserExamStatus status;
    private Long timeStart;
    private Long timeEnd;
    private List<EditOrAddAnswerRequest> answerRequests;
}
