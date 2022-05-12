package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.DuplicateEmailException;
import org.maktab.houseservice.exception.InvalidInputException;
import org.maktab.houseservice.exception.ServiceNotFoundException;
import org.maktab.houseservice.model.dto.expert.ExpertRegistrationDto;
import org.maktab.houseservice.model.dto.expert.ExpertResponseDto;
import org.maktab.houseservice.model.entity.Expert;
import org.maktab.houseservice.model.entity.ExpertAccountStatus;
import org.maktab.houseservice.repository.ExpertRepository;
import org.maktab.houseservice.repository.ServiceRepository;
import org.maktab.houseservice.repository.UserRepository;
import org.maktab.houseservice.service.ExpertService;
import org.maktab.houseservice.service.VerificationTokenService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultExpertService implements ExpertService {

    private final ExpertRepository expertRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;

    @Transactional
    @Override
    public ExpertResponseDto create(ExpertRegistrationDto expertDto) {
        Boolean expertAlreadyExists = userRepository.existsByEmail(expertDto.getEmail());
        if (expertAlreadyExists) {
            throw new DuplicateEmailException(String.format("email %s has been used before", expertDto.getEmail()));
        }

        Expert expert = new Expert(expertDto.getFirstName(), expertDto.getLastName(), expertDto.getEmail(), passwordEncoder.encode(expertDto.getPassword()), ExpertAccountStatus.WAITING_FOR_EMAIL_CONFIRMATION, expertDto.getPhotoUrl(), new HashSet<>(), 0L);
        expertRepository.save(expert);
        verificationTokenService.sendVerificationTo(expert);
        return ExpertResponseDto.fromEntity(expert);
    }

    @Override
    public List<ExpertResponseDto> getAllExperts(Pageable pageable) {
        return expertRepository.findAll(pageable).getContent().stream().map(ExpertResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ExpertResponseDto getExpertByEmail(String email) {
        Expert expert = expertRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("there is not expert with email %s", email)));
        return ExpertResponseDto.fromEntity(expert);
    }

    @Override
    public List<ExpertResponseDto> search(String firstName, String lastName, String email, String skillName) {
        Example<Expert> expertExample = createExample(firstName, lastName, email);
        List<Expert> matchedExperts = expertRepository.findAll(expertExample);
        if (skillName == null || skillName.isBlank()) {
            return matchedExperts.stream().map(ExpertResponseDto::fromEntity).collect(Collectors.toList());
        }

        Optional<org.maktab.houseservice.model.entity.Service> skill = serviceRepository.findByServiceName(skillName);
        if (skill.isEmpty()) {
            return new ArrayList<>();
        }
        matchedExperts.removeIf(item -> !item.getSkills().contains(skill.get()));
        return matchedExperts.stream().map(ExpertResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ExpertResponseDto deleteAccount(String email) {
        Expert expert = expertRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("email %s doesn't exists", email)));
        expert.setAccountStatus(ExpertAccountStatus.DELETED);
        expert.setActive(false);
        expertRepository.save(expert);
        return ExpertResponseDto.fromEntity(expert);
    }

    @Override
    @Transactional
    public ExpertResponseDto addUserSkills(String name, Set<Long> skillsId) {

        Set<org.maktab.houseservice.model.entity.Service> skills = getSkillsById(skillsId);

        // update expert skills
        Expert loadedExpert = expertRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("email %s doesn't exists", name)));
        loadedExpert.addSkills(skills);

        return ExpertResponseDto.fromEntity(loadedExpert);

    }

    @Override
    @Transactional
    public ExpertResponseDto removeUserSkills(String name, Set<Long> skillsId) {
        Set<org.maktab.houseservice.model.entity.Service> skills = getSkillsById(skillsId);

        // update expert skills
        Expert loadedExpert = expertRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("email %s doesn't exists", name)));
        loadedExpert.removeSkills(skills);


        return ExpertResponseDto.fromEntity(loadedExpert);

    }

    @Override
    public List<ExpertResponseDto> getUnapprovedExperts() {
        List<Expert> experts = expertRepository.findByAccountStatus(ExpertAccountStatus.WAITING_FOR_ADMIN_APPROVE);
        return experts.stream().map(ExpertResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ExpertResponseDto approveExpertByAdmin(Long id) {
        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user with id %d not found", id)));
        expert.setAccountStatus(ExpertAccountStatus.ACTIVE);
        expert.setActive(true);
        return ExpertResponseDto.fromEntity(expert);
    }

    @Override
    public Expert findByEmail(String expertEmail) {
        return expertRepository.findByEmail(expertEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("there is no expert with email %d", expertEmail)));
    }

    @Transactional
    @Override
    public ExpertResponseDto update(String expertEmail, ExpertRegistrationDto expertRegistrationDto) {
        Expert expert = expertRepository.findByEmail(expertEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("there is not expert with email %s", expertEmail)));
        if (expertRegistrationDto.getFirstName() != null) {
            expert.setFirstname(expertRegistrationDto.getFirstName());
        }

        if (expertRegistrationDto.getLastName() != null) {
            expert.setLastname(expertRegistrationDto.getLastName());
        }

        if (expertRegistrationDto.getPhotoUrl() != null) {
            expert.setPhotoUrl(expertRegistrationDto.getPhotoUrl());
        }
        if (expertRegistrationDto.getPassword() != null) {
            expert.setPassword(passwordEncoder.encode(expertRegistrationDto.getPassword()));
        }
        return ExpertResponseDto.fromEntity(expert);
    }

    private Example<Expert> createExample(String firstName, String lastName, String email) {
        Expert expert = new Expert();
        expert.setFirstname(firstName);
        expert.setLastname(lastName);
        expert.setEmail(email);
        expert.setAccountStatus(ExpertAccountStatus.ACTIVE);
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase("firstName", "lastName", "email")
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Expert> expertExample = Example.of(expert, matcher);
        return expertExample;
    }

    private Set<org.maktab.houseservice.model.entity.Service> getSkillsById(Set<Long> ids) {
        Set<org.maktab.houseservice.model.entity.Service> skillSet = new HashSet<>();
        for (Long id : ids) {
            skillSet.add(serviceRepository.findById(id)
                    .orElseThrow(() -> new ServiceNotFoundException(String.format("there is no skill with id %d", id))));
        }
        return skillSet;
    }

}
