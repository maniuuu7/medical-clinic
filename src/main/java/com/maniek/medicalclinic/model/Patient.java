package com.maniek.medicalclinic.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Patient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String latName;
    private double phoneNumber;
    private String birthday;

}
