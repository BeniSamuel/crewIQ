package com.workers.www.dto;

import com.workers.www.enums.Role;
import lombok.Getter;

@Getter
public class RegisterUserDto {
    private String name;
    private String email;
    private String password;
    private Role role;
}
