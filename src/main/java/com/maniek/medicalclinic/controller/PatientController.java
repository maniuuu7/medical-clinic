package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("patients")
public class PatientController {
    private List<Patient> patients = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Patient>> showPatients() {
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable("email") String email) {
        Optional<Patient> patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        return patient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Optional<Patient> patient1 = patients.stream()
                .filter(patient2 -> patient2.getEmail().equals(patient.getEmail()))
                .findFirst();
        if (patient1.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        patients.add(patient);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Patient> deletePatientByEmail(@PathVariable("email") String email) {
        Optional<Patient> patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        patients.remove(patient.get());
        return ResponseEntity.ok(patient.get());
    }

    @PutMapping("/{email}")
    public ResponseEntity<Patient> editPatient(@PathVariable("email") String email, @RequestBody Patient patient) {
        Optional<Patient> entity = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        entity.get().setBirthday(patient.getBirthday());
        entity.get().setEmail(patient.getEmail());
        entity.get().setPassword(patient.getPassword());
        entity.get().setFirstName(patient.getFirstName());
        entity.get().setLastName(patient.getLastName());
        entity.get().setIdCardNo(patient.getIdCardNo());
        entity.get().setPhoneNumber(patient.getPhoneNumber());
        return ResponseEntity.ok(entity.get());
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> editPassword(@PathVariable("email") String email, @RequestBody String password) {
        Optional<Patient> patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equals(email))
                .findFirst();
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        patient.get().setPassword(password);
        return ResponseEntity.ok("Password was changed successfully");
    }
}
