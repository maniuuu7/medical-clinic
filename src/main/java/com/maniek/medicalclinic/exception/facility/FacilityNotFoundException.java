package com.maniek.medicalclinic.exception.facility;

public class FacilityNotFoundException extends RuntimeException {

    public FacilityNotFoundException(){
        super("Facility not found");
    }
}
