package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Boolean existsByEmail(String email);
}
