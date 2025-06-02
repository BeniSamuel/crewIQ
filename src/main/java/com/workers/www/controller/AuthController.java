package com.workers.www.controller;

import com.workers.www.dto.LoginUserDto;
import com.workers.www.dto.RegisterUserDto;
import com.workers.www.model.User;
import com.workers.www.service.AuthService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workers/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> registerUser (@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully registered user", this.authService.registerUser(registerUserDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser (@RequestBody LoginUserDto loginUserDto) {
        return this.authService.loginUser(loginUserDto);
    }
}
