package org.maktab.houseservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Service {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 4, max = 64)
    @Column(unique = true)
    private String serviceName;

    @ManyToOne
    private ServiceCategory category;

    @NotNull
    @PositiveOrZero
    private Long basePrice;

    @NotNull
    @Size(min = 16, max = 512)
    private String description;
}
