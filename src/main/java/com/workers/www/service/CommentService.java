package com.workers.www.service;

import com.workers.www.dto.CreateCommentAssDto;
import com.workers.www.dto.CreateCommentContrDto;
import com.workers.www.model.Assignment;
import com.workers.www.model.Comment;
import com.workers.www.model.Contribution;
import com.workers.www.model.User;
import com.workers.www.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AssignmentService assignmentService;
    private final ContributionService contributionService;

    public List<Comment> getAllComments () {
        return this.commentRepository.findAll();
    }

    public Comment getCommentById (Long id) {
        return this.commentRepository.getCommentById(id);
    }

    public Page<Comment> getCommentByUser (Long user_id, int page, int size) {
        User user = this.userService.getUserById(user_id);
        if (user != null) {
            Pageable pageable = PageRequest.of(page, size);
            return this.commentRepository.getCommentsByAuthor(user, pageable);
        }
        return null;
    }

    public Page<Comment> getCommentByAssignment (Long assignment_id, int page, int size) {
        Assignment assignment = this.assignmentService.getAssignmentById(assignment_id);
        if (assignment != null) {
            Pageable pageable = PageRequest.of(page, size);
            return this.commentRepository.getCommentsByAssignment(assignment, pageable);
        }
        return null;
    }

    public Page<Comment> getCommentByContribution (Long contribution_id, int page, int size) {
        Contribution contribution= this.contributionService.getContributionById(contribution_id);
        if (contribution != null) {
            Pageable pageable = PageRequest.of(page, size);
            return this.commentRepository.getCommentsByContribution(contribution, pageable);
        }
        return null;
    }

    public Boolean deleteCommentById (Long id) {
        Comment comment = this.getCommentById(id);
        if (comment != null) {
            this.commentRepository.delete(comment);
            return true;
        }
        return false;
    }

    public Comment updateComment (Long id, CreateCommentAssDto commentDto) {
        Comment comment = this.getCommentById(id);
        if (comment == null) {
            return null;
        }

        Assignment assignment = this.assignmentService.getAssignmentById(commentDto.getAssignment_id());
        if (assignment == null) {
            return null;
        }

        User author = this.userService.getUserById(commentDto.getUser_id());
        if (author == null) {
            return null;
        }

        comment.setAssignment(assignment);
        comment.setAuthor(author);
        comment.setComment(comment.getComment());

        return this.commentRepository.save(comment);
    }

    public Comment createCommentOnAss (CreateCommentAssDto commentAssDto) {
        Assignment assignment = this.assignmentService.getAssignmentById(commentAssDto.getAssignment_id());
        if (assignment == null) {
            return null;
        }

        User author = this.userService.getUserById(commentAssDto.getUser_id());
        if (author == null) {
            return null;
        }

        Comment newComment = new Comment(assignment, author, commentAssDto.getComment());
        return this.commentRepository.save(newComment);
    }

    public Comment createCommentOnContribution (CreateCommentContrDto commentContrDto) {
        Contribution contribution = this.contributionService.getContributionById(commentContrDto.getContribution_id());
        if (contribution == null) {
            return null;
        }

        User author = this.userService.getUserById(commentContrDto.getUser_id());
        if (author == null) {
            return null;
        }

        Comment comment = new Comment(contribution, author, commentContrDto.getComment());
        return this.commentRepository.save(comment);
    }
}
