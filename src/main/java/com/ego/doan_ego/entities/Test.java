package com.ego.doan_ego.entities;

import com.ego.doan_ego.constant.TestType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Test {
    @Id
    private String id;

    public Test() {

    }

    public Test(String id) {
        LocalTime localTime = LocalTime.now();
        this.id = String.format("%s%s%s%s", id, localTime.getHour(), localTime.getMinute(), localTime.getSecond());
    }

    private String name;
    private long time;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TestType testType;

    @ManyToMany
    @JoinTable(name = "test_quest", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions = new HashSet<>();
}
