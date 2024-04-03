package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {
}
