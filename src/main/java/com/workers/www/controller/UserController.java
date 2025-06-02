package com.workers.www.controller;

import com.workers.www.dto.RegisterUserDto;
import com.workers.www.model.User;
import com.workers.www.service.UserService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all user!!!", this.userService.getAllUsers()));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsersPaginated (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all users paginated!!!", this.userService.getAllUserPaginated(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById (@PathVariable Long id) {
        User user = this.userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained user!!!", user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain user!!!", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getCurrentUser () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained current user", this.userService.getCurrentUser()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<User>> updateUserById (@PathVariable Long id, @RequestBody RegisterUserDto registerUserDto) {
        User user = this.userService.updateUserById(id, registerUserDto);
        if (user != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully updated user!!!", user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to update user!!!", null));
    }

}
