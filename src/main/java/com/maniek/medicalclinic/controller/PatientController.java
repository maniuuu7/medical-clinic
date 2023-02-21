package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.Patient;
import com.maniek.medicalclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("patients")
public class PatientController {

    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> showPatients() {
        return ResponseEntity.ok(patientService.showPatient());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable("email") String email) {
        Optional<Patient> patient = patientService.getPatientByEmail(email);
        return patient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Optional<Patient> patient1 = patientService.addPatient(patient);
        if (patient1.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Patient> deletePatientByEmail(@PathVariable("email") String email) {
        Optional<Patient> patient = patientService.deletePatientByEmail(email);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<Patient> editPatient(@PathVariable("email") String email, @RequestBody Patient patient) {
        Optional<Patient> entity = patientService.editPatient(email, patient);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> editPassword(@PathVariable("email") String email, @RequestBody String password) {
        Optional<String> patient = patientService.editPassword(email, password);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Password was changed successfully");
    }
}
