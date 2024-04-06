package com.ego.doan_ego.response;

import com.ego.doan_ego.entities.UserExam;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserExamResp extends UserExam {
    private List<AnswerResp> answerResps = new ArrayList<>();
}
