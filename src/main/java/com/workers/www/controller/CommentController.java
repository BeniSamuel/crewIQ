package com.workers.www.controller;

import com.workers.www.dto.CreateCommentAssDto;
import com.workers.www.dto.CreateCommentContrDto;
import com.workers.www.model.Comment;
import com.workers.www.service.CommentService;
import com.workers.www.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Comment>>> getAllComments () {
        return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all comments", this.commentService.getAllComments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Comment>> getCommentById (@PathVariable Long id) {
        Comment comment = this.commentService.getCommentById(id);
        return comment != null ?
               ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained comment", comment)) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain comment",null));
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<ApiResponse<Page<Comment>>> getCommentByUser (
            @PathVariable Long user_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
            ) {
        Page<Comment> comments = this.commentService.getCommentByUser(user_id, page, size);
        if (!comments.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all comments by user", comments));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain comment by user", null));
    }

    @GetMapping("/assignment/{assignment_id}")
    public ResponseEntity<ApiResponse<Page<Comment>>> getCommentByAssignment (
            @PathVariable Long assignment_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Comment> comments = this.commentService.getCommentByAssignment(assignment_id, page, size);
        if (!comments.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all comments by assignment", comments));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain comment by assignment", null));
    }

    @GetMapping("/contribution/{contribution_id}")
    public ResponseEntity<ApiResponse<Page<Comment>>> getCommentByContribution (
            @PathVariable Long contribution_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Comment> comments = this.commentService.getCommentByContribution(contribution_id, page, size);
        if (!comments.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully obtained all comments by contribution", comments));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to obtain comment by contribution", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteComment (@PathVariable Long id) {
        return this.commentService.deleteCommentById(id) ?
               ResponseEntity.ok(new ApiResponse<>(true, "Successfully deleted comment", true)) :
               ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Failed to delete comment", null));
    }

    @PutMapping("/update/{id}/assignment")
    public ResponseEntity<ApiResponse<Comment>> updateCommentByAssignment (@PathVariable Long id ,@RequestBody CreateCommentAssDto commentAssDto) {
        Comment comment = this.commentService.updateComment(id, commentAssDto);
        if (comment != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully updated comment", comment));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(true, "Failed to update comment", null));
    }

    @PostMapping("/create/assignment")
    public ResponseEntity<ApiResponse<Comment>> createCommentOnAssignment (@RequestBody CreateCommentAssDto createCommentAssDto) {
        Comment newComment = this.commentService.createCommentOnAss(createCommentAssDto);
        if (newComment != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully created comment", newComment));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create comment", null));
    }

    @PostMapping("/create/contribution")
    public ResponseEntity<ApiResponse<Comment>> createCommentOnContribution (@RequestBody CreateCommentContrDto commentContrDto) {
        Comment newComment = this.commentService.createCommentOnContribution(commentContrDto);
        if (newComment != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Successfully created comment", newComment));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Failed to create comment", null));
    }

}
