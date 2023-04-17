package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.dto.FacilityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Facility facility = (Facility) o;
        return this.id != null && super.equals(facility);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", street='" + street + '\'' +
                ", flatNumber=" + flatNumber +
                ", doctors=" + doctors.stream()
                .map(doctor -> doctor.getId().toString())
                .toList();
    }
}
