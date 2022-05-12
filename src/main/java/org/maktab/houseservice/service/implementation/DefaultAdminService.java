package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.DuplicateEmailException;
import org.maktab.houseservice.model.dto.admin.AdminRegistrationRequestDto;
import org.maktab.houseservice.model.dto.admin.AdminResponseDto;
import org.maktab.houseservice.model.entity.Admin;
import org.maktab.houseservice.repository.AdminRepository;
import org.maktab.houseservice.repository.UserRepository;
import org.maktab.houseservice.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultAdminService implements AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AdminResponseDto create(AdminRegistrationRequestDto adminDto) {
        Boolean adminAlreadyExists = userRepository.existsByEmail(adminDto.getEmail());
        if (adminAlreadyExists) {
            throw new DuplicateEmailException(String.format("email %s have been registered before", adminDto.getEmail()));
        }

        Admin admin = new Admin(adminDto.getFirstname(), adminDto.getLastname(), adminDto.getEmail(), passwordEncoder.encode(adminDto.getPassword()));
        Admin savedAdmin = adminRepository.save(admin);
        return AdminResponseDto.fromEntity(savedAdmin);
    }
}
