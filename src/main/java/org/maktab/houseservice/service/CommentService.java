package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.comment.CommentResponseDto;
import org.maktab.houseservice.model.dto.comment.CreateCommentRequestDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto postComment(String name, CreateCommentRequestDto createCommentRequestDto);

    List<CommentResponseDto> getCommentsByExpertEmail(String email);

    List<CommentResponseDto> getCommentsByClientEmail(String email);

    CommentResponseDto deleteComment(String clientEmail, Long commentId);

    CommentResponseDto updateComment(String clientEmail, Long id, CreateCommentRequestDto createCommentRequestDto);
}
