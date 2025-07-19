package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    // Lấy danh sách tất cả bác sĩ
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Lấy bác sĩ theo ID
    @GetMapping("/{id}")
    public Optional<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id);
    }

    // Thêm bác sĩ mới
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Cập nhật thông tin bác sĩ
    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctor.setName(updatedDoctor.getName());
                    doctor.setSpecialization(updatedDoctor.getSpecialization());
                    return doctorRepository.save(doctor);
                })
                .orElseGet(() -> {
                    updatedDoctor.setId(id);
                    return doctorRepository.save(updatedDoctor);
                });
    }

    // Xóa bác sĩ
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
    }
}
