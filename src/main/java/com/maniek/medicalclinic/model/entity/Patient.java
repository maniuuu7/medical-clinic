package com.maniek.medicalclinic.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(unique = true, nullable = false)
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    public void update(Patient editInfo) {
        this.setEmail(editInfo.getEmail());
        this.setFirstName(editInfo.getFirstName());
        this.setLastName(editInfo.getLastName());
        this.setBirthday(editInfo.getBirthday());
        this.setPhoneNumber(editInfo.getPhoneNumber());
        this.setPassword(editInfo.getPassword());
    }
}
