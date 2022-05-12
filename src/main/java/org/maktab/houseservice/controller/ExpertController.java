package org.maktab.houseservice.controller;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.expert.ExpertRegistrationDto;
import org.maktab.houseservice.model.dto.expert.ExpertResponseDto;
import org.maktab.houseservice.model.entity.Expert;
import org.maktab.houseservice.service.ExpertService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/expert")
public class ExpertController {

    private final ExpertService expertService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllExperts(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemsInPage) {
        Pageable pageable = PageRequest.of(page.orElse(0), itemsInPage.orElse(10));
        List<ExpertResponseDto> expertsList = expertService.getAllExperts(pageable);
        ApiResponse responseBody = SuccessApiResponse.withData(expertsList);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @PutMapping
    public ResponseEntity<ApiResponse> update(Principal principal, @RequestBody @Valid ExpertRegistrationDto expertRegistrationDto) {
        ExpertResponseDto expertResponseDto = expertService.update(principal.getName(), expertRegistrationDto);
        ApiResponse responseBody = SuccessApiResponse.withData(expertResponseDto);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> getExpertByEmail(@PathVariable("email") String email) {
        ExpertResponseDto expert = expertService.getExpertByEmail(email);
        ApiResponse responseBody = SuccessApiResponse.withData(expert);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchExperts(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String email, @RequestParam(required = false) String skill) {
        List<ExpertResponseDto> experts = expertService.search(firstName, lastName, email, skill);
        ApiResponse responseBody = SuccessApiResponse.withData(experts);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid ExpertRegistrationDto expertRegistrationDto) {
        ExpertResponseDto expertResponseDto = expertService.create(expertRegistrationDto);
        ApiResponse apiResponse = SuccessApiResponse.withMessageAndData("check your email for verification link", expertResponseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/waiting-for-approval")
    public ResponseEntity<ApiResponse> getUnapprovedExperts() {
        List<ExpertResponseDto> experts = expertService.getUnapprovedExperts();
        ApiResponse responseBody = SuccessApiResponse.withData(experts);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/approve")
    public ResponseEntity<ApiResponse> approveExpertByAdmin(@RequestParam Long id) {
        ExpertResponseDto approvedExpert = expertService.approveExpertByAdmin(id);
        ApiResponse responseBody = SuccessApiResponse.withData(approvedExpert);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @PutMapping("/skills")
    public ResponseEntity<ApiResponse> addSkillToExpertSkills(Principal principal, @RequestBody Set<Long> skillsId) {
        ExpertResponseDto updatedExpert = expertService.addUserSkills(principal.getName(), skillsId);
        ApiResponse responseBody = SuccessApiResponse.withData(updatedExpert);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @DeleteMapping("/skills")
    public ResponseEntity<ApiResponse> removeSkillFromExpertSkills(Principal principal, @RequestBody Set<Long> skillsId) {
        ExpertResponseDto updatedExpert = expertService.removeUserSkills(principal.getName(), skillsId);
        ApiResponse responseBody = SuccessApiResponse.withData(updatedExpert);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteExpertAccount(Principal principal) {
        ExpertResponseDto expert = expertService.deleteAccount(principal.getName());
        ApiResponse responseBody = SuccessApiResponse.withData(expert);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponse> deleteExpertAccountByEmail(@PathVariable("email") @Valid @Email String email) {
        ExpertResponseDto expert = expertService.deleteAccount(email);
        ApiResponse responseBody = SuccessApiResponse.withData(expert);
        return ResponseEntity.ok(responseBody);
    }
}
