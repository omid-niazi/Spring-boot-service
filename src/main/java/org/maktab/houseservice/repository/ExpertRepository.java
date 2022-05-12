package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.Expert;
import org.maktab.houseservice.model.entity.ExpertAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
    Boolean existsByEmail(String email);

    Optional<Expert> findByEmail(String email);


    List<Expert> findByAccountStatus(ExpertAccountStatus expertAccountStatus);
}
