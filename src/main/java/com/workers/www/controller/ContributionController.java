package com.workers.www.controller;

import com.workers.www.model.Contribution;
import com.workers.www.service.ContributionService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workers/contribution")
@AllArgsConstructor
public class ContributionController {
    private final ContributionService contributionService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Contribution>>> getAllContribution () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all contributions", this.contributionService.getAllContribution()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contribution>> getContributionById (@PathVariable Long id) {
        Contribution contribution = this.contributionService.getContributionById(id);

    }
}
