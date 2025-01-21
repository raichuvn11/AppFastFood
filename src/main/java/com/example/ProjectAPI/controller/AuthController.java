package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // API đăng ký và gửi OTP
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username,
                                      @RequestParam String email,
                                      @RequestParam String password) {
        // Gọi service để xử lý đăng ký và gửi OTP
        return userService.registerUser(username, email, password);
    }

    // API xác thực OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String username,
                                       @RequestParam String email,
                                       @RequestParam String password, @RequestParam String otp) {
        // Gọi service để xử lý xác thực OTP
        return userService.verifyOtp(username,email,password, otp);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }
    // Gửi OTP qua email để quên mật khẩu
    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendResetOtp(@RequestParam String email) {
        return userService.sendResetOtp(email);
    }

    // Đặt lại mật khẩu bằng OTP
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String otp,
                                           @RequestParam String newPassword) {
        return userService.resetPassword(email, otp, newPassword);
    }
}