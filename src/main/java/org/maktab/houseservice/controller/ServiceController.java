package org.maktab.houseservice.controller;

import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.service.CreateServiceRequestDto;
import org.maktab.houseservice.model.dto.service.ServiceResponseDto;
import org.maktab.houseservice.model.entity.Service;
import org.maktab.houseservice.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/service")
public class ServiceController {

    private final ServiceService serviceService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createService(@RequestBody CreateServiceRequestDto createServiceRequestDto) {
        ServiceResponseDto createdService = serviceService.create(createServiceRequestDto);
        ApiResponse responseBody = SuccessApiResponse.withData(createdService);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateService(@RequestParam Long id, @RequestParam Optional<String> name, Optional<String> description, Optional<Long> basePrice) {
        ServiceResponseDto service = serviceService.updateService(id, name, description, basePrice);
        ApiResponse responseBody = SuccessApiResponse.withData(service);
        return ResponseEntity.ok(responseBody);
    }
}
