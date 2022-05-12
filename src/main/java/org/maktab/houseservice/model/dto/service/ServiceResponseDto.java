package org.maktab.houseservice.model.dto.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.dto.category.CategoryDto;
import org.maktab.houseservice.model.entity.Service;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class ServiceResponseDto {
    private Long id;
    private String serviceName;
    private CategoryDto serviceCategoryDto;
    private String description;
    private Long basePrice;

    public Service toEntity() {
        return new Service(getId(), getServiceName(), serviceCategoryDto.toEntity(), getBasePrice(), getDescription());
    }

    public static ServiceResponseDto fromEntity(Service service) {
        return new ServiceResponseDto(service.getId(), service.getServiceName(),
                CategoryDto.fromEntity(service.getCategory()),
                service.getDescription(),
                service.getBasePrice());
    }
}
