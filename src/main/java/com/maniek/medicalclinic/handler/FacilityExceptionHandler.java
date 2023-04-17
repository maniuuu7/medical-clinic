package com.maniek.medicalclinic.handler;

import com.maniek.medicalclinic.exception.facility.FacilityIllegalArgumentException;
import com.maniek.medicalclinic.exception.facility.FacilityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FacilityExceptionHandler {

    @ExceptionHandler(FacilityIllegalArgumentException.class)
    public ResponseEntity<String> illegalFacilityArgumentErrorResponse(FacilityIllegalArgumentException facilityIllegalArgumentException) {
        return ResponseEntity.status(400).body(facilityIllegalArgumentException.getMessage());
    }
    @ExceptionHandler(FacilityNotFoundException.class)
    public ResponseEntity<String> facilityNotFoundErrorResponse(FacilityNotFoundException facilityNotFoundException) {
        return ResponseEntity.status(404).body(facilityNotFoundException.getMessage());
    }
}
