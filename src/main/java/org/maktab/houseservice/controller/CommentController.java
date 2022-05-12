package org.maktab.houseservice.controller;

import lombok.AllArgsConstructor;
import org.maktab.houseservice.exception.InvalidRequestException;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.comment.CommentResponseDto;
import org.maktab.houseservice.model.dto.comment.CreateCommentRequestDto;
import org.maktab.houseservice.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping
    public ResponseEntity<ApiResponse> postComment(Principal principal, @RequestBody CreateCommentRequestDto createCommentRequestDto) {
        CommentResponseDto commentDto = commentService.postComment(principal.getName(), createCommentRequestDto);
        ApiResponse responseBody = SuccessApiResponse.withData(commentDto);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getClientCommentsById(@RequestParam("role") String role, @RequestParam("email") @Valid @Email String email) {

        List<CommentResponseDto> commentResponseDtoList;
        if (role.equalsIgnoreCase("client")) {
            commentResponseDtoList = commentService.getCommentsByClientEmail(email);
        } else if (role.equalsIgnoreCase("expert")) {
            commentResponseDtoList = commentService.getCommentsByExpertEmail(email);
        } else {
            throw new InvalidRequestException("role attribute must be client or expert");
        }
        ApiResponse responseBody = SuccessApiResponse.withData(commentResponseDtoList);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCommentById(Principal principal, @PathVariable Long id) {
        CommentResponseDto deletedCommentDto = commentService.deleteComment(principal.getName(), id);
        ApiResponse responseBody = SuccessApiResponse.withData(deletedCommentDto);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateComment(Principal principal, @PathVariable Long id, @RequestBody CreateCommentRequestDto createCommentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(principal.getName(), id, createCommentRequestDto);
        ApiResponse responseBody = SuccessApiResponse.withData(commentResponseDto);
        return ResponseEntity.ok(responseBody);
    }

}
