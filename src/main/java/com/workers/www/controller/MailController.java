package com.workers.www.controller;

import com.workers.www.service.MailService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workers/send/mail")
@AllArgsConstructor
public class MailController {
    private final MailService mailService;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> getTestingMessage () {
        this.mailService.sendEmail("ishimwebeni544@gmail.com", "Just testing mail", "Testing is just going to being");
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully sent the testing mail", "Done 🎉🎉🎉"));
    }
}
