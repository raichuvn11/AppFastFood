package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.OtpVerification;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.OtpVerificationRepository;
import com.example.ProjectAPI.repository.UserRepository;
import com.example.ProjectAPI.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private EmailService emailService;

    // Đăng ký và gửi OTP
    public ResponseEntity<?> registerUser(String username, String email, String password) {
        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Email already exists!"));
        }

        // Tạo OTP
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // 6 chữ số OTP
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setCreatedAt(LocalDateTime.now());
        otpVerification.setVerified(false);
        otpVerificationRepository.save(otpVerification);

        // Gửi OTP qua email
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok(Map.of("status", "success", "message", "OTP sent to your email. Please verify."));
    }

    // Xác thực OTP
    public ResponseEntity<?> verifyOtp(String username, String email, String password, String otp) {
        // Kiểm tra OTP trong database
        Optional<OtpVerification> otpVerification = otpVerificationRepository.findByEmailAndOtp(email, otp);

        if (otpVerification.isEmpty() || otpVerification.get().isVerified()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Invalid or already used OTP!"));
        }

        // Kiểm tra thời gian hết hạn của OTP
        if (otpVerification.get().getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "OTP expired!"));
        }

        // Đánh dấu OTP là đã được xác thực
        OtpVerification verifiedOtp = otpVerification.get();
        verifiedOtp.setVerified(true);
        otpVerificationRepository.save(verifiedOtp);

        // Lưu người dùng vào hệ thống sau khi xác thực OTP
        User user = new User();
        user.setUsername(username); // username sẽ được lấy từ một nơi nào đó, ví dụ một yêu cầu khác
        user.setEmail(email);
        user.setPassword(password); // Nên mã hóa mật khẩu trong thực tế
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("status", "success", "message", "User registered successfully!"));
    }

    public ResponseEntity<?> loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "User not found!"));
        }

        User user = userOptional.get();

        // Kiểm tra mật khẩu (nên mã hóa mật khẩu trong thực tế)
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Invalid password!"));
        }

        // Trả về thông tin người dùng khi đăng nhập thành công
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Login successful!",
                "user", Map.of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "email", user.getEmail()
                )
        ));
    }

    private final Map<String, String> otpStore = new HashMap<>(); // Lưu OTP tạm thời

    // Gửi OTP qua email
    public ResponseEntity<?> sendResetOtp(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "User not found!"));
        }

        String otp = String.valueOf((int)(Math.random() * 9000) + 1000); // Tạo OTP 4 chữ số
        otpStore.put(email, otp);

        // Gửi OTP qua email
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok(Map.of("status", "success", "message", "OTP sent to your email!"));
    }
    // Đặt lại mật khẩu
    public ResponseEntity<?> resetPassword(String email, String otp, String newPassword) {
        if (!otpStore.containsKey(email) || !otpStore.get(email).equals(otp)) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Invalid OTP!"));
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "User not found!"));
        }

        User user = userOptional.get();
        user.setPassword(newPassword); // Nên mã hóa mật khẩu trong thực tế
        userRepository.save(user);

        // Xóa OTP sau khi sử dụng
        otpStore.remove(email);

        return ResponseEntity.ok(Map.of("status", "success", "message", "Password reset successfully!"));
    }
}




