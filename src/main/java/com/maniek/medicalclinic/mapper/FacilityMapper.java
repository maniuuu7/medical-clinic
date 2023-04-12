package com.maniek.medicalclinic.mapper;

import com.maniek.medicalclinic.model.dto.FacilityDTO;
import com.maniek.medicalclinic.model.entity.Facility;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface FacilityMapper {
    FacilityDTO mapToFacilityDTO(Facility facility);

    Set<FacilityDTO> mapToSetFacilityDTO(Set<Facility> facilities);
}
