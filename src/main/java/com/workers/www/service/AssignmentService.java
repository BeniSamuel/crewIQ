package com.workers.www.service;

import com.workers.www.dto.CreateAssignmentDto;
import com.workers.www.enums.AssignmentStatus;
import com.workers.www.enums.Role;
import com.workers.www.model.Assignment;
import com.workers.www.model.User;
import com.workers.www.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final UserService userService;

    public List<Assignment> getAllAssignment () {
        return this.assignmentRepository.findAll();
    }

    public Page<Assignment> getAssignmentByManager (Long user_id, int page, int size) {
        User manager = this.userService.getUserById(user_id);
        if (manager != null) {
            Pageable pageable = PageRequest.of(page, size);
            return this.assignmentRepository.getAssignmentsByManager(manager, pageable);
        }
        return null;
    }

    public Page<Assignment> getAssignmentByStatus (AssignmentStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.assignmentRepository.getAssignmentsByStatus(status, pageable);
    }

    public List<Assignment> searchAssignment (AssignmentStatus status, Role role) {
        return this.assignmentRepository.findAll(Sort.by(status.name(), role.name()));
    }

    public Assignment getAssignmentById (Long id) {
        return this.assignmentRepository.getAssignmentById(id);
    }

    public Assignment createAssignment (CreateAssignmentDto createAssignmentDto) {
        User user = this.userService.getUserById(createAssignmentDto.getUser_id());
        if (user == null) {
            return null;
        }

        Assignment assignment = new Assignment(user, createAssignmentDto.getDescription(), createAssignmentDto.getDuration(), createAssignmentDto.getStatus(), createAssignmentDto.getRole());
        return this.assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment (Long id, CreateAssignmentDto createAssignmentDto)  {
        Assignment assignment = this.getAssignmentById(id);
        if (assignment == null) {
            return null;
        }

        User user = this.userService.getUserById(createAssignmentDto.getUser_id());
        if (user == null) {
            return null;
        }

        assignment.setManager(user);
        assignment.setDescription(createAssignmentDto.getDescription());
        assignment.setDuration(createAssignmentDto.getDuration());
        assignment.setRole(createAssignmentDto.getRole());
        assignment.setStatus(createAssignmentDto.getStatus());

        return this.assignmentRepository.save(assignment);
    }

    public Boolean deleteAssignmentById (Long id) {
        Assignment assignment = this.getAssignmentById(id);
        if (assignment != null) {
            this.assignmentRepository.delete(assignment);
            return true;
        }
        return false;
    }
}
