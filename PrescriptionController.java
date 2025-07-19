package com.example.clinic.controller;

import com.example.clinic.model.Prescription;
import com.example.clinic.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/patient/{patientId}")
    public List<Prescription> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return prescriptionService.getPrescriptionsByPatientId(patientId);
    }

    @PostMapping
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription updated) {
        return prescriptionService.updatePrescription(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
    }
}
