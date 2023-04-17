package com.maniek.medicalclinic.repository;

import com.maniek.medicalclinic.model.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByEmail(String email);
}
