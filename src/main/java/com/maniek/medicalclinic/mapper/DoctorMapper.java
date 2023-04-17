package com.maniek.medicalclinic.mapper;

import com.maniek.medicalclinic.model.dto.DoctorDTO;
import com.maniek.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO mapToDoctorDTO(Doctor doctor);

    Set<DoctorDTO> mapToSetDoctorDTO(Set<Doctor> doctors);
}
