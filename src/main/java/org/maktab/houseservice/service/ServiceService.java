package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.service.CreateServiceRequestDto;
import org.maktab.houseservice.model.dto.service.ServiceResponseDto;
import org.maktab.houseservice.model.entity.Service;

import java.util.Optional;

public interface ServiceService {
    ServiceResponseDto create(CreateServiceRequestDto createServiceRequestDto);

    ServiceResponseDto updateService(Long id, Optional<String> name, Optional<String> description, Optional<Long> basePrice);

    Service findServiceById(Long id);
}
