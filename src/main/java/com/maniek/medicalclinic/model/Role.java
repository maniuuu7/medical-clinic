package com.maniek.medicalclinic.model;

import lombok.Getter;

@Getter
public enum Role {
    DOCTOR("ROLE_DOCTOR"), PATIENT("ROLE_PATIENT"), ADMIN("ROLE_ADMIN");
    String name;

    Role(String name) {
        this.name = name;
    }
}
