import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // Khóa bí mật dùng để ký JWT (ở đây tạo ngẫu nhiên, bạn nên cấu hình từ file properties)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Thời gian hiệu lực token (ví dụ 1 giờ)
    private final long expirationMillis = 3600000;

    /**
     * Tạo JWT token dựa trên email
     * @param email Email của người dùng
     * @return JWT token dưới dạng String
     */
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(email)             // Đặt email làm subject trong token
                .setIssuedAt(now)              // Thời điểm phát hành token
                .setExpiration(expiryDate)    // Thời gian hết hạn token
                .signWith(key)                // Ký token với khóa bí mật
                .compact();
    }
}
