package org.maktab.houseservice.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.dto.offer.OfferDto;
import org.maktab.houseservice.model.entity.Comment;
import org.maktab.houseservice.model.entity.RateType;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private OfferDto offerDto;
    private RateType rateType;
    private String description;

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(comment.getId(), OfferDto.fromEntity(comment.getOffer()), comment.getRate(), comment.getDescription());
    }

}
