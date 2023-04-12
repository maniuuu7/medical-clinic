package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.mapper.FacilityMapper;
import com.maniek.medicalclinic.model.dto.FacilityDTO;
import com.maniek.medicalclinic.model.entity.Doctor;
import com.maniek.medicalclinic.model.entity.Facility;
import com.maniek.medicalclinic.repository.DoctorRepository;
import com.maniek.medicalclinic.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final DoctorRepository doctorRepository;
    private final FacilityMapper facilityMapper;

    public String assignDoctor(Long doctorId, Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(IllegalArgumentException::new);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(IllegalArgumentException::new);
        doctor.getFacilities().add(facility);
        doctorRepository.save(doctor);
        return "Doctor assigned to facility";
    }

    public FacilityDTO addFacility(FacilityDTO facilityDTO) {
        facilityRepository.save(Facility.from(facilityDTO));
        return facilityDTO;
    }
}
