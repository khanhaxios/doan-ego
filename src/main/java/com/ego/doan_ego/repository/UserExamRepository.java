package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.Exam;
import com.ego.doan_ego.entities.UserDao;
import com.ego.doan_ego.entities.UserExam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExamRepository extends JpaRepository<UserExam, Long> {
    Page<UserExam> findAllByExam(Pageable pageable, Exam exam);

    Page<UserExam> findAllByUserDao(Pageable pageable, UserDao userDao);
}
