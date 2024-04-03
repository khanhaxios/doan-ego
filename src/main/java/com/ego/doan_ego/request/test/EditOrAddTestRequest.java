package com.ego.doan_ego.request.test;

import com.ego.doan_ego.constant.TestType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EditOrAddTestRequest extends TestRequest {
    private String id;
    private String name;
    private long time;

    private TestType testType;

    private List<Long> questionIds;
}
