package com.ego.doan_ego.request.exam;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class EditOrAddExamRequest extends ExamRequest {
    private String name;
    private long startTime;

    private long endTime;
    private Set<String> testIds = new HashSet<>();

}
