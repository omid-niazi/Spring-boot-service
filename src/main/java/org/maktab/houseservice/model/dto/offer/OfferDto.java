package org.maktab.houseservice.model.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.dto.expert.ExpertResponseDto;
import org.maktab.houseservice.model.entity.Offer;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OfferDto {
    private Long id;
    private Long requestId;
    private ExpertResponseDto expertResponseDto;
    private LocalDateTime submitOfferTime;
    private Long requestedAmount;
    private Integer requiredDuration;
    private LocalDateTime startTime;

    public static OfferDto fromEntity(Offer offer) {
        return new OfferDto(offer.getId(), offer.getRequest().getId(), ExpertResponseDto.fromEntity(offer.getExpert()), offer.getSubmitOfferTime(), offer.getRequestedAmount(), offer.getRequiredDuration(), offer.getStartTime());
    }
}
