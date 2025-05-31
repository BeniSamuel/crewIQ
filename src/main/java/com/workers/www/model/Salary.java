package com.workers.www.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Double amount;

    @Column
    private String description;

    @Column
    private LocalDateTime duration;

    public Salary (User user, Double amount, String description, LocalDateTime duration) {
        this.user = user;
        this.amount = amount;
        this.description = description;
        this.duration = duration;
    }
}
