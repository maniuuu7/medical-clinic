package com.maniek.medicalclinic.model.dto;

import com.maniek.medicalclinic.model.Specialization;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DoctorDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Specialization specialization;
}
