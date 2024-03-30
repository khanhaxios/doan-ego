package com.ego.doan_ego.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private int score;

    @OneToOne
    private Answer answer;
    @ManyToOne
    @JoinColumn(name = "question_type_id")
    @Nullable
    private QuestionType questionType;
    @OneToMany
    private List<Question> childrenQuestion = new ArrayList<>();

}
