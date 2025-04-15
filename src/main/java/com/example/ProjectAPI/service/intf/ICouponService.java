package com.example.ProjectAPI.service.intf;

import org.springframework.http.ResponseEntity;

public interface ICouponService {
    ResponseEntity<?> applyCoupon(String code);
}
