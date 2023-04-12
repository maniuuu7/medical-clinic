package com.maniek.medicalclinic.repository;

import com.maniek.medicalclinic.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByTerm(LocalDateTime term);
}
