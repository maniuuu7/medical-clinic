package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.VisitIllegalArgumentException;
import com.maniek.medicalclinic.mapper.VisitMapper;
import com.maniek.medicalclinic.mapper.VisitMapperImpl;
import com.maniek.medicalclinic.model.dto.VisitDTO;
import com.maniek.medicalclinic.model.entity.Visit;
import com.maniek.medicalclinic.repository.PatientRepository;
import com.maniek.medicalclinic.repository.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class VisitServiceTest {

    VisitRepository visitRepository;
    VisitService visitService;
    VisitMapper visitMapper;
    PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        this.visitRepository = mock(VisitRepository.class);
        this.visitMapper = new VisitMapperImpl();
        this.patientRepository = mock(PatientRepository.class);
        this.visitService = new VisitService(visitRepository, visitMapper, patientRepository);
    }

    @Test
    void addVisit_CorrectData_VisitAdded() {
        Visit visit = new Visit(null, LocalDateTime.of(2023, 12, 10, 10, 15, 0, 0), null);
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.of(2023, 12, 10, 10, 15, 0, 0));
        when(visitRepository.save(visit)).thenReturn(visit);
        when(visitRepository.findByTerm(any())).thenReturn(Optional.empty());
        VisitDTO result = visitService.addVisit(visitDTO);

        Assertions.assertEquals(visitDTO, result);
    }

    @Test
    void addVisit_InCorrectData_VisitNotAdded() {
        Visit visit = new Visit(null, LocalDateTime.of(2023, 12, 10, 10, 15, 0, 0), null);
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.of(2023, 12, 10, 10, 0, 0, 0));
        when(visitRepository.findByTerm(any())).thenReturn(Optional.ofNullable(visit));
        VisitIllegalArgumentException ex = Assertions.assertThrows(VisitIllegalArgumentException.class, () -> visitService.addVisit(visitDTO));

        Assertions.assertEquals("Illegal visit ", ex.getMessage());
    }

    @Test
    void addVisit_InCorrectTime_VisitNotAdded() {
        Visit visit = new Visit(null, LocalDateTime.of(2023, 12, 10, 10, 15, 0, 0), null);
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.of(2023, 12, 9, 10, 15, 0, 0));
        when(visitRepository.findByTerm(any())).thenReturn(Optional.ofNullable(visit));
        VisitIllegalArgumentException ex = Assertions.assertThrows(VisitIllegalArgumentException.class, () -> visitService.addVisit(visitDTO));

        Assertions.assertEquals("Illegal visit ", ex.getMessage());
    }

    @Test
    void addVisit_InCorrectTime15_VisitNotAdded() {
        Visit visit = new Visit(null, LocalDateTime.of(2023, 12, 10, 10, 15, 0, 0), null);
        VisitDTO visitDTO = new VisitDTO(LocalDateTime.of(2023, 12, 10, 10, 8, 0, 0));
        when(visitRepository.findByTerm(any())).thenReturn(Optional.ofNullable(visit));
        VisitIllegalArgumentException ex = Assertions.assertThrows(VisitIllegalArgumentException.class, () -> visitService.addVisit(visitDTO));

        Assertions.assertEquals("Illegal visit ", ex.getMessage());
    }
}
