package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.CategoryNotFoundException;
import org.maktab.houseservice.exception.DuplicateServiceNameException;
import org.maktab.houseservice.exception.ServiceNotFoundException;
import org.maktab.houseservice.model.dto.service.CreateServiceRequestDto;
import org.maktab.houseservice.model.dto.service.ServiceResponseDto;
import org.maktab.houseservice.model.entity.ServiceCategory;
import org.maktab.houseservice.repository.CategoryRepository;
import org.maktab.houseservice.repository.ServiceRepository;
import org.maktab.houseservice.service.ServiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultServiceService implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;


    @Transactional
    @Override
    public ServiceResponseDto create(CreateServiceRequestDto serviceDto) {
        ServiceCategory category = categoryRepository.findById(serviceDto.getCategoryId()).
                orElseThrow(() -> new CategoryNotFoundException(String.format("there is no category with id %d", serviceDto.getCategoryId())));

        Boolean serviceAlreadyExists = serviceRepository.existsByServiceName(serviceDto.getServiceName());
        if (serviceAlreadyExists) {
            throw new DuplicateServiceNameException(String.format("name %s is not available", serviceDto.getServiceName()));
        }
        org.maktab.houseservice.model.entity.Service service = new org.maktab.houseservice.model.entity.Service(null, serviceDto.getServiceName(), category, serviceDto.getBasePrice(), serviceDto.getDescription());
        serviceRepository.save(service);
        return ServiceResponseDto.fromEntity(service);
    }

    @Transactional
    @Override
    public ServiceResponseDto updateService(Long id, Optional<String> name, Optional<String> description, Optional<Long> basePrice) {
        org.maktab.houseservice.model.entity.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(String.format("service with id %d doesn't exists", id)));
        name.ifPresent(service::setServiceName);
        description.ifPresent(service::setDescription);
        basePrice.ifPresent(service::setBasePrice);
        return ServiceResponseDto.fromEntity(service);
    }

    @Override
    public org.maktab.houseservice.model.entity.Service findServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(String.format("there is no service with id %d", id)));
    }
}
