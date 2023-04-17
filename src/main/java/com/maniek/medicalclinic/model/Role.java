package com.maniek.medicalclinic.model;

public enum Role {
    DOCTOR("ROLE_Doctor"), PATIENT("ROLE_Patient"), ADMIN("ROLE_Admin");
    String name;

    Role(String name) {
        this.name = name;
    }
}
