package com.workers.www.dto;

import lombok.Getter;

@Getter
public class CreateContributionDto {
    private Long assignment_id;
    private Long user_id;
    private String description;
}
