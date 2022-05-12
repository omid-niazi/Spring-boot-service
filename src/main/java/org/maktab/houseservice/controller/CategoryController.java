package org.maktab.houseservice.controller;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.category.CategoryDto;
import org.maktab.houseservice.model.entity.ServiceCategory;
import org.maktab.houseservice.service.ServiceCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final ServiceCategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryDto> categoryDtoList = categoryService.findAll();
        ApiResponse responseBody = SuccessApiResponse.withData(categoryDtoList);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestParam @Valid @Size(min = 4, max = 64) String name) {
        CategoryDto category = categoryService.createCategory(name);
        ApiResponse responseBody = SuccessApiResponse.withData(category);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponse> updateCategory(@RequestParam Long id, @RequestParam @Valid @Size(min = 4, max = 64) String newName) {
        CategoryDto category = categoryService.updateName(id, newName);
        ApiResponse responseBody = SuccessApiResponse.withData(category);
        return ResponseEntity.ok(responseBody);
    }

}
