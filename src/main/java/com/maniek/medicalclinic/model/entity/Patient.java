package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Patient")
public class Patient extends UserData {

    @Column(unique = true, nullable = false)
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    public Patient(Long id, String email, String password, Role role, String idCardNo, String firstName,
                   String lastName, String phoneNumber, LocalDate birthday) {
        super(id, email, password, role);
        this.idCardNo = idCardNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public void update(Patient editInfo) {
        this.setEmail(editInfo.getEmail());
        this.setFirstName(editInfo.getFirstName());
        this.setLastName(editInfo.getLastName());
        this.setBirthday(editInfo.getBirthday());
        this.setPhoneNumber(editInfo.getPhoneNumber());
        this.setPassword(editInfo.getPassword());
    }
}
