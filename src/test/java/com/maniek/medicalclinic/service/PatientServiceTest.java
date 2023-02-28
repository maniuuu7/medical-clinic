package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.PatientIllegalArgumentException;
import com.maniek.medicalclinic.exception.PatientNotFoundException;
import com.maniek.medicalclinic.model.Patient;
import com.maniek.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PatientServiceTest {
    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    PatientService patientService;

    @Test
    void showPatient_PatientsExist_PatientsReturned() {
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient patient1 = new Patient("fdsfsd", "dsad", " fhhgf", "fghfg", "fghfgh", "986345213", LocalDate.of(1995, 04, 12));
        patients.add(patient);
        patients.add(patient1);
        when(patientRepository.getAllPatients()).thenReturn(patients);

        List<Patient> result = patientService.showPatient();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("fdsfsd", result.get(1).getEmail());
    }

    @Test
    void getPatientByEmail_PatientExists_PatientsReturned() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.of(patient));

        Patient patient1 = patientService.getPatientByEmail("dsadsa");

        Assertions.assertEquals("dsadsa", patient1.getEmail());
        Assertions.assertEquals("Masds", patient1.getFirstName());
    }

    @Test
    void getPatientByEmail_PatientExists_ThrownException() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.empty());

        PatientNotFoundException thrown =
                catchThrowableOfType(() -> patientService.getPatientByEmail(eq("dsadsa")), PatientNotFoundException.class);

        assertThat(thrown)
                .hasMessage("Patient not found");

        Assertions.assertEquals("dsadsa", patient.getEmail());
    }

    @Test
    void addPatient_CorrectData_PatientAdded() {
        Patient patient2 = new Patient("ouyut", "43543", "67756", "kjhk", "qewqe", "67567", LocalDate.of(1997, 06, 10));
        when(patientRepository.addPatient(patient2)).thenReturn(Optional.of(patient2));
        when(patientRepository.getPatientByEmail(eq("ouyut"))).thenReturn(Optional.empty());

        Optional<Patient> patient = patientService.addPatient(patient2);

        Assertions.assertEquals("ouyut", patient.get().getEmail());
        Assertions.assertEquals("67756", patient.get().getIdCardNo());
    }

    @Test
    void addPatient_ExistsPatient_IllegalArgumentException() {
        Patient patient2 = new Patient("ouyut", "43543", "67756", "kjhk", "qewqe", "67567", LocalDate.of(1997, 06, 10));
        when(patientRepository.getPatientByEmail(eq("ouyut"))).thenReturn(Optional.of(patient2));

        PatientIllegalArgumentException thrown =
                catchThrowableOfType(() -> patientService.addPatient(patient2), PatientIllegalArgumentException.class);

        assertThat(thrown).hasMessage("Error during patient creation. Patient with given email exists");

        Assertions.assertEquals("ouyut", patient2.getEmail());
    }

    @Test
    void deletePatientByEmail_PatientExists_PatientDeleted() {
        Patient patient1 = new Patient("fdsfsd", "dsad", " fhhgf", "fghfg", "fghfgh", "986345213", LocalDate.of(1995, 04, 12));
        when(patientRepository.getPatientByEmail(patient1.getEmail())).thenReturn(Optional.ofNullable(patient1));
        when(patientRepository.deletePatient(patient1)).thenReturn(patient1);

        Patient patient = patientService.deletePatientByEmail(patient1.getEmail());

        Assertions.assertEquals("fdsfsd", patient.getEmail());
    }

    @Test
    void deletePatientByEmail_PatientNotFound_ThrownException() {
        Patient patient1 = new Patient("fdsfsd", "dsad", " fhhgf", "fghfg", "fghfgh", "986345213", LocalDate.of(1995, 04, 12));

        when(patientRepository.getPatientByEmail(eq(patient1.getEmail()))).thenReturn(Optional.empty());

        PatientNotFoundException thrown =
                catchThrowableOfType(() -> patientService.deletePatientByEmail(patient1.getEmail()), PatientNotFoundException.class);

        assertThat(thrown)
                .hasMessage("Patient not found");

        Assertions.assertEquals("fdsfsd", patient1.getEmail());
    }

    @Test
    void editPatient_PatientExists_PatientEdited() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient editInfo = new Patient("htyht", "htyht", "rewrew", "gdfgdf", "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));
        when(patientRepository.getPatientByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        when(patientRepository.getPatientByEmail(eq(editInfo.getEmail()))).thenReturn(Optional.empty());
        when(patientRepository.editPatient(eq(patient.getEmail()), eq(editInfo))).thenReturn(Optional.of(editInfo));

        Patient patient1 = patientService.editPatient(patient.getEmail(), editInfo);

        Assertions.assertEquals("dsadsa", patient.getEmail());
        Assertions.assertEquals("htyht", editInfo.getEmail());
    }

    @Test
    void editPatient_PatientNotFound_ExceptionThrown() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient editInfo = new Patient("htyht", "htyht", "rewrew", "gdfgdf", "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));

        when(patientRepository.getPatientByEmail(eq(patient.getEmail()))).thenReturn(Optional.empty());

        PatientNotFoundException thrown =
                catchThrowableOfType(() -> patientService.editPatient(patient.getEmail(), editInfo), PatientNotFoundException.class);

        assertThat(thrown)
                .hasMessage("Patient not found");

        Assertions.assertEquals("dsadsa", patient.getEmail());
    }

    @Test
    void editPassword_PatientExists_PatientPasswordEdited() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        String password = "dsads";
        String email = "jghjgh";
        when(patientRepository.getPatientByEmail(eq(email))).thenReturn(Optional.of(patient));
        when(patientRepository.editPassword(eq(email), eq(password))).thenReturn(Optional.of(password));

        String result = patientService.editPassword(email, password);

        Assertions.assertEquals("dsads", result);
    }
@Test
    void editPassword_PatientNotFound_ExceptionThrown(){
    Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
    String password = "dsads";
    String email = "jghjgh";

    when(patientRepository.getPatientByEmail(patient.getEmail())).thenReturn(Optional.empty());

    PatientNotFoundException thrown =
            catchThrowableOfType(() ->patientService.editPassword(email, password), PatientNotFoundException.class);

    assertThat(thrown)
            .hasMessage("Patient not found");

    Assertions.assertEquals("dsadsa", patient.getEmail());
}
}
