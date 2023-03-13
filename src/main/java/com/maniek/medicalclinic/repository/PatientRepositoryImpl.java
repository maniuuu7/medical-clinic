package com.maniek.medicalclinic.repository;

import com.maniek.medicalclinic.model.entity.Patient;
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
        patients.add(patient);
        return Optional.of(patient);
    }

    @Override
    public Patient deletePatient(Patient patient) {
        patients.remove(patient);
        return patient;
    }

    @Override
    public Optional<Patient> editPatient(String email, Patient editInfo) {
        Optional<Patient> patient = getPatientByEmail(email);
        patient.ifPresent(entity -> {
            entity.setBirthday(editInfo.getBirthday());
            entity.setEmail(editInfo.getEmail());
            entity.setPassword(editInfo.getPassword());
            entity.setFirstName(editInfo.getFirstName());
            entity.setLastName(editInfo.getLastName());
            entity.setIdCardNo(editInfo.getIdCardNo());
            entity.setPhoneNumber(editInfo.getPhoneNumber());
        });
        return patient;
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
