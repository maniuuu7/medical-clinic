package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.dto.FacilityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String postCode;
    private String street;
    private Integer flatNumber;
    @ManyToMany(mappedBy = "facilities")
    private Set<Doctor> doctors;

    public static Facility from(FacilityDTO facilityDTO) {
        return new Facility(null, facilityDTO.getName(), facilityDTO.getCity(), facilityDTO.getPostCode()
                , facilityDTO.getStreet(), facilityDTO.getFlatNumber(), new HashSet<>());
    }
}
