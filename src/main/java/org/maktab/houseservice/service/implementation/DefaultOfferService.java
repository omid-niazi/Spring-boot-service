package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.*;
import org.maktab.houseservice.model.dto.offer.CreateOfferDto;
import org.maktab.houseservice.model.dto.offer.OfferDto;
import org.maktab.houseservice.model.entity.Expert;
import org.maktab.houseservice.model.entity.Offer;
import org.maktab.houseservice.model.entity.Request;
import org.maktab.houseservice.model.entity.RequestStatus;
import org.maktab.houseservice.repository.OfferRepository;
import org.maktab.houseservice.repository.RequestRepository;
import org.maktab.houseservice.service.ExpertService;
import org.maktab.houseservice.service.OfferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultOfferService implements OfferService {
    private final OfferRepository offerRepository;
    private final ExpertService expertService;
    private final RequestRepository requestRepository;

    @Transactional
    @Override
    public OfferDto create(String expertEmail, CreateOfferDto createOfferDto) {
        Expert expert = expertService.findByEmail(expertEmail);
        Request request = requestRepository.findByIdAndRequestStatus(createOfferDto.getRequestId(), RequestStatus.OPEN_TO_OFFER).orElseThrow(() -> new RequestNotFoundException(String.format("there is no open request with id %d", createOfferDto.getRequestId())));
        if (request.getRequiredSkill().getBasePrice() > createOfferDto.getRequestedAmount()) {
            throw new OfferAmountIsLowException(String.format("your offer must be at least %d", request.getRequiredSkill().getBasePrice()));
        }

        Boolean expertOfferedBefore = offerRepository.existsByExpertAndRequest(expert, request);
        if (expertOfferedBefore) {
            throw new DuplicateOfferException("expert can offer to a request only once");
        }

        if (!expert.getSkills().contains(request.getRequiredSkill())) {
            throw new SkillsDoesNotMatchedException("you don't have required skill for this request");
        }

        Offer offer = new Offer(request, expert, createOfferDto.getRequestedAmount(), createOfferDto.getRequiredDuration(), createOfferDto.getStartTime());
        Offer savedOffer = offerRepository.save(offer);
        return OfferDto.fromEntity(savedOffer);
    }

    @Transactional
    @Override
    public OfferDto removeOffer(String expertEmail, Long id) {
        Offer offer = offerRepository.findByIdAndExpert_EmailAndRequest_RequestStatus(id, expertEmail, RequestStatus.OPEN_TO_OFFER)
                .orElseThrow(() -> new OfferNotFoundException(String.format("you don't have an offer with id %d to an open request", id)));

        offerRepository.delete(offer);

        return OfferDto.fromEntity(offer);
    }

    @Transactional
    @Override
    public OfferDto updateOffer(String expertEmail, Long id, CreateOfferDto createOfferDto) {
        Offer offer = offerRepository.findByIdAndExpert_EmailAndRequest_RequestStatus(id, expertEmail, RequestStatus.OPEN_TO_OFFER)
                .orElseThrow(() -> new OfferNotFoundException(String.format("you don't have an offer with id %d (or the request is not open)", id)));
        if (createOfferDto.getRequestedAmount() != null) {
            if (createOfferDto.getRequestedAmount() < offer.getRequest().getRequiredSkill().getBasePrice()) {
                throw new OfferAmountIsLowException(String.format("your offer must be at least %d", offer.getRequest().getRequiredSkill().getBasePrice()));
            }
            offer.setRequestedAmount(createOfferDto.getRequestedAmount());
        }

        if (createOfferDto.getStartTime() != null) {
            offer.setStartTime(createOfferDto.getStartTime());
        }

        if (createOfferDto.getRequiredDuration() != null) {
            offer.setRequiredDuration(createOfferDto.getRequiredDuration());
        }
        return OfferDto.fromEntity(offer);
    }
}
