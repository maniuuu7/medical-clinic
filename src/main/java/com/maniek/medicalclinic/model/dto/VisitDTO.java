package com.maniek.medicalclinic.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VisitDTO {

    private LocalDateTime term;

}
