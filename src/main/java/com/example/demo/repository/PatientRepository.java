import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Đối tượng Entity Patient giả định đã được định nghĩa
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Tìm bệnh nhân theo email
    Optional<Patient> findByEmail(String email);

    // Tìm bệnh nhân theo số điện thoại
    Optional<Patient> findByPhone(String phone);
}

