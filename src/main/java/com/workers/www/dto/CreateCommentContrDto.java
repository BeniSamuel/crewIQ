package com.workers.www.dto;

import lombok.Getter;

@Getter
public class CreateCommentContrDto {
    private Long contribution_id;
    private Long user_id;
    private String comment;
}
