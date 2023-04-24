package com.maniek.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maniek.medicalclinic.model.Role;
import com.maniek.medicalclinic.model.dto.VisitDTO;
import com.maniek.medicalclinic.model.entity.Patient;
import com.maniek.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@WithMockUser(roles = {"Patient", "Admin", "Doctor"})
@AutoConfigureMockMvc
@SpringBootTest
public class VisitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VisitController visitController;

    @Autowired
    PatientController patientController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PatientRepository patientRepository;

    @Test
    void showVisit_VisitExists_VisitsReturned() throws Exception {
        visitController.addVisit(new VisitDTO(LocalDateTime.of(2023, 10, 10, 10, 15)));
        mockMvc.perform(MockMvcRequestBuilders.get("/visits"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].term").value("2023-10-10T10:15:00"));
    }

    @Test
    void assignPatientToVisit_AssignPatient_VisitWithPatientReturned() throws Exception {
        visitController.addVisit(new VisitDTO(LocalDateTime.of(2024, 12, 10, 10, 15)));
        patientController.addPatient(new Patient(null, "dsadsa", "sdsa", Role.PATIENT, "dasds",
                "Masds", "dsadsad", "987465376", LocalDate.of(1995, 12, 5), new HashSet<>()));
        Patient patient = patientRepository.findAll().stream().reduce((e1, e2)-> e2).get();
        mockMvc.perform(MockMvcRequestBuilders.post("/visits/1?patientId="+patient.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Patient assigned successfully"));
    }
}
