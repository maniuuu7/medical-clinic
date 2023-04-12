package com.maniek.medicalclinic.mapper;

import com.maniek.medicalclinic.model.dto.VisitDTO;
import com.maniek.medicalclinic.model.entity.Visit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    VisitDTO mapToVisitDTO(Visit visit);

    List<VisitDTO> mapToListVisitDTO(List<Visit> visits);
}
