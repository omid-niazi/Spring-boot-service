package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);
}
