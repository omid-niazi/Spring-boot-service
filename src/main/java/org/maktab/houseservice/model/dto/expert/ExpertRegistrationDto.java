package org.maktab.houseservice.model.dto.expert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.maktab.houseservice.model.entity.Expert;
import org.maktab.houseservice.model.entity.ExpertAccountStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpertRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    @NotNull
    @Size(min = 8)
    private String password;
    private String photoUrl;

    public Expert toEntity() {
        return new Expert(getFirstName(), getLastName(), getEmail(), getPassword(), ExpertAccountStatus.WAITING_FOR_EMAIL_CONFIRMATION, getPhotoUrl(), new HashSet<>(), 0L);
    }
}
