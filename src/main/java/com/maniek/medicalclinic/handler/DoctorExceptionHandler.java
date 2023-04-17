package com.maniek.medicalclinic.handler;

import com.maniek.medicalclinic.exception.doctor.DoctorIllegalArgumentException;
import com.maniek.medicalclinic.exception.doctor.DoctorNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DoctorExceptionHandler {

    @ExceptionHandler(DoctorIllegalArgumentException.class)
    public ResponseEntity<String> illegalDoctorArgumentErrorResponse(DoctorIllegalArgumentException doctorIllegalArgumentException) {
        return ResponseEntity.status(400).body(doctorIllegalArgumentException.getMessage());
    }
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<String> doctorNotFoundErrorResponse(DoctorNotFoundException doctorNotFoundException) {
        return ResponseEntity.status(404).body(doctorNotFoundException.getMessage());
    }
}
