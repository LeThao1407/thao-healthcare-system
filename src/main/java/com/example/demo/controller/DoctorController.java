@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    // Existing CRUD methods...

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<?> getDoctorAvailability(
        @PathVariable Long doctorId,
        @RequestParam String date,  // Format: yyyy-MM-dd
        @RequestHeader("Authorization") String token) {

        // Xác thực token ở đây nếu cần
        if (!validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // Chuyển đổi ngày sang LocalDate
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Expected yyyy-MM-dd");
        }

        // Gọi service để lấy danh sách khung giờ đã đặt
        List<LocalTime> bookedSlots = appointmentService.getBookedSlots(doctorId, localDate);

        // Giả sử bác sĩ làm việc từ 8:00 đến 17:00, mỗi khung 1h
        List<LocalTime> allSlots = IntStream.range(8, 17)
            .mapToObj(LocalTime::of)
            .collect(Collectors.toList());

        List<LocalTime> availableSlots = allSlots.stream()
            .filter(slot -> !bookedSlots.contains(slot))
            .collect(Collectors.toList());

        return ResponseEntity.ok(availableSlots);
    }

    // (Ví dụ đơn giản) Xác thực token giả lập
    private boolean validateToken(String token) {
        return token != null && token.startsWith("Bearer ");
    }
}
public List<LocalTime> getBookedSlots(Long doctorId, LocalDate date) {
    List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
        doctorId,
        date.atStartOfDay(),
        date.plusDays(1).atStartOfDay()
    );

    return appointments.stream()
            .map(app -> app.getAppointmentTime().toLocalTime())
            .collect(Collectors.toList());
}
