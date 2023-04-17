package com.maniek.medicalclinic.exception.doctor;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(){ super("Doctor not found");}
}
