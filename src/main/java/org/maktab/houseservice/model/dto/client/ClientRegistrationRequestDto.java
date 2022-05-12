package org.maktab.houseservice.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.houseservice.model.entity.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientRegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    @NotNull
    @Size(min = 8)
    private String password;

    public Client toEntity() {
        return new Client(getFirstName(), getLastName(), getEmail(), getPassword(), ClientAccountStatus.WAITING_FOR_EMAIL_CONFIRMATION, new HashSet<>(), 0L);
    }
}
