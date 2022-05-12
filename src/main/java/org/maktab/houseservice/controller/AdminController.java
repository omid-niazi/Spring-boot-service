package org.maktab.houseservice.controller;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.admin.AdminRegistrationRequestDto;
import org.maktab.houseservice.model.dto.admin.AdminResponseDto;
import org.maktab.houseservice.model.entity.Admin;
import org.maktab.houseservice.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody AdminRegistrationRequestDto adminDto) {
        AdminResponseDto adminResponseDto = adminService.create(adminDto);
        ApiResponse apiResponse = SuccessApiResponse.withData(adminResponseDto);
        return ResponseEntity.ok(apiResponse);
    }
}
