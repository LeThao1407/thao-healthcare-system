@RestController
@RequestMapping("/api/{role}/doctor")
public class DoctorController {

    // Giả sử có service để xác thực token và lấy trạng thái bác sĩ
    private final AuthService authService;
    private final DoctorService doctorService;

    public DoctorController(AuthService authService, DoctorService doctorService) {
        this.authService = authService;
        this.doctorService = doctorService;
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<?> checkDoctorAvailability(
        @PathVariable String role,
        @PathVariable Long doctorId,
        @RequestParam String token
    ) {
        // Kiểm tra vai trò hợp lệ (ví dụ chỉ patient và admin được truy cập)
        if (!role.equalsIgnoreCase("patient") && !role.equalsIgnoreCase("admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid role");
        }

        // Xác thực token theo vai trò
        boolean isTokenValid = authService.validateTokenForRole(token, role);
        if (!isTokenValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid or expired token");
        }

        // Lấy trạng thái rảnh/bận của bác sĩ (ví dụ true = rảnh)
        boolean isAvailable = doctorService.isDoctorAvailable(doctorId);

        return ResponseEntity.ok(Collections.singletonMap("available", isAvailable));
    }
}
