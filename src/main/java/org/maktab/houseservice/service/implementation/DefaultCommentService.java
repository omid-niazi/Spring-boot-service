package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.CommentNotFoundException;
import org.maktab.houseservice.exception.DuplicateCommentException;
import org.maktab.houseservice.exception.OfferNotFoundException;
import org.maktab.houseservice.model.dto.comment.CommentResponseDto;
import org.maktab.houseservice.model.dto.comment.CreateCommentRequestDto;
import org.maktab.houseservice.model.entity.Comment;
import org.maktab.houseservice.model.entity.Offer;
import org.maktab.houseservice.model.entity.RequestStatus;
import org.maktab.houseservice.repository.CommentRepository;
import org.maktab.houseservice.repository.OfferRepository;
import org.maktab.houseservice.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final OfferRepository offerRepository;

    @Override
    public CommentResponseDto postComment(String email, CreateCommentRequestDto createCommentRequestDto) {
        Offer offer = offerRepository.findByIdAndRequest_Client_EmailAndRequest_RequestStatus(createCommentRequestDto.getOfferId(), email, RequestStatus.DONE)
                .orElseThrow(() -> new OfferNotFoundException(String.format("you don't have a done offer with id %d", createCommentRequestDto.getOfferId())));


        Boolean aCommentAlreadyPosted = commentRepository.existsByOffer_Id(createCommentRequestDto.getOfferId());
        if (aCommentAlreadyPosted) {
            throw new DuplicateCommentException(String.format("you already post a comment for offer %d ", createCommentRequestDto.getOfferId()));
        }
        Comment comment = new Comment(null, offer, createCommentRequestDto.getRateType(), createCommentRequestDto.getDescription());
        commentRepository.save(comment);
        return CommentResponseDto.fromEntity(comment);
    }


    @Override
    public List<CommentResponseDto> getCommentsByExpertEmail(String email) {
        List<CommentResponseDto> commentsDto = commentRepository.findByOffer_Expert_Email(email)
                .stream().map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
        return commentsDto;
    }

    @Override
    public List<CommentResponseDto> getCommentsByClientEmail(String email) {
        List<CommentResponseDto> commentsDto = commentRepository.findByOffer_Request_Client_Email(email)
                .stream().map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
        return commentsDto;
    }

    @Override
    public CommentResponseDto deleteComment(String clientEmail, Long commentId) {
        Comment comment = commentRepository.findByIdAndOffer_Request_Client_Email(commentId, clientEmail)
                .orElseThrow(() -> new CommentNotFoundException(String.format("you don't have a comment with id %d ", commentId)));
        commentRepository.delete(comment);
        return CommentResponseDto.fromEntity(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto updateComment(String clientEmail, Long id, CreateCommentRequestDto createCommentRequestDto) {
        Comment comment = commentRepository.findByIdAndOffer_Request_Client_Email(id, clientEmail).orElseThrow(() -> new CommentNotFoundException(String.format("you dont have a comment with id %d", id)));
        if (createCommentRequestDto.getDescription() != null && !createCommentRequestDto.getDescription().isBlank()) {
            comment.setDescription(createCommentRequestDto.getDescription());
        }
        if (createCommentRequestDto.getRateType() != null) {
            comment.setRate(createCommentRequestDto.getRateType());
        }

        return CommentResponseDto.fromEntity(comment);
    }
}
