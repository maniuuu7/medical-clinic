package com.maniek.medicalclinic.model.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PatientDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
}
