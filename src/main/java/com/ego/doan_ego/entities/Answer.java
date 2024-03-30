package com.ego.doan_ego.entities;

import com.ego.doan_ego.constant.AnswerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Answer {

    @jakarta.persistence.Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long Id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean isDefaultAnswer;

    private boolean isCorrect;
}
