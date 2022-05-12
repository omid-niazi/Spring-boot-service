package org.maktab.houseservice.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Admin extends AppUser {
    public Admin(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password, UserRole.ADMIN, true);
    }

    public Admin(Long id, String firstname, String lastname, String email, String password) {
        super(id, firstname, lastname, email, password, UserRole.ADMIN, true);
    }
}
