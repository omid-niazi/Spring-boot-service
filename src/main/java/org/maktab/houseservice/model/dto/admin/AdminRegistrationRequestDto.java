package org.maktab.houseservice.model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.entity.Admin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class AdminRegistrationRequestDto {
    private String firstname;
    private String lastname;
    private String email;
    @NotNull
    @Size(min = 8)
    private String password;

    public AdminRegistrationRequestDto fromEntity(Admin admin) {
        return new AdminRegistrationRequestDto(admin.getFirstname(), admin.getLastname(), admin.getEmail(), admin.getPassword());
    }
}
