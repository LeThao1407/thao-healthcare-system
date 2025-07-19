package com.example.healthcaresystem.controller;

import com.example.healthcaresystem.model.Appointment;
import com.example.healthcaresystem.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    public void testGetAllAppointments() throws Exception {
        Appointment a1 = new Appointment(1L, 1L, 2L, "2024-01-01", "10:00 AM");
        Appointment a2 = new Appointment(2L, 1L, 3L, "2024-01-02", "11:00 AM");
        List<Appointment> mockList = Arrays.asList(a1, a2);

        Mockito.when(appointmentService.getAllAppointments()).thenReturn(mockList);

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
