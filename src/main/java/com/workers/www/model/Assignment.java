package com.workers.www.model;

import com.workers.www.enums.AssignmentStatus;
import com.workers.www.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User manager;

    @Column
    private String description;

    @Column
    private LocalDateTime duration;

    @Enumerated(value = EnumType.STRING)
    private AssignmentStatus status;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Assignment (User manager, String description, LocalDateTime duration, AssignmentStatus status, Role role) {
        this.manager = manager;
        this.description = description;
        this.duration = duration;
        this.status = status;
        this.role = role;
    }
}
