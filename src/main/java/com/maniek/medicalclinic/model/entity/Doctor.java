package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.Role;
import com.maniek.medicalclinic.model.Specialization;
import com.maniek.medicalclinic.model.dto.DoctorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Doctor")
public class Doctor extends UserData {

    private String firstName;
    private String lastName;
    private Specialization specialization;
    @ManyToMany
    @JoinTable(name = "doctor_facilities",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private List<Facility> facilities;

    public Doctor(Long id, String email, String password, Role role, String firstName, String lastName,
                  Specialization specialization, List<Facility> facilities) {
        super(id, email, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.facilities = facilities;
    }

    public static Doctor from(DoctorDTO doctorDTO) {
        return new Doctor(null, doctorDTO.getEmail(), doctorDTO.getPassword(), Role.DOCTOR, doctorDTO.getFirstName(),
                doctorDTO.getLastName(), doctorDTO.getSpecialization(), new ArrayList<>());
    }
}
