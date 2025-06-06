package com.workers.www.repository;

import com.workers.www.model.Assignment;
import com.workers.www.model.Comment;
import com.workers.www.model.Contribution;
import com.workers.www.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment getCommentById (Long id);
    Page<Comment> getCommentsByAuthor (User user, Pageable pageable);
    Page<Comment> getCommentsByContribution (Contribution contribution, Pageable pageable);
    Page<Comment> getCommentsByAssignment (Assignment assignment, Pageable pageable);
}
