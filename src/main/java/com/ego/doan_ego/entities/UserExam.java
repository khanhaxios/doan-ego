package com.ego.doan_ego.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserExam {
    @jakarta.persistence.Id
    private Long Id;

    @ManyToOne
    private UserDao userDao;

    @OneToOne
    private Exam exam;

    private int point;

    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    @OneToMany
    private List<Answer> answers = new ArrayList<>();
}
