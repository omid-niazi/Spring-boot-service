package org.maktab.houseservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Request {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "request")
    private Set<Offer> offers = new HashSet<>();

    @OneToOne
    private Offer acceptedOffer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @NotNull
    @PositiveOrZero
    private Long offerAmount;

    @NotNull
    @Size(min = 4, max = 48)
    private String title;

    @NotNull
    @Size(min = 16, max = 512)
    private String description;

    @NotNull
    @Future
    private LocalDateTime doingTime;

    @ManyToOne
    private Service requiredSkill;

    public Request(Client client, RequestStatus requestStatus, Long offerAmount, String title, String description, LocalDateTime doingTime, Service requiredSkill) {
        this.client = client;
        this.requestStatus = requestStatus;
        this.offerAmount = offerAmount;
        this.title = title;
        this.description = description;
        this.doingTime = doingTime;
        this.requiredSkill = requiredSkill;
    }
}
