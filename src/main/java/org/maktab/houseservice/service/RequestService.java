package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.request.RequestCreationDto;
import org.maktab.houseservice.model.dto.request.RequestResponseDto;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    RequestResponseDto createRequest(String clientEmail, RequestCreationDto requestCreationDto);

    RequestResponseDto confirmRequest(Long requestId);

    List<RequestResponseDto> findAll(Optional<Integer> page, Optional<Integer> itemsInPage);

    RequestResponseDto findById(Long id);

    RequestResponseDto chooseOffer(String clientEmail, Long offerId, Long requestId);

    RequestResponseDto doneRequest(String clientEmail, Long requestId);
}
