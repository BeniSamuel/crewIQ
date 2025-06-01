package com.workers.www.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateSalaryDto {
    private Long user_id;
    private Double amount;
    private String description;
    private LocalDateTime duration;
}
