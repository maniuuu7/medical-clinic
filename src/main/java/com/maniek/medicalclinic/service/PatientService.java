package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private List<Patient> patients = new ArrayList<>();

    public List<Patient> showPatient() {
        return patients;
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Patient> addPatient(Patient patient) {
        Optional<Patient> patient1 = patients.stream()
                .filter(patient2 -> patient2.getEmail().equals(patient.getEmail()))
                .findFirst();
        if (patient1.isPresent()) {
            return Optional.empty();
        }
        patients.add(patient);
        return Optional.of(patient);
    }

    public Optional<Patient> deletePatientByEmail(String email) {
        Optional<Patient> patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        patients.remove(patient);
        return patient;
    }

    public Optional<Patient> editPatient(String email, Patient patient) {
        Optional<Patient> entity = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        entity.get().setBirthday(patient.getBirthday());
        entity.get().setEmail(patient.getEmail());
        entity.get().setPassword(patient.getPassword());
        entity.get().setFirstName(patient.getFirstName());
        entity.get().setLastName(patient.getLastName());
        entity.get().setIdCardNo(patient.getIdCardNo());
        entity.get().setPhoneNumber(patient.getPhoneNumber());
        return Optional.of(patient);
    }

    public Optional<String> editPassword(String email, String password) {
        Optional<Patient> patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        patient.get().setPassword(password);
        return Optional.ofNullable(password);
    }
}
