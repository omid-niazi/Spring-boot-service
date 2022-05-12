package org.maktab.houseservice.model.dto.service;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.dto.category.CategoryDto;
import org.maktab.houseservice.model.dto.category.CreateCategoryRequestDto;
import org.maktab.houseservice.model.entity.Service;

@AllArgsConstructor
@Data
public class CreateServiceRequestDto {
    private String serviceName;
    private Long categoryId;
    private String description;
    private Long basePrice;

}
