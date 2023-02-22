package com.maniek.medicalclinic.repository;

import com.maniek.medicalclinic.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private List<Patient> patients = new ArrayList<>();



    @Override
    public Optional<Patient> getPatientByEmail(String email) {
        return patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<Patient> getAllPatients() {
        return patients;
    }

    @Override
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

    @Override
    public Optional<Patient> deletePatientByEmail(String email) {
        Optional<Patient> patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        patients.remove(patient.get());
        return patient;
    }

    @Override
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
        return entity;
    }

    @Override
    public Optional<String> editPassword(String email, String password) {
        Optional<Patient> patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        patient.get().setPassword(password);
        return Optional.of(password);
    }
}
