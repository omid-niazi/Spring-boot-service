package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.category.CategoryDto;
import org.maktab.houseservice.model.entity.ServiceCategory;

import java.util.List;

public interface ServiceCategoryService {
    CategoryDto createCategory(String name);

    CategoryDto updateName(Long id, String name);

    List<CategoryDto> findAll();
}
