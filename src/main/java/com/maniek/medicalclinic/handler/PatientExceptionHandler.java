package com.maniek.medicalclinic.handler;

import com.maniek.medicalclinic.exception.patient.PatientIllegalArgumentException;
import com.maniek.medicalclinic.exception.patient.PatientNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PatientExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> patientNotFoundErrorResponse(PatientNotFoundException patientNotFoundException) {
        return ResponseEntity.status(404).body(patientNotFoundException.getMessage());
    }

    @ExceptionHandler(PatientIllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentErrorResponse(PatientIllegalArgumentException patientIllegalArgumentException) {
        return ResponseEntity.status(400).body(patientIllegalArgumentException.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> throwableErrorResponse(Throwable throwable) {
        return ResponseEntity.status(500).body("Unknown error");
    }

}
