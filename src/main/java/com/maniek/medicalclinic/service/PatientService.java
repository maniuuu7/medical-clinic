package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.PatientIllegalArgumentException;
import com.maniek.medicalclinic.exception.PatientNotFoundException;
import com.maniek.medicalclinic.mapper.PatientMapper;
import com.maniek.medicalclinic.model.dto.PatientDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import com.maniek.medicalclinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PatientService {
    private PatientRepository patientRepository;
    private PatientMapper patientMapper;


    public List<PatientDTO> showPatient() {
        return patientMapper.mapToListPatientDTO(patientRepository.findAll());
    }

    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
        return patientMapper.mapToPatientDTO(patient);
    }

    public Optional<PatientDTO> addPatient(Patient patient) {
        Optional<Patient> patient1 = patientRepository.findByEmail(patient.getEmail());
        if (patient1.isPresent()) {
            throw new PatientIllegalArgumentException("Error during patient creation. Patient with given email exists");
        }
        if (!validatePatient(patient)) {
            throw new PatientIllegalArgumentException("Incorrect patient data");
        }
        Patient entity = patientRepository.save(patient);
        return Optional.ofNullable(patientMapper.mapToPatientDTO(entity));
    }

    public PatientDTO deletePatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
        patientRepository.delete(patient);
        return patientMapper.mapToPatientDTO(patient);
    }

    public PatientDTO editPatient(String email, Patient editInfo) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
        if (!validatePatient(editInfo) || !validatePatientEdit(editInfo, patient)) {
            throw new PatientIllegalArgumentException("Incorrect patient data");
        }
        patient.setEmail(editInfo.getEmail());
        patient.setFirstName(editInfo.getFirstName());
        patient.setLastName(editInfo.getLastName());
        patient.setBirthday(editInfo.getBirthday());
        patient.setPhoneNumber(editInfo.getPhoneNumber());
        patient.setPassword(editInfo.getPassword());
        patientRepository.save(patient);
        return patientMapper.mapToPatientDTO(patient);
    }

    private boolean validatePatientEdit(Patient editInfo, Patient patientToEdit) {
        Optional<Patient> patient = patientRepository.findByEmail(editInfo.getEmail());
        if (patient.isPresent() && !editInfo.getEmail().equals(patientToEdit.getEmail())) {
            return false;
        }
        if (!patientToEdit.getIdCardNo().equals(editInfo.getIdCardNo())) {
            return false;
        }
        return true;
    }

    public String editPassword(String email, String password) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
        if (!validatePatient(patient)) {
            throw new PatientIllegalArgumentException("Incorrect patient data");
        }
        patient.setPassword(password);
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
        if (patient.getPassword() == null) {
            return false;
        }
        return true;
    }
}
