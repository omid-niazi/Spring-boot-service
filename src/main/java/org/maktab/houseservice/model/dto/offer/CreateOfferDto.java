package org.maktab.houseservice.model.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.entity.Offer;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateOfferDto {
    private Long requestId;
    private LocalDateTime submitOfferTime;
    private Long requestedAmount;
    private Integer requiredDuration;
    @Future
    private LocalDateTime startTime;

    public static CreateOfferDto fromEntity(Offer offer) {
        return new CreateOfferDto(offer.getRequest().getId(),
                offer.getSubmitOfferTime(), offer.getRequestedAmount(), offer.getRequiredDuration(), offer.getStartTime());
    }
}
