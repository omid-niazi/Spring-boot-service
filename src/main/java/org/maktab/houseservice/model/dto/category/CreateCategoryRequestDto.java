package org.maktab.houseservice.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.entity.ServiceCategory;

import javax.validation.constraints.Size;
import java.util.HashSet;

@Data
public class CreateCategoryRequestDto {
    private String categoryName;

    public CreateCategoryRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public static CreateCategoryRequestDto fromEntity(ServiceCategory serviceCategory) {
        return new CreateCategoryRequestDto(serviceCategory.getCategoryName());
    }
}
