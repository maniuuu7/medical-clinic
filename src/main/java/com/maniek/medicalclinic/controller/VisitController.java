package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.dto.VisitDTO;
import com.maniek.medicalclinic.model.entity.Visit;
import com.maniek.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitController {
    private final VisitService visitService;

    @GetMapping
    public ResponseEntity<List<Visit>> showVisit() {
        return ResponseEntity.ok(visitService.showVisit());
    }

    @PostMapping
    public ResponseEntity<VisitDTO> addVisit(@RequestBody VisitDTO visitDTO) {
        return ResponseEntity.ok(visitService.addVisit(visitDTO));
    }

    @PostMapping("/{visitId}")
    public ResponseEntity<String> assignPatientToVisit(@PathVariable Long visitId, @RequestParam Long patientId) {
        return ResponseEntity.ok(visitService.assignPatientToVisit(visitId, patientId));

    }
}
