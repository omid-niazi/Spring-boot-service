package org.maktab.houseservice.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Client extends AppUser {
    @NotNull
    @Enumerated(EnumType.STRING)
    private ClientAccountStatus registrationStatus;

    @OneToMany(mappedBy = "client")
    private Set<Request> requests;

    @NotNull
    @PositiveOrZero
    private Long balance;

    public Client(String firstname, String lastname, String email, String password, ClientAccountStatus registrationStatus, Set<Request> requests, Long balance) {
        super(firstname, lastname, email, password, UserRole.CLIENT, registrationStatus == ClientAccountStatus.ACTIVE);
        this.registrationStatus = registrationStatus;
        this.requests = requests;
        this.balance = balance;
    }

    public Client(Long id, String firstname, String lastname, String email, String password, ClientAccountStatus registrationStatus, Set<Request> requests, Long balance) {
        super(id, firstname, lastname, email, password, UserRole.CLIENT, registrationStatus == ClientAccountStatus.ACTIVE);
        this.registrationStatus = registrationStatus;
        this.requests = requests;
        this.balance = balance;
    }
}
