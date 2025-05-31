package com.workers.www.repository;

import com.workers.www.enums.AssignmentStatus;
import com.workers.www.model.Assignment;
import com.workers.www.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Assignment getAssignmentById (Long id);
    List<Assignment> getAssignmentsByManager(User manager, Pageable pageable);
    List<Assignment> getAssignmentsByStatus(AssignmentStatus status, Pageable pageable);
}
