package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.dto.DoctorDTO;
import com.maniek.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/{doctorId}/assign")
    public ResponseEntity<String> assignFacilityToDoctor(@PathVariable Long doctorId, @RequestParam Long facilityId) {
        return ResponseEntity.ok(doctorService.assignFacility(doctorId, facilityId));
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(doctorService.addDoctor(doctorDTO));
    }
}
