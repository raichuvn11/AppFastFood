package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.PaymentDTO;
import com.example.ProjectAPI.service.VnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private VnPayService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO request) {
        return paymentService.createPayment(request);
    }
}
