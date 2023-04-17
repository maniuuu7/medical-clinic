package com.maniek.medicalclinic.exception.visit;

public class VisitNotFoundException extends RuntimeException {

    public VisitNotFoundException() {
        super("Visit not found");
    }
}
