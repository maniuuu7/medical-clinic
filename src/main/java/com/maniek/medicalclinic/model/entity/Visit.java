package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.dto.VisitDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Visit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisit;
    private LocalDateTime term;
    @ManyToOne
    private Patient patient;

    public static Visit from(VisitDTO visitDTO) {
        return new Visit(null, visitDTO.getTerm(), null);
    }
}
