package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.request.*;
import com.example.ProjectAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;



    // Đăng ký và gửi OTP
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
    }

    // Xác thực OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return userService.verifyOtpAndRegister(
                request.getUsername(), request.getEmail(), request.getPassword(), request.getOtp());
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody GoogleLoginRequest request) {
        return userService.loginWithGoogle(request.getIdToken());
    }

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userService.loginUser(request.getEmail(), request.getPassword());
    }

    // Quên mật khẩu - gửi OTP
    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendResetOtp(@RequestBody EmailRequest request) {
        return userService.sendResetOtp(request.getEmail());
    }
    //Check OTP reset
    @PostMapping("/check-otp")
    public ResponseEntity<?> checkOTPResetPassword(@RequestBody CheckOTPResetPasswordRequest request) {
        return userService.checkOtpAndEmailForForgotPassword(request.getEmail(), request.getOtp());
    }
    // Đặt lại mật khẩu
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
    }
}
