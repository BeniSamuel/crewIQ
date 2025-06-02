package com.workers.www.service;

import com.workers.www.dto.LoginUserDto;
import com.workers.www.dto.RegisterUserDto;
import com.workers.www.model.User;
import com.workers.www.utils.ApiResponse;
import com.workers.www.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public User registerUser (RegisterUserDto registerUserDto) {
        return this.userService.createUser(registerUserDto);
    }

    public ResponseEntity<ApiResponse<String>> loginUser (LoginUserDto loginUserDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword())
            );

            User user = this.userService.getUserByEmail(loginUserDto.getEmail());
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully logged in user", this.jwtUtil.generateToken(user.getEmail())));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to log in user", null));
        }
    }
}
