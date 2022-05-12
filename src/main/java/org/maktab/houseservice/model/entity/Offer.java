package org.maktab.houseservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Request request;

    @ManyToOne
    private Expert expert;

    @CreationTimestamp
    private LocalDateTime submitOfferTime;

    @NotNull
    @PositiveOrZero
    private Long requestedAmount;

    @NotNull
    @Positive
    private Integer requiredDuration;

    @NotNull
    @Future
    private LocalDateTime startTime;


    public Offer(Request request, Expert expert, Long requestedAmount, Integer requiredDuration, LocalDateTime startTime) {
        this.request = request;
        this.expert = expert;
        this.requestedAmount = requestedAmount;
        this.requiredDuration = requiredDuration;
        this.startTime = startTime;
    }
}
