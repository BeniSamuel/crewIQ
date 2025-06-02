package com.workers.www.dto;

import lombok.Getter;

@Getter
public class CreateCommentAssDto {
    private Long assignment_id;
    private Long user_id;
    private String comment;
}
