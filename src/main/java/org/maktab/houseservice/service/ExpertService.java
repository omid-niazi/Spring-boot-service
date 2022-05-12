package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.expert.ExpertRegistrationDto;
import org.maktab.houseservice.model.dto.expert.ExpertResponseDto;
import org.maktab.houseservice.model.entity.Expert;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ExpertService {
    ExpertResponseDto create(ExpertRegistrationDto expertRegistrationDto);

    List<ExpertResponseDto> getAllExperts(Pageable pageable);

    ExpertResponseDto getExpertByEmail(String email);

    List<ExpertResponseDto> search(String firstName, String lastName, String email, String skill);

    ExpertResponseDto deleteAccount(String name);

    ExpertResponseDto addUserSkills(String email, Set<Long> skillsId);

    ExpertResponseDto removeUserSkills(String email, Set<Long> skillsId);

    List<ExpertResponseDto> getUnapprovedExperts();

    ExpertResponseDto approveExpertByAdmin(Long id);

    Expert findByEmail(String expertEmail);

    ExpertResponseDto update(String expertEmail, ExpertRegistrationDto expertRegistrationDto);
}
