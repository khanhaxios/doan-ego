package com.ego.doan_ego.entities;

import com.ego.doan_ego.constant.UserExamStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserExam {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    private UserDao userDao;

    @ManyToOne
    private Exam exam;

    private int point;

    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    @Enumerated(EnumType.STRING)
    private UserExamStatus status;
    @OneToMany
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public void removeAnswer(Answer answer) {
        this.answers.remove(answer);
    }

    public void clear() {
        this.answers.clear();
    }
}
