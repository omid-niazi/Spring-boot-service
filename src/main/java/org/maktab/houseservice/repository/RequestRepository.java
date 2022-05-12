package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.Request;
import org.maktab.houseservice.model.entity.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByIdAndRequestStatus(Long id, RequestStatus requestStatus);

    Optional<Request> findByIdAndRequestStatusAndClient_Email(Long id, RequestStatus requestStatus, String clientEmail);
}
