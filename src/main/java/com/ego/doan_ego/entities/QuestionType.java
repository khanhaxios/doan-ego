package com.ego.doan_ego.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class QuestionType {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String name;
}
