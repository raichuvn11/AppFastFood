package com.example.ProjectAPI.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otp;
    private LocalDateTime createdAt;
    private boolean verified;

    public OtpVerification(String email, String otp, LocalDateTime createdAt, boolean verified) {
        this.email = email;
        this.otp = otp;
        this.createdAt = createdAt;
        this.verified = verified;
    }

    public OtpVerification() {}
}