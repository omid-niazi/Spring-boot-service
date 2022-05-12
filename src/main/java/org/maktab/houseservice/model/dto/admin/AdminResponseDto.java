package org.maktab.houseservice.model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.entity.Admin;

@AllArgsConstructor
@Data
public class AdminResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Boolean isActive;

    public static AdminResponseDto fromEntity(Admin admin) {
        return new AdminResponseDto(admin.getId(), admin.getFirstname(), admin.getLastname(), admin.getEmail(), admin.getActive());
    }

}
