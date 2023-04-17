package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.dto.FacilityDTO;
import com.maniek.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilitiesService;

    @PostMapping("/{facilityId}/assign")
    public ResponseEntity<String> assignDoctorToFacility(@RequestParam Long doctorId, @PathVariable Long facilityId) {
        return ResponseEntity.ok(facilitiesService.assignDoctor(doctorId, facilityId));
    }

    @PostMapping
    public ResponseEntity<FacilityDTO> addFacility(@RequestBody FacilityDTO facilityDTO) {
        return ResponseEntity.ok(facilitiesService.addFacility(facilityDTO));
    }

}
