package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.patient.PatientIllegalArgumentException;
import com.maniek.medicalclinic.exception.patient.PatientNotFoundException;
import com.maniek.medicalclinic.mapper.PatientMapper;
import com.maniek.medicalclinic.model.dto.PatientDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import com.maniek.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final PasswordEncoder passwordEncoder;

    public List<PatientDTO> showPatient() {
        return patientMapper.mapToListPatientDTO(patientRepository.findAll());
    }

    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(PatientNotFoundException::new);
        return patientMapper.mapToPatientDTO(patient);
    }

    public PatientDTO addPatient(Patient patient) {
        Optional<Patient> patient1 = patientRepository.findByEmail(patient.getEmail());
        if (patient1.isPresent()) {
            throw new PatientIllegalArgumentException("Error during patient creation. Patient with given email exists");
        }
        if (!validatePatient(patient)) {
            throw new PatientIllegalArgumentException("Incorrect patient data");
        }
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        Patient entity = patientRepository.save(patient);
        return patientMapper.mapToPatientDTO(entity);
    }

    public PatientDTO deletePatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(PatientNotFoundException::new);
        patientRepository.delete(patient);
        return patientMapper.mapToPatientDTO(patient);
    }

    public PatientDTO editPatient(String email, Patient editInfo) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(PatientNotFoundException::new);
        if (!validatePatient(editInfo) || !validatePatientEdit(editInfo, patient)) {
            throw new PatientIllegalArgumentException("Incorrect patient data");
        }
        editInfo.setPassword(passwordEncoder.encode(editInfo.getPassword()));
        patient.update(editInfo);
        patientRepository.save(patient);
        return patientMapper.mapToPatientDTO(patient);
    }

    private boolean validatePatientEdit(Patient editInfo, Patient patientToEdit) {
        Optional<Patient> patient = patientRepository.findByEmail(editInfo.getEmail());
        if (patient.isPresent() && !editInfo.getEmail().equals(patientToEdit.getEmail())) {
            return false;
        }
        return patientToEdit.getIdCardNo().equals(editInfo.getIdCardNo());
    }

    public String editPassword(String email, String password) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(PatientNotFoundException::new);
        if (!validatePatient(patient)) {
            throw new PatientIllegalArgumentException("Incorrect patient data");
        }
        patient.setPassword(passwordEncoder.encode(password));
        patientRepository.save(patient);
        return patient.getPassword();
    }

    public boolean validatePatient(Patient patient) {
        if (patient.getEmail() == null) {
            return false;
        }
        if (patient.getFirstName() == null) {
            return false;
        }
        if (patient.getIdCardNo() == null) {
            return false;
        }
        if (patient.getBirthday() == null) {
            return false;
        }
        if (patient.getLastName() == null) {
            return false;
        }
        if (patient.getPhoneNumber() == null) {
            return false;
        }
        return patient.getPassword() != null;
    }
}
