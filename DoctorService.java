package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.project.entity.Doctor;
import com.example.project.entity.Appointment;
import com.example.project.entity.TimeSlot;
import com.example.project.repository.DoctorRepository;
import com.example.project.repository.ScheduleRepository;
import com.example.project.repository.AppointmentRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, 
                         ScheduleRepository scheduleRepository,
                         AppointmentRepository appointmentRepository,
                         PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
        this.appointmentRepository = appointmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<TimeSlot> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        // Lấy tất cả khung giờ làm việc của bác sĩ trong ngày
        List<TimeSlot> allSlots = scheduleRepository.findTimeSlotsByDoctorAndDate(doctorId, date);

        // Lấy các cuộc hẹn đã được đặt trong ngày đó
        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);

        // Loại bỏ các khung giờ đã bị đặt
        List<TimeSlot> availableSlots = allSlots.stream()
            .filter(slot -> bookedAppointments.stream()
                .noneMatch(appointment -> appointment.getTimeSlot().equals(slot)))
            .collect(Collectors.toList());

        return availableSlots;
    }

    public boolean authenticateDoctor(String email, String password) {
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        if (doctorOpt.isEmpty()) return false;

        Doctor doctor = doctorOpt.get();
        // So sánh mật khẩu đã mã hóa
        return passwordEncoder.matches(password, doctor.getPasswordHash());
    }

    // Các phương thức CRUD khác nếu cần
}
