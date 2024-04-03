package com.ego.doan_ego.service.interfaceService;

import com.ego.doan_ego.request.exam.EditOrAddExamRequest;
import com.ego.doan_ego.request.exam.ExamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface ExamService extends IBaseService<ExamRequest, EditOrAddExamRequest, Long> {
}
