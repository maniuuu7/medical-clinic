package com.maniek.medicalclinic.mapper;

import com.maniek.medicalclinic.model.dto.PatientDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO mapToPatientDTO(Patient patient);

    List<PatientDTO> mapToListPatientDTO(List<Patient> patients);
}
