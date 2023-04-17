package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.dto.VisitDTO;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Visit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime term;
    @ManyToOne
    private Patient patient;

    public static Visit from(VisitDTO visitDTO) {
        return new Visit(null, visitDTO.getTerm(), null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Visit visit = (Visit) o;
        return this.id != null && super.equals(visit);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

