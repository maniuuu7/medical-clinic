package com.maniek.medicalclinic.controller;

import com.maniek.medicalclinic.model.dto.VisitDTO;
import com.maniek.medicalclinic.model.entity.Visit;
import com.maniek.medicalclinic.service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitController {
    private VisitService visitService;

    @GetMapping
    public ResponseEntity<List<Visit>> showVisit() {
        return ResponseEntity.ok(visitService.showVisit());
    }

    @PostMapping
    public ResponseEntity<VisitDTO> addVisit(@RequestBody VisitDTO visitDTO) {
        VisitDTO visit = visitService.addVisit(visitDTO);
        return ResponseEntity.ok(visit);
    }

    @PostMapping("/{visitId}")
    public ResponseEntity<String> assignPatientToVisit(@PathVariable Long visitId, @RequestParam Long patientId) {
        return ResponseEntity.ok(visitService.assignPatientToVisit(visitId, patientId));

    }
}
