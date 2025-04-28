package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.UserDTO;
import com.example.ProjectAPI.Util.JwtUtil;
import com.example.ProjectAPI.model.OtpVerification;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.OtpVerificationRepository;
import com.example.ProjectAPI.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class UserService {

    private static final String CLIENT_ID = "288243577014-798i3784366217gjfi218kdb53gsurhd.apps.googleusercontent.com";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private EmailService emailService;

    @Value("${upload.dir}")
    private String uploadDir;

    private final Map<String, String> otpStore = new HashMap<>();

    // Salt cố định
    private static final String SALT = "SaltAppFastFood057";

    // Mã hóa mật khẩu với SHA-256
    private String encodePassword(String password) {
        System.out.println(password);
        String saltedPassword = password + SALT;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // So sánh mật khẩu
    private boolean matches(String rawPassword, String hashedPassword) {
        System.out.println(encodePassword(rawPassword));
        System.out.println(hashedPassword);
        return encodePassword(rawPassword).equals(hashedPassword);
    }

    // Trả về lỗi
    private ResponseEntity<?> buildErrorResponse(String message) {
        return ResponseEntity.ok(Map.of(
                "status", "error",
                "message", message
        ));
    }

    // Trả về thành công
    private ResponseEntity<?> buildSuccessResponse(String message, Object data) {
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", message,
                "data", data != null ? data : new HashMap<>()
        ));
    }


    // Tạo OTP ngẫu nhiên 6 chữ số
    private String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
    //Đăng nhập bằng google
    public ResponseEntity<?> loginWithGoogle(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                    .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID Token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setUsername(name);
                user.setAvatar(pictureUrl);
                userRepository.save(user); // lưu user mới
            }

            /*String token = JwtUtil.generateToken(email);
            user.setDeviceToken(token);
            userRepository.save(user);*/ // cập nhật lại token (nếu cần)

            return buildSuccessResponse("Login successful!", Map.of(
                    "userId", user.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    // Đăng ký: tạo OTP và gửi qua email
    public ResponseEntity<?> registerUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            return buildErrorResponse("Email already exists!");
        }

        String otp = generateOtp();
        OtpVerification otpEntity = new OtpVerification(email, otp, LocalDateTime.now(), false);
        otpVerificationRepository.save(otpEntity);

        emailService.sendOtpEmail(email, otp);
        return buildSuccessResponse("OTP sent to your email. Please verify.", null);
    }


    // Xác thực OTP và đăng ký user
    public ResponseEntity<?> verifyOtpAndRegister(String username, String email, String password, String otp) {
        Optional<OtpVerification> optionalOtp = otpVerificationRepository.findByEmailAndOtp(email, otp);

        if (optionalOtp.isEmpty() || optionalOtp.get().isVerified()) {
            return buildErrorResponse("Invalid or already used OTP!");
        }

        OtpVerification otpVerification = optionalOtp.get();
        if (otpVerification.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            return buildErrorResponse("OTP expired!");
        }

        otpVerification.setVerified(true);
        otpVerificationRepository.save(otpVerification);

        // Mã hóa mật khẩu với SHA-256 và salt cố định
        String encodedPassword = encodePassword(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);  // Lưu mật khẩu đã mã hóa
        userRepository.save(user);
        otpStore.remove(email);
        return buildSuccessResponse("User registered successfully!", null);
    }

    // Đăng nhập
    public ResponseEntity<?> loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return buildErrorResponse("User not found!");
        }

        User user = userOptional.get();

        if (!matches(password, user.getPassword())) {
            return buildErrorResponse("Invalid password!");
        }

        // Tạo token
        /*String token = JwtUtil.generateToken(user.getEmail());
        user.setDeviceToken(token);
        userRepository.save(user);*/
        return buildSuccessResponse("Login successful!", Map.of(
                "userId", user.getId()
        ));
    }


    // Gửi OTP đặt lại mật khẩu
    public ResponseEntity<?> sendResetOtp(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return buildErrorResponse("User not found!");
        }

        String otp = generateOtp();
        OtpVerification otpEntity = new OtpVerification(email, otp, LocalDateTime.now(), false);
        otpVerificationRepository.save(otpEntity);

        emailService.sendOtpEmail(email, otp);

        return buildSuccessResponse("OTP sent to your email!", null);
    }

    // Kiểm tra OTP và email khi quên mật khẩu
    public ResponseEntity<?> checkOtpAndEmailForForgotPassword(String email, String otp) {
        Optional<OtpVerification> otpVerificationOptional = otpVerificationRepository.findByEmailAndOtp(email, otp);

        if (otpVerificationOptional.isEmpty()) {
            return buildErrorResponse("Invalid OTP or email mismatch.");
        }

        OtpVerification otpVerification = otpVerificationOptional.get();

        // Kiểm tra nếu OTP đã hết hạn
        if (otpVerification.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            return buildErrorResponse("OTP expired.");
        }

        if (otpVerification.isVerified()) {
            return buildErrorResponse("OTP already used.");
        }

        return buildSuccessResponse("OTP is valid. You can reset your password.", null);
    }

    // Đặt lại mật khẩu
    public ResponseEntity<?> resetPassword(String email, String otp, String newPassword) {
        Optional<OtpVerification> optionalOtp = otpVerificationRepository.findByEmailAndOtp(email, otp);
        System.out.println(email + " " + otp);
        if (optionalOtp.isEmpty() || optionalOtp.get().isVerified()) {

            return buildErrorResponse("Invalid or already used OTP!");
        }

        OtpVerification otpVerification = optionalOtp.get();
        if (otpVerification.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            return buildErrorResponse("OTP expired!");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return buildErrorResponse("User not found!");
        }

        User user = userOptional.get();
        user.setPassword(encodePassword(newPassword));
        userRepository.save(user);

        otpStore.remove(email);

        return buildSuccessResponse("Password reset successfully!", null);
    }

    // get user infomation
    public ResponseEntity<?> getUseProfile(int userId){
        Optional<User> userOptional = userRepository.findById(String.valueOf(userId));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());
            userDTO.setImg(user.getAvatar());
            return ResponseEntity.ok(userDTO);
        }
        else{
            return ResponseEntity.status(404).body("User not found!");
        }
    }

    // Phương thức cập nhật địa chỉ người dùng
    public User updateAddress(Long userId, String newAddress) {
        // Kiểm tra nếu người dùng tồn tại
        User user = userRepository.findById(userId).orElse(null);
        newAddress = newAddress.replace("\"", "");
        if (user != null) {
            // Cập nhật địa chỉ
            user.setAddress(newAddress);
            return userRepository.save(user); // Lưu lại thông tin người dùng
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    // update user profile
    public ResponseEntity<?> updateUserProfile(UserDTO userDTO, MultipartFile avatarFile) {

        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/avatar";

        Optional<User> userOptional = userRepository.findById(userDTO.getId().toString());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!userDTO.getUsername().isEmpty()) {
                user.setUsername(userDTO.getUsername());
            }
            if (!userDTO.getEmail().isEmpty()) {
                user.setEmail(userDTO.getEmail());
            }
            if (!userDTO.getPhone().isEmpty()) {
                user.setPhone(userDTO.getPhone());
            }
            if (!userDTO.getAddress().isEmpty()) {
                user.setAddress(userDTO.getAddress());
            }

            // Xử lý ảnh nếu có
            if (avatarFile != null && !avatarFile.isEmpty()) {
                try {
                    // Tạo thư mục nếu chưa tồn tại
                    Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath); // Đảm bảo thư mục tồn tại
                        System.out.println("Created directory: " + uploadPath);
                    }

                    String fileName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);

                    // Lưu file vào thư mục project
                    avatarFile.transferTo(filePath.toFile());
                    System.out.println("Saved file to: " + filePath);
                    String avatarPath = "uploads/avatar/" + fileName;
                    user.setAvatar(avatarPath);
                    System.out.println("Avatar path set: " + user.getAvatar());
                } catch (IOException e) {
                    System.err.println("Error saving file: " + e.getMessage());
                    return ResponseEntity.status(500).body("Lỗi khi lưu ảnh: " + e.getMessage());
                }
            } else {
                System.out.println("Không có avatar part ");
            }

            try {
                System.out.println("Saving user to repository: " + user.getUsername());
                userRepository.save(user);
            } catch (Exception e) {
                System.err.println("Error saving to repository: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(500).body("Lỗi khi lưu vào DB: " + e.getMessage());
            }

            return ResponseEntity.ok("Update profile successful");
        }
        return ResponseEntity.status(500).body("Không tìm thấy user hoặc lỗi lưu avatar");
    }
}




