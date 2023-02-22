package com.maniek.medicalclinic.repository;

import com.maniek.medicalclinic.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    Optional<Patient> getPatientByEmail(String email);
    List<Patient> getAllPatients();
    Optional<Patient> addPatient(Patient patient);
    Optional<Patient> deletePatientByEmail(String email);
    Optional<Patient> editPatient(String email, Patient patient);
    Optional<String> editPassword(String email, String password);


}
