package com.maniek.medicalclinic.exception.patient;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException() {
        super("Patient not found");
    }
}
