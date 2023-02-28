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
       return ResponseEntity.ok(patientService.getPatientByEmail(email));
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
       return ResponseEntity.ok(patientService.deletePatientByEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<Patient> editPatient(@PathVariable("email") String email, @RequestBody Patient patient) {
       return ResponseEntity.ok(patientService.editPatient(email, patient));
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> editPassword(@PathVariable("email") String email, @RequestBody String password) {
        return ResponseEntity.ok(patientService.editPassword(email, password));
    }
}
