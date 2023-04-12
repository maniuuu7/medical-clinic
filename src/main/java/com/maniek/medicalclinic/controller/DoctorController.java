package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.dto.DoctorDTO;
import com.maniek.medicalclinic.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private DoctorService doctorService;

    @PostMapping("/assign")
    public ResponseEntity<String> assignFacilityToDoctor(@RequestParam Long doctorId, @RequestParam Long facilityId) {
        return ResponseEntity.ok(doctorService.assignFacility(doctorId, facilityId));
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(doctorService.addDoctor(doctorDTO));
    }
}
