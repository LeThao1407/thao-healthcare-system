@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final AuthService authService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, AuthService authService) {
        this.prescriptionService = prescriptionService;
        this.authService = authService;
    }

    @PostMapping("/{token}")
    public ResponseEntity<?> createPrescription(
        @PathVariable String token,
        @Valid @RequestBody PrescriptionRequest prescriptionRequest,
        BindingResult bindingResult
    ) {
        // Kiểm tra lỗi validate dữ liệu đầu vào
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // Xác thực token
        if (!authService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        // Tạo đơn thuốc
        Prescription prescription = prescriptionService.createPrescription(prescriptionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(prescription);
    }
}
