package com.workers.www.repository;

import com.workers.www.model.Assignment;
import com.workers.www.model.Contribution;
import com.workers.www.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    Contribution getContributionById (Long id);
    Page<Contribution> getContributionsByContributor (User user, Pageable pageable);
    Page<Contribution> getContributionsByAssignment (Assignment assignment, Pageable pageable);
}
