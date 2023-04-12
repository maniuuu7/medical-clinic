package com.maniek.medicalclinic.repository;

import com.maniek.medicalclinic.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
