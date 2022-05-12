package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);
}
