package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.*;
import org.maktab.houseservice.model.dto.request.RequestCreationDto;
import org.maktab.houseservice.model.dto.request.RequestResponseDto;
import org.maktab.houseservice.model.entity.*;
import org.maktab.houseservice.repository.OfferRepository;
import org.maktab.houseservice.repository.RequestRepository;
import org.maktab.houseservice.service.ClientService;
import org.maktab.houseservice.service.RequestService;
import org.maktab.houseservice.service.ServiceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultRequestService implements RequestService {

    private final RequestRepository requestRepository;
    private final ClientService clientService;
    private final ServiceService serviceService;
    private final OfferRepository offerRepository;

    @Transactional
    @Override
    public RequestResponseDto createRequest(String clientEmail, RequestCreationDto requestCreationDto) {
        Client client = clientService.findByEmail(clientEmail);
        if (client.getBalance() < requestCreationDto.getOfferAmount()) {
            throw new BalanceIsNotEnoughException(String.format("your balance is %d, it is %d lower than your offer", client.getBalance(), (requestCreationDto.getOfferAmount() - client.getBalance())));
        }
        org.maktab.houseservice.model.entity.Service requiredSkill = serviceService.findServiceById(requestCreationDto.getServiceId());
        if (requestCreationDto.getOfferAmount() < requiredSkill.getBasePrice()) {
            throw new OfferAmountIsLowException(String.format("your request offer amount must be at least %d", requiredSkill.getBasePrice()));
        }
        Request request = new Request(client, RequestStatus.WAITING_FOR_CONFIRM, requestCreationDto.getOfferAmount(), requestCreationDto.getTitle(), requestCreationDto.getDescription(), requestCreationDto.getDoingTime(), requiredSkill);
        Request savedRequest = requestRepository.save(request);
        return RequestResponseDto.fromEntity(savedRequest);
    }

    @Transactional
    @Override
    public RequestResponseDto confirmRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(String.format("there is not request with id %d", requestId)));
        if (request.getRequestStatus() != RequestStatus.WAITING_FOR_CONFIRM) {
            throw new InvalidRequestException("you can confirm requests which are in WAITING FOR CONFIRM mode");
        }
        request.setRequestStatus(RequestStatus.OPEN_TO_OFFER);
        return RequestResponseDto.fromEntity(request);
    }

    @Override
    public List<RequestResponseDto> findAll(Optional<Integer> pageNumber, Optional<Integer> itemsInPage) {
        PageRequest pageRequest = PageRequest.of(pageNumber.orElse(0), itemsInPage.orElse(10));
        List<Request> requests = requestRepository.findAll(pageRequest).getContent();
        return requests.stream().map(RequestResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public RequestResponseDto findById(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException(String.format("there is not request with id %d", id)));
        return RequestResponseDto.fromEntity(request);
    }

    @Transactional
    @Override
    public RequestResponseDto chooseOffer(String clientEmail, Long offerId, Long requestId) {
        Request request = requestRepository.findByIdAndRequestStatusAndClient_Email(requestId, RequestStatus.OPEN_TO_OFFER, clientEmail)
                .orElseThrow(() -> new RequestNotFoundException(String.format("you don't have an open request with id %d", requestId)));

        Offer choseOffer = null;
        for (Offer offer : request.getOffers()) {
            if (offer.getId().equals(offerId)) {
                choseOffer = offer;
                break;
            }
        }
        if (choseOffer == null) {
            throw new OfferNotFoundException(String.format("there is no offer with id %d for request %d", offerId, requestId));
        }

        if (request.getClient().getBalance() < choseOffer.getRequestedAmount()) {
            throw new BalanceIsNotEnoughException(String.format("your balance is not enough, you need at least %d mode", (choseOffer.getRequestedAmount() - request.getClient().getBalance())));
        }

        request.setRequestStatus(RequestStatus.OFFER_CHOSE);
        request.setAcceptedOffer(choseOffer);
        request.getClient().setBalance(request.getClient().getBalance() - request.getAcceptedOffer().getRequestedAmount());

        return RequestResponseDto.fromEntity(request);
    }

    @Transactional
    @Override
    public RequestResponseDto doneRequest(String clientEmail, Long requestId) {
        Request request = requestRepository.findByIdAndRequestStatusAndClient_Email(requestId, RequestStatus.OFFER_CHOSE, clientEmail)
                .orElseThrow(() -> new RequestNotFoundException(String.format("you don't have offer chose request with id %d", requestId)));

        request.setRequestStatus(RequestStatus.DONE);
        Expert expert = request.getAcceptedOffer().getExpert();
        expert.setBalance(expert.getBalance() + request.getAcceptedOffer().getRequestedAmount());
        return RequestResponseDto.fromEntity(request);
    }
}
