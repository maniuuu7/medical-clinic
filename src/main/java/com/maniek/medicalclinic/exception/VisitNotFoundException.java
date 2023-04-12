package com.maniek.medicalclinic.exception;

public class VisitNotFoundException extends RuntimeException {

    public VisitNotFoundException(String visitNotFound) {
        super("Visit not found");
    }
}
