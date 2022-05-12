package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ServiceCategory, Long> {
    Boolean existsByCategoryName(String categoryName);
}
