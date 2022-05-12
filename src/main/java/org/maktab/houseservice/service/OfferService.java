package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.offer.CreateOfferDto;
import org.maktab.houseservice.model.dto.offer.OfferDto;

public interface OfferService {
    OfferDto create(String expertEmail, CreateOfferDto createOfferDto);

    OfferDto removeOffer(String expertEmail, Long id);

    OfferDto updateOffer(String expertEmail, Long id, CreateOfferDto createOfferDto);
}
