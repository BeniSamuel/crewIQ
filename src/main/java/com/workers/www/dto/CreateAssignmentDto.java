package com.workers.www.dto;

import com.workers.www.enums.AssignmentStatus;
import com.workers.www.enums.Role;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateAssignmentDto {
    private Long user_id;
    private String description;
    private final LocalDateTime duration = LocalDateTime.now();
    private AssignmentStatus status;
    private Role role;
}
