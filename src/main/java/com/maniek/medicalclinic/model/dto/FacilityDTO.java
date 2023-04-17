package com.maniek.medicalclinic.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FacilityDTO {
    private String name;
    private String city;
    private String postCode;
    private String street;
    private Integer flatNumber;
}
