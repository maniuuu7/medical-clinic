package com.maniek.medicalclinic.service;

import com.maniek.medicalclinic.exception.PatientIllegalArgumentException;
import com.maniek.medicalclinic.exception.PatientNotFoundException;
import com.maniek.medicalclinic.mapper.PatientMapper;
import com.maniek.medicalclinic.mapper.PatientMapperImpl;
import com.maniek.medicalclinic.model.dto.PatientDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import com.maniek.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PatientServiceTest {

    PatientRepository patientRepository;

    PatientService patientService;

    PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        this.patientRepository = mock(PatientRepository.class);
        this.patientMapper = new PatientMapperImpl();
        this.patientService = new PatientService(patientRepository, patientMapper);
    }


    @Test
    void showPatient_PatientsExist_PatientsReturned() {
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient patient1 = new Patient("fdsfsd", "dsad", " fhhgf", "fghfg", "fghfgh", "986345213", LocalDate.of(1995, 04, 12));
        patients.add(patient);
        patients.add(patient1);
        when(patientRepository.getAllPatients()).thenReturn(patients);

        List<PatientDTO> result = patientService.showPatient();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("fdsfsd", result.get(1).getEmail());
    }

    @Test
    void getPatientByEmail_PatientExists_PatientsReturned() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.of(patient));

        PatientDTO patient1 = patientService.getPatientByEmail("dsadsa");

        Assertions.assertEquals("dsadsa", patient1.getEmail());
        Assertions.assertEquals("Masds", patient1.getFirstName());
    }

    @Test
    void getPatientByEmail_PatientNotFound_ThrownException() {
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.empty());

        PatientNotFoundException thrown =
                Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.getPatientByEmail(eq("dsadsa")));

        Assertions.assertEquals("Patient not found", thrown.getMessage());
    }

    @Test
    void addPatient_CorrectData_PatientAdded() {
        Patient patient2 = new Patient("ouyut", "43543", "67756", "kjhk", "qewqe", "67567", LocalDate.of(1997, 06, 10));
        when(patientRepository.addPatient(patient2)).thenReturn(Optional.of(patient2));
        when(patientRepository.getPatientByEmail(eq("ouyut"))).thenReturn(Optional.empty());

        Optional<PatientDTO> patient = patientService.addPatient(patient2);

        Assertions.assertEquals("ouyut", patient.get().getEmail());

    }

    @Test
    void addPatient_ExistsPatient_IllegalArgumentException() {
        Patient patient2 = new Patient("ouyut", "43543", "67756", "kjhk", "qewqe", "67567", LocalDate.of(1997, 06, 10));
        when(patientRepository.getPatientByEmail(eq("ouyut"))).thenReturn(Optional.of(patient2));

        PatientIllegalArgumentException thrown =
                Assertions.assertThrows(PatientIllegalArgumentException.class, () -> patientService.addPatient(patient2));

        Assertions.assertEquals("Error during patient creation. Patient with given email exists", thrown.getMessage());
    }

    @Test
    void addPatient_PatientValidateInfo_ExceptionThrown() {
        Patient patient2 = new Patient("ouyut", null, "67756", "kjhk", "qewqe", "67567", LocalDate.of(1997, 06, 10));
        when(patientRepository.getPatientByEmail(eq("ouyut"))).thenReturn(Optional.empty());

        PatientIllegalArgumentException thrown =
                Assertions.assertThrows(PatientIllegalArgumentException.class, () -> patientService.addPatient(patient2));

        Assertions.assertEquals("Incorrect patient data", thrown.getMessage());
    }

    @Test
    void deletePatientByEmail_PatientExists_PatientDeleted() {
        Patient patient1 = new Patient("fdsfsd", "dsad", " fhhgf", "fghfg", "fghfgh", "986345213", LocalDate.of(1995, 04, 12));
        when(patientRepository.getPatientByEmail(patient1.getEmail())).thenReturn(Optional.ofNullable(patient1));
        when(patientRepository.deletePatient(patient1)).thenReturn(patient1);

        PatientDTO patient = patientService.deletePatientByEmail(patient1.getEmail());

        Assertions.assertEquals("fdsfsd", patient.getEmail());
    }

    @Test
    void deletePatientByEmail_PatientNotFound_ThrownException() {
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.empty());

        PatientNotFoundException thrown =
                Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.deletePatientByEmail("dsadsa"));

        Assertions.assertEquals("Patient not found", thrown.getMessage());
    }

    @Test
    void editPatient_PatientExists_PatientEdited() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient editInfo = new Patient("htyht", "htyht", "dasds", "gdfgdf", "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));
        when(patientRepository.getPatientByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        when(patientRepository.getPatientByEmail(eq(editInfo.getEmail()))).thenReturn(Optional.empty());
        when(patientRepository.editPatient(eq(patient.getEmail()), eq(editInfo))).thenReturn(Optional.of(editInfo));

        PatientDTO patient1 = patientService.editPatient(patient.getEmail(), editInfo);

        Assertions.assertEquals("gdfgdf", patient1.getFirstName());
        Assertions.assertEquals("hgfhgfh", patient1.getLastName());
    }

    @Test
    void editPatient_PatientExistsWithEmail_PatientEdited() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient editInfo = new Patient("dsadsa", "htyht", "dasds", "gdfgdf", "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));
        when(patientRepository.getPatientByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));
        when(patientRepository.editPatient(eq(patient.getEmail()), eq(editInfo))).thenReturn(Optional.of(editInfo));


        PatientDTO patient1 = patientService.editPatient(patient.getEmail(), editInfo);

        Assertions.assertEquals("dsadsa", patient1.getEmail());
        Assertions.assertEquals("gdfgdf", patient1.getFirstName());
    }

    @Test
    void editPatient_PatientNotFound_ExceptionThrown() {
        Patient editInfo = new Patient("htyht", "htyht", "rewrew", "gdfgdf", "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.empty());

        PatientNotFoundException thrown =
                Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.editPatient("dsadsa", editInfo));

        Assertions.assertEquals("Patient not found", thrown.getMessage());
    }

    @Test
    void editPatient_IncorrectIdCardNo_ExceptionThrown() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient editInfo = new Patient("dsadsa", "htyht", "gfdgd", "gdfgdf", "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.of(patient));

        PatientIllegalArgumentException thrown =
                Assertions.assertThrows(PatientIllegalArgumentException.class, () -> patientService.editPatient("dsadsa", editInfo));

        Assertions.assertEquals("Incorrect patient data", thrown.getMessage());
    }

    @Test
    void editPatient_PatientEmailIsExists_ExceptionThrown() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient editInfo = new Patient("dfgfd", "htyht", "dasds", "gdfgdf", "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));
        when(patientRepository.getPatientByEmail(eq("dfgfd"))).thenReturn(Optional.of(editInfo));
        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.of(patient));

        PatientIllegalArgumentException thrown =
                Assertions.assertThrows(PatientIllegalArgumentException.class, () -> patientService.editPatient("dsadsa", editInfo));

        Assertions.assertEquals("Incorrect patient data", thrown.getMessage());
    }

    @Test
    void editPatient_PatientValidateInfo_ExceptionThrown() {
        Patient patient = new Patient("dsadsa", "sdsa", "dasds", "fsdfds", "dsadsad", "987465376", LocalDate.of(1995, 12, 05));
        Patient editInfo = new Patient("dfgfd", "htyht", "gfdgd", null, "hgfhgfh", "567456354", LocalDate.of(1987, 05, 12));

        when(patientRepository.getPatientByEmail(eq("dsadsa"))).thenReturn(Optional.of(patient));

        PatientIllegalArgumentException thrown =
                Assertions.assertThrows(PatientIllegalArgumentException.class, () -> patientService.editPatient("dsadsa", editInfo));

        Assertions.assertEquals("Incorrect patient data", thrown.getMessage());
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
    void editPassword_PatientNotFound_ExceptionThrown() {
        String password = "dsads";
        String email = "jghjgh";
        when(patientRepository.getPatientByEmail("dsadsa")).thenReturn(Optional.empty());

        PatientNotFoundException thrown =
                Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.editPassword(email, password));

        Assertions.assertEquals("Patient not found", thrown.getMessage());
    }
}
