package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.admin.AdminRegistrationRequestDto;
import org.maktab.houseservice.model.dto.admin.AdminResponseDto;

public interface AdminService {

    AdminResponseDto create(AdminRegistrationRequestDto adminDto);

}
