package com.maniek.medicalclinic.model.entity;

import com.maniek.medicalclinic.model.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserData userData = (UserData) o;
        return this.getId() != null && super.equals(userData);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
