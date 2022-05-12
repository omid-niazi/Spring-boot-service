package org.maktab.houseservice.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.entity.ServiceCategory;

import javax.validation.constraints.Size;
import java.util.HashSet;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @Size(min = 4, max = 64)
    private String categoryName;

    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public ServiceCategory toEntity() {
        return new ServiceCategory(getId(), getCategoryName(), new HashSet<>());
    }

    public static CategoryDto fromEntity(ServiceCategory serviceCategory) {
        return new CategoryDto(serviceCategory.getId(), serviceCategory.getCategoryName());
    }
}
