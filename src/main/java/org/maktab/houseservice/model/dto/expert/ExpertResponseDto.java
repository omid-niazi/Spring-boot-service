package org.maktab.houseservice.model.dto.expert;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.dto.service.ServiceResponseDto;
import org.maktab.houseservice.model.entity.Expert;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class ExpertResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<ServiceResponseDto> skills;
    private String photoUrl;
    private Boolean isActive;

    public static ExpertResponseDto fromEntity(Expert expert) {
        List<ServiceResponseDto> skillsDto = expert.getSkills().stream().map(ServiceResponseDto::fromEntity)
                .collect(Collectors.toList());
        return new ExpertResponseDto(expert.getId(), expert.getFirstname(),
                expert.getLastname(),
                expert.getEmail(),
                skillsDto,
                expert.getPhotoUrl(), expert.getActive());
    }
}
