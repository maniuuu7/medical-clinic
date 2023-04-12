package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.PatientNotFoundException;
import com.maniek.medicalclinic.exception.VisitIllegalArgumentException;
import com.maniek.medicalclinic.exception.VisitNotFoundException;
import com.maniek.medicalclinic.mapper.VisitMapper;
import com.maniek.medicalclinic.model.dto.VisitDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import com.maniek.medicalclinic.model.entity.Visit;
import com.maniek.medicalclinic.repository.PatientRepository;
import com.maniek.medicalclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final PatientRepository patientRepository;

    public List<Visit> showVisit() {
        return visitRepository.findAll();
    }

    public VisitDTO addVisit(VisitDTO visitDTO) {
        if (visitRepository.findByTerm(visitDTO.getTerm()).isPresent()) {
            throw new VisitIllegalArgumentException("Illegal visit ");
        }
        if (visitDTO.getTerm().isBefore(LocalDateTime.now())) {
            throw new VisitIllegalArgumentException("Illegal visit ");
        }
        if (visitDTO.getTerm().getMinute() % 15 != 0) {
            throw new VisitIllegalArgumentException("Illegal visit ");
        }
        Visit visit = Visit.from(visitDTO);
        Visit entity = visitRepository.save(visit);
        return visitMapper.mapToVisitDTO(entity);
    }

    public String assignPatientToVisit(Long visitId, Long patientId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitNotFoundException("Illegal visit"));
        Patient patient = patientRepository.findById(patientId).orElseThrow(PatientNotFoundException::new);
        if (visit.getPatient() != null) {
            throw new VisitIllegalArgumentException("Visit already assigned to patient");
        }

        visit.setPatient(patient);
        visitRepository.save(visit);
        return "Patient assigned successfully";
    }
}
