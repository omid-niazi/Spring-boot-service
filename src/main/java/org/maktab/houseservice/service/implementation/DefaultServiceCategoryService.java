package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.CategoryNotFoundException;
import org.maktab.houseservice.exception.DuplicateCategoryNameException;
import org.maktab.houseservice.exception.InvalidInputException;
import org.maktab.houseservice.model.dto.category.CategoryDto;
import org.maktab.houseservice.model.entity.ServiceCategory;
import org.maktab.houseservice.repository.CategoryRepository;
import org.maktab.houseservice.service.ServiceCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultServiceCategoryService implements ServiceCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryDto createCategory(String name) {
        Boolean categoryNameExisted = categoryRepository.existsByCategoryName(name);
        if (categoryNameExisted) {
            throw new DuplicateCategoryNameException(String.format("category name %s has been used before", name));
        }
        ServiceCategory serviceCategory = new ServiceCategory(name);
        ServiceCategory savedCategory = categoryRepository.save(serviceCategory);
        return CategoryDto.fromEntity(savedCategory);
    }

    @Transactional
    @Override
    public CategoryDto updateName(Long id, String name) {
        ServiceCategory serviceCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("category with id %d doesn't exists", id)));
        serviceCategory.setCategoryName(name);
        return CategoryDto.fromEntity(serviceCategory);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

}
