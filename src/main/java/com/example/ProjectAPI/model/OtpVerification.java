package com.example.ProjectAPI.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OtpVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otp;
    private LocalDateTime createdAt;
    private boolean verified;

    // Getter cho id
    public Long getId() {
        return id;
    }

    // Setter cho id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter cho email
    public String getEmail() {
        return email;
    }

    // Setter cho email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter cho otp
    public String getOtp() {
        return otp;
    }

    // Setter cho otp
    public void setOtp(String otp) {
        this.otp = otp;
    }

    // Getter cho createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setter cho createdAt
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter cho verified
    public boolean isVerified() {
        return verified;
    }

    // Setter cho verified
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

