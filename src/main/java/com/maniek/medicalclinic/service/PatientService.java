package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.PatientIllegalArgumentException;
import com.maniek.medicalclinic.exception.PatientNotFoundException;
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

    public Patient getPatientByEmail(String email) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
        return patient;
    }

    public Optional<Patient> addPatient(Patient patient) {
        Optional<Patient> patient1 = patientRepository.getPatientByEmail(patient.getEmail());
        if (patient1.isPresent()){
            throw new PatientIllegalArgumentException("Error during patient creation. Patient with given email exists");
        }
        return patientRepository.addPatient(patient);
    }

    public Patient deletePatientByEmail(String email) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
        return patientRepository.deletePatient(patient);
    }

    public Patient editPatient(String email, Patient editInfo) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
       Optional <Patient> patient1 = patientRepository.getPatientByEmail(editInfo.getEmail());
        if (patient1.isPresent() && !email.equals(editInfo.getEmail())){
            throw new PatientIllegalArgumentException("Error during patient creation. Patient with given email exists");
        }
        Patient editedPatient = patientRepository.editPatient(email, editInfo).get();
        return editedPatient;
    }

    public String editPassword(String email, String password) {
        Patient patient = patientRepository.getPatientByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException());
        String result = patientRepository.editPassword(email, password).get();
        return result;
    }
}
