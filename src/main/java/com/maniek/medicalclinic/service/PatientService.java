package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.model.Patient;
import com.maniek.medicalclinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PatientService {
    private PatientRepository patientRepository;


    public List<Patient> showPatient() {
        return patientRepository.getAllPatients();
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email);
    }

    public Optional<Patient> addPatient(Patient patient) {
        return patientRepository.addPatient(patient);
    }

    public Optional<Patient> deletePatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.getPatientByEmail(email);
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        return patientRepository.deletePatient(patient.get());
    }

    public Optional<Patient> editPatient(String email, Patient editInfo) {
        return patientRepository.editPatient(email, editInfo);
    }

    public Optional<String> editPassword(String email, String password) {
        return patientRepository.editPassword(email, password);
    }
}
