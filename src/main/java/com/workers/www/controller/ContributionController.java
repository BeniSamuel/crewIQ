package com.workers.www.controller;

import com.workers.www.dto.CreateContributionDto;
import com.workers.www.model.Contribution;
import com.workers.www.service.ContributionService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers/contribution")
@AllArgsConstructor
public class ContributionController {
    private final ContributionService contributionService;

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<Contribution>>> getContributionByCurrentUser (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained contribution", this.contributionService.getContributionForCurrentUser(page, size)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Contribution>>> getAllContribution () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all contributions", this.contributionService.getAllContribution()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contribution>> getContributionById (@PathVariable Long id) {
        Contribution contribution = this.contributionService.getContributionById(id);
        if (contribution != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained the contribution", contribution));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain contribution", null));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<Page<Contribution>>> getContributionsByUser (
            @PathVariable Long user_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Contribution> contributions = this.contributionService.getContributionByUser(user_id, page, size);
        if (!contributions.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all contributions by user", contributions));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain contribution by user", null));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Contribution>> createContribution (@RequestBody CreateContributionDto createContributionDto) {
        Contribution newContribution = this.contributionService.createContribution(createContributionDto);
        if (newContribution != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully created contribution", newContribution));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create contribution", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteContributionById (@PathVariable Long id) {
        return this.contributionService.deleteContributionById(id) ?
               ResponseEntity.ok(new ApiResponse<>(true, "Successfully deleted contribution", true)) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to delete contribution", false));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Contribution>> updateContributionById (@PathVariable Long id, @RequestBody CreateContributionDto createContributionDto) {
        Contribution contribution = this.contributionService.updateContributionById(id, createContributionDto);
        if (contribution != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully updated contribution", contribution));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to update contribution", null));
    }

}
