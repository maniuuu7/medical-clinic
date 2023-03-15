package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.dto.PatientDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import com.maniek.medicalclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("patients")
public class PatientController {

    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> showPatients() {
        return ResponseEntity.ok(patientService.showPatient());
    }

    @GetMapping("/{email}")
    public ResponseEntity<PatientDTO> getPatientByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }

    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@RequestBody Patient patient) {
        PatientDTO patient1 = patientService.addPatient(patient);
        return ResponseEntity.ok(patient1);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<PatientDTO> deletePatientByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(patientService.deletePatientByEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<PatientDTO> editPatient(@PathVariable("email") String email, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.editPatient(email, patient));
    }

    @PatchMapping("/{email}")
    public ResponseEntity<String> editPassword(@PathVariable("email") String email, @RequestBody String password) {
        return ResponseEntity.ok(patientService.editPassword(email, password));
    }
}
