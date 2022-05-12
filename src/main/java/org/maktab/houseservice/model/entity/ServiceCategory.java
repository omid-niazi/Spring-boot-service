package org.maktab.houseservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceCategory {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 4, max = 64)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private Set<Service> services = new HashSet<>();


    public ServiceCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
