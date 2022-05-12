package org.maktab.houseservice.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.entity.Comment;
import org.maktab.houseservice.model.entity.RateType;

@Data
@AllArgsConstructor
public class CreateCommentRequestDto {
    private Long offerId;
    private RateType rateType;
    private String description;

    public static CreateCommentRequestDto fromEntity(Comment comment) {
        return new CreateCommentRequestDto(comment.getOffer().getId(), comment.getRate(), comment.getDescription());
    }
}
