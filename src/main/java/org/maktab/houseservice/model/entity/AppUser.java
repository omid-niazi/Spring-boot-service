package org.maktab.houseservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 4, max = 16)
    private String firstname;
    @NotNull
    @Size(min = 4, max = 16)
    private String lastname;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDateTime joinDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean active;

    public AppUser() {
    }

    public AppUser(String firstname, String lastname, String email, String password, UserRole userRole, Boolean isActive) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.active = isActive;
    }

    public AppUser(Long id, String firstname, String lastname, String email, String password, UserRole userRole, Boolean isActive) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.active = isActive;

    }
}
