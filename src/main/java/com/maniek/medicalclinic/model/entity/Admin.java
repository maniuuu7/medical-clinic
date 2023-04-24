package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
public class Admin extends UserData {

    {
        super.setRole(Role.ADMIN);
    }
}
