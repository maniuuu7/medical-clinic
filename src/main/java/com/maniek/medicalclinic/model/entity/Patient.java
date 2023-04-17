package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
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
    @OneToMany
    private Set<Visit> visits;

    public Patient(Long id, String email, String password, Role role, String idCardNo, String firstName,
                   String lastName, String phoneNumber, LocalDate birthday, Set<Visit> visits) {
        super(id, email, password, role);
        this.idCardNo = idCardNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.visits = visits;
    }

    public void update(Patient editInfo) {
        this.setEmail(editInfo.getEmail());
        this.setFirstName(editInfo.getFirstName());
        this.setLastName(editInfo.getLastName());
        this.setBirthday(editInfo.getBirthday());
        this.setPhoneNumber(editInfo.getPhoneNumber());
        this.setPassword(editInfo.getPassword());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Patient patient = (Patient) o;
        return this.getId() != null && super.equals(patient);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
