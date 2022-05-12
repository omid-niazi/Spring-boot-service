package org.maktab.houseservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.dto.service.ServiceResponseDto;
import org.maktab.houseservice.model.dto.client.ClientResponseDto;
import org.maktab.houseservice.model.dto.offer.OfferDto;
import org.maktab.houseservice.model.entity.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RequestResponseDto {
    private Long id;
    private LocalDateTime creationTime;
    private ClientResponseDto clientResponseDto;
    private Set<OfferDto> offers = new HashSet<>();
    private RequestStatus requestStatus;
    private Long offerAmount;
    private String title;
    private String description;
    private LocalDateTime doingTime;
    private ServiceResponseDto requiredSkill;

    public static RequestResponseDto fromEntity(Request request) {
        Set<OfferDto> offerDtos = request.getOffers().stream().map(OfferDto::fromEntity).collect(Collectors.toSet());
        return new RequestResponseDto(request.getId(), request.getCreationTime(), ClientResponseDto.fromEntity(request.getClient(), false), offerDtos, request.getRequestStatus(), request.getOfferAmount(), request.getTitle(), request.getDescription(), request.getDoingTime(), ServiceResponseDto.fromEntity(request.getRequiredSkill()));
    }

}
