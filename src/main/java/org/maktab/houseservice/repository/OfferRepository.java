package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.Expert;
import org.maktab.houseservice.model.entity.Offer;
import org.maktab.houseservice.model.entity.Request;
import org.maktab.houseservice.model.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Boolean existsByExpertAndRequest(Expert expert, Request request);

    Optional<Offer> findByIdAndRequest_Client_EmailAndRequest_RequestStatus(Long id, String clientEmail, RequestStatus requestStatus);

    Optional<Offer> findByIdAndExpert_EmailAndRequest_RequestStatus(Long id, String expertEmail, RequestStatus requestStatus);

}
