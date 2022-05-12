package org.maktab.houseservice.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Expert extends AppUser {
    @Enumerated(EnumType.STRING)
    private ExpertAccountStatus accountStatus;
    private String photoUrl;
    @ManyToMany
    @JoinTable(name = "expert_skills")
    private Set<Service> skills = new HashSet<>();
    @NotNull
    @PositiveOrZero
    private Long balance;

    public Expert(String firstname, String lastname, String email, String password, ExpertAccountStatus registrationStatus, String photoUrl, Set<Service> skills, Long balance) {
        super(firstname, lastname, email, password, UserRole.EXPERT, registrationStatus == ExpertAccountStatus.ACTIVE);
        this.accountStatus = registrationStatus;
        this.photoUrl = photoUrl;
        this.skills = skills;
        this.balance = balance;
    }

    public Expert(Long id, String firstname, String lastname, String email, String password, ExpertAccountStatus registrationStatus, String photoUrl, Set<Service> skills, Long balance) {
        super(id, firstname, lastname, email, password, UserRole.EXPERT, registrationStatus == ExpertAccountStatus.ACTIVE);
        this.accountStatus = registrationStatus;
        this.photoUrl = photoUrl;
        this.skills = skills;
        this.balance = balance;
    }

    public void addSkills(Set<Service> skills) {
        this.skills.addAll(skills);
    }

    public void removeSkills(Set<Service> skills) {
        this.skills.removeAll(skills);
    }
}
