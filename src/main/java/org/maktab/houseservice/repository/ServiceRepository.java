package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Boolean existsByServiceName(String name);

    Optional<Service> findByServiceName(String serviceName);
}
