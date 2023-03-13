package com.maniek.medicalclinic.repository;

import com.maniek.medicalclinic.model.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    Optional<Patient> getPatientByEmail(String email);
    List<Patient> getAllPatients();
    Optional<Patient> addPatient(Patient patient);
    Patient deletePatient(Patient patient);
    Optional<Patient> editPatient( String email, Patient editInfo);
    Optional<String> editPassword(String email, String password);


}
