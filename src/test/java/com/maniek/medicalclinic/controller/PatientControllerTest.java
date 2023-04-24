package com.maniek.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maniek.medicalclinic.model.Role;
import com.maniek.medicalclinic.model.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@WithMockUser(roles = {"PATIENT", "ADMIN", "DOCTOR"})
@AutoConfigureMockMvc
@SpringBootTest
public class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PatientController patientController;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        try {
            patientController.deletePatientByEmail("mac@gmail.com");
        } catch (Exception ignored) {
        }
    }

    @Test
    void showPatient_PatientExists_PatientsReturned() throws Exception {
        patientController.addPatient(new Patient(null,"mac@gmail.com", "dsadsa", Role.PATIENT, "2345", "mac", "ghgj", "987456354",
                LocalDate.of(1995, 05, 12), new HashSet<>()));
        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("mac@gmail.com"));
    }

    @Test
    void getPatientByEmail_PatientsExists_PatientReturned() throws Exception {
        patientController.addPatient(new Patient(null,"mac@gmail.com", "dsadsa",Role.PATIENT, "2345", "mac", "ghgj", "987456354",
                LocalDate.of(1995, 05, 12), new HashSet<>()));
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/mac@gmail.com"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mac@gmail.com"));
    }

    @Test
    void addPatient_PatientExists_PatientAdded() throws Exception {
        Patient patient = new Patient(null,"mac@gmail.com", "dsadsa",Role.PATIENT, "2345", "mac", "ghgj", "987456354", LocalDate.of(1995, 05, 12), new HashSet<>());
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .content(objectMapper.writeValueAsString(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
    }

    @Test
    void deletePatientByEmail_PatientExists_DeletePatient() throws Exception {
        patientController.addPatient(new Patient(null,"mac@gmail.com", "dsadsa",Role.PATIENT, "2345", "mac", "ghgj", "987456354", LocalDate.of(1995, 05, 12), new HashSet<>()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/mac@gmail.com"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mac@gmail.com"));
    }

    @Test
    void editPatient_PatientExists_PatientEdited() throws Exception {
        patientController.addPatient(new Patient(null,"mac@gmail.com", "dsadsa",Role.PATIENT, "2345", "mac", "ghgj", "987456354", LocalDate.of(1995, 5, 12), new HashSet<>()));
        Patient patient1 = new Patient(null,"mac@gmail.com", "gdfgd",Role.PATIENT, "2345", "dgfgd", "gfdgdf", "987564738", LocalDate.of(1998, 12, 7), new HashSet<>());
        mockMvc.perform(MockMvcRequestBuilders.put("/patients/mac@gmail.com")
                        .content(objectMapper.writeValueAsString(patient1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mac@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("gfdgdf"));
    }

    @Test
    void editPatient_PatientWithGivenEmailDoesNotExist_404NotFound() throws Exception {
        Patient editInfo = new Patient(null,"mac@gmail.com", "gdfgd", Role.PATIENT, "6475", "dgfgd", "gfdgdf", "987564738", LocalDate.of(1998, 12, 7), new HashSet<>());
        mockMvc.perform(MockMvcRequestBuilders.put("/patients/dsa@gmail.com")
                        .content(objectMapper.writeValueAsString(editInfo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Patient not found"));
    }

    @Test
    void editPatient_PatientIncorectDataError_400IllegalArgument() throws Exception {
        patientController.addPatient(new Patient(null,"mac@gmail.com", "dsadsa",Role.PATIENT, "2345", "mac", "ghgj", "987456354", LocalDate.of(1995, 5, 12), new HashSet<>()));
        Patient editInfo = new Patient(null,"mac@gmail.com", null,Role.PATIENT, "6475", "dgfgd", "gfdgdf", "987564738", LocalDate.of(1998, 12, 7), new HashSet<>());
        mockMvc.perform(MockMvcRequestBuilders.put("/patients/mac@gmail.com")
                        .content(objectMapper.writeValueAsString(editInfo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Incorrect patient data"));
    }

    @Test
    void editpassword_PatientExists_PatientPasswordEdited() throws Exception {
        patientController.addPatient(new Patient(null,"mac@gmail.com", "dsadsa",Role.PATIENT, "2345", "mac", "ghgj", "987456354", LocalDate.of(1995, 05, 12), new HashSet<>()));
        String password = "fgdfd";
        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/mac@gmail.com")
                        .content(objectMapper.writeValueAsString(password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("fgdfd"));
    }
}
