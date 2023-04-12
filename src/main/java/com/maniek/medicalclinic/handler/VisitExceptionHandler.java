package com.maniek.medicalclinic.handler;

import com.maniek.medicalclinic.exception.VisitIllegalArgumentException;
import com.maniek.medicalclinic.exception.VisitNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VisitExceptionHandler {

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<String> visitNotFoundErrorResponse(VisitNotFoundException visitNotFoundException) {
        return ResponseEntity.status(404).body(visitNotFoundException.getMessage());
    }

    @ExceptionHandler(VisitIllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentErrorResponse(VisitIllegalArgumentException visitIllegalArgumentException) {
        return ResponseEntity.status(400).body(visitIllegalArgumentException.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> throwableErrorResponse(Throwable throwable) {
        return ResponseEntity.status(500).body("Unkown error");
    }
}