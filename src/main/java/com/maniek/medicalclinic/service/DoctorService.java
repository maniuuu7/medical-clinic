package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.doctor.DoctorNotFoundException;
import com.maniek.medicalclinic.exception.facility.FacilityNotFoundException;
import com.maniek.medicalclinic.mapper.DoctorMapper;
import com.maniek.medicalclinic.model.dto.DoctorDTO;
import com.maniek.medicalclinic.model.entity.Doctor;
import com.maniek.medicalclinic.model.entity.Facility;
import com.maniek.medicalclinic.repository.DoctorRepository;
import com.maniek.medicalclinic.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final FacilityRepository facilityRepository;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;

    public String assignFacility(Long doctorId, Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(FacilityNotFoundException::new);
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(DoctorNotFoundException::new);
        facility.getDoctors().add(doctor);
        facilityRepository.save(facility);
        return "Facility assigned to doctor";
    }

    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {
        doctorDTO.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        doctorRepository.save(Doctor.from(doctorDTO));
        return doctorDTO;
    }
}
