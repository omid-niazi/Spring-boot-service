package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.dto.comment.CommentResponseDto;
import org.maktab.houseservice.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Boolean existsByOffer_Id(Long id);

    List<Comment> findByOffer_Expert_Email(String email);

    List<Comment> findByOffer_Request_Client_Email(String email);

    Optional<Comment> findByIdAndOffer_Request_Client_Email(Long id, String clientEmail);
}
