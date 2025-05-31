package com.workers.www.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "contribution_id")
    private Contribution contribution;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column
    private String comment;

    public Comment (Assignment assignment, User author, String comment) {
        this.assignment = assignment;
        this.author = author;
        this.comment = comment;
    }

    public Comment (Contribution contribution, User author, String comment) {
       this.contribution = contribution;
       this.author = author;
       this.comment = comment;
    }
}
