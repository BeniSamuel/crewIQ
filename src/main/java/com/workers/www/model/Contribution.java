package com.workers.www.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User contributor;

    @Column
    private String description;

    public Contribution (Assignment assignment, User contributor, String description) {
        this.assignment = assignment;
        this.contributor = contributor;
        this.description = description;
    }
}
