package com.maniek.medicalclinic.mapper;

import com.maniek.medicalclinic.model.dto.PatientDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import com.maniek.medicalclinic.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(source = "visits", target = "visitIds", qualifiedByName = "mapVisits")
    PatientDTO mapToPatientDTO(Patient patient);

    List<PatientDTO> mapToListPatientDTO(List<Patient> patients);

    @Named("mapVisits")
    default Set<String> mapVisits(Set<Visit> visits) {
        return visits.stream()
                .map(visit -> visit.getId().toString())
                .collect(Collectors.toSet());
    }
}
