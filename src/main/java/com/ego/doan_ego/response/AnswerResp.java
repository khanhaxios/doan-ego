package com.ego.doan_ego.response;

import com.ego.doan_ego.entities.Answer;
import com.ego.doan_ego.entities.Question;
import lombok.Data;

@Data
public class AnswerResp extends Answer {

    private Question question;

}
