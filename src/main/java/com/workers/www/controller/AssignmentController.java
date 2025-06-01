package com.workers.www.controller;

import com.workers.www.dto.CreateAssignmentDto;
import com.workers.www.enums.AssignmentStatus;
import com.workers.www.enums.Role;
import com.workers.www.model.Assignment;
import com.workers.www.service.AssignmentService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers/assignment")
@AllArgsConstructor
public class AssignmentController {
    private AssignmentService assignmentService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Assignment>>> getAllAssignment () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all assignment", this.assignmentService.getAllAssignment()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Assignment>>> searchAssignmentByStatusAndRole (
            @RequestParam("status") AssignmentStatus status,
            @RequestParam("role")Role role
            ) {
        List<Assignment> assignments = this.assignmentService.searchAssignment(status, role);
        if (!assignments.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all search results", assignments));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain search result", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Assignment>> getAssignmentById (@PathVariable Long id) {
        Assignment assignment = this.assignmentService.getAssignmentById(id);
        if (assignment != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained assignment", assignment));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain assignment", null));
    }
    
    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<Page<Assignment>>> getAssignmentByUser (
            @PathVariable Long user_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Assignment> assignments = this.assignmentService.getAssignmentByManager(user_id, page, size);
        if (!assignments.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained assignment by user", assignments));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain assignment by user", null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Assignment>> updateAssignmentById (@PathVariable Long id, @RequestBody CreateAssignmentDto createAssignmentDto) {
        Assignment assignment = this.assignmentService.updateAssignment(id, createAssignmentDto);
        if (assignment != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully updated assignment", assignment));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to update assignment", null));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteAssignmentById (@PathVariable Long id) {
        return this.assignmentService.deleteAssignmentById(id) ?
               ResponseEntity.ok(new ApiResponse<>(true, "Successfully deleted assignment", true)) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to delete assignment not found", false)); 
    }

}
