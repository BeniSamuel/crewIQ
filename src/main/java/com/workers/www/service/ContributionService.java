package com.workers.www.service;

import com.workers.www.dto.CreateContributionDto;
import com.workers.www.model.Assignment;
import com.workers.www.model.Contribution;
import com.workers.www.model.User;
import com.workers.www.repository.ContributionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContributionService {
    private final ContributionRepository contributionRepository;
    private final UserService userService;
    private final AssignmentService assignmentService;

    public List<Contribution> getAllContribution () {
        return this.contributionRepository.findAll();
    }

    public Contribution getContributionById (Long id) {
        return this.contributionRepository.getContributionById(id);
    }

    public Page<Contribution> getContributionByUser (Long user_id, int page, int size) {
        User user = this.userService.getUserById(user_id);
        if (user != null) {
            Pageable pageable = PageRequest.of(page, size);
            return this.contributionRepository.getContributionsByContributor(user, pageable);
        }
        return null;
    }

    public Contribution createContribution (CreateContributionDto createContributionDto) {
        Assignment assignment = this.assignmentService.getAssignmentById(createContributionDto.getAssignment_id());
        if (assignment == null) {
            return null;
        }

        User contributor = this.userService.getUserById(createContributionDto.getUser_id());
        if (contributor == null) {
            return null;
        }

        Contribution newContribution = new Contribution(assignment, contributor, createContributionDto.getDescription());
        return this.contributionRepository.save(newContribution);
    }

    public Page<Contribution> getContributionForCurrentUser (int page, int size) {
        User contributor = this.userService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size);

        return this.contributionRepository.getContributionsByContributor(contributor, pageable);
    }

    public Boolean deleteContributionById (Long id) {
        Contribution contribution = this.getContributionById(id);
        if (contribution != null) {
            this.contributionRepository.delete(contribution);
            return true;
        }
        return false;
    }

    public Contribution updateContributionById (Long id, CreateContributionDto createContributionDto) {
        Contribution contribution = this.getContributionById(id);
        if (contribution == null) {
            return null;
        }

        Assignment assignment = this.assignmentService.getAssignmentById(createContributionDto.getAssignment_id());
        if (assignment == null) {
            return null;
        }

        User contributor = this.userService.getUserById(createContributionDto.getUser_id());
        if (contributor == null) {
            return null;
        }

        contribution.setContributor(contributor);
        contribution.setAssignment(assignment);
        contribution.setDescription(createContributionDto.getDescription());

        return this.contributionRepository.save(contribution);
    }
}
