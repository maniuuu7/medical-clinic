package com.maniek.medicalclinic.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
public class Admin extends UserData {
}
