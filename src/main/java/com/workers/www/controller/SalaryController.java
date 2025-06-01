package com.workers.www.controller;

import com.workers.www.dto.CreateSalaryDto;
import com.workers.www.model.Salary;
import com.workers.www.service.SalaryService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers/salary")
@AllArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Salary>>> getAllSalaries () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all salaries", this.salaryService.getAllSalaries()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Salary>> getSalaryById (@PathVariable Long id) {
        Salary salary = this.salaryService.getSalaryById(id);
        if (salary != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained salary", salary));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain salary", null));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<Salary>> getSalaryByUser (@PathVariable Long id) {
        Salary salary = this.salaryService.getSalaryByUser(id);
        if (salary != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained salary by user", salary));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain salary by user", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSalaryById (@PathVariable Long id) {
        return this.salaryService.deleteSalaryById(id) ?
               ResponseEntity.ok(new ApiResponse<>(true, "Successfully deleted salary by id", true)) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to delete salary by id", false));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Salary>> updateSalaryById (@PathVariable Long id, @RequestBody CreateSalaryDto createSalaryDto) {
        Salary salary = this.salaryService.updateSalaryById(id, createSalaryDto);
        if (salary != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully updated salary by id", salary));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to update salary by id", null));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Salary>> createSalary (@RequestBody CreateSalaryDto createSalaryDto) {
        Salary salary = this.salaryService.createSalary(createSalaryDto);
        if (salary != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully added salary", salary));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create salary bad request", null));
    }
}
