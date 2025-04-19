package com.example.ProjectAPI.service.impl;

import com.example.ProjectAPI.DTO.CouponDTO;
import com.example.ProjectAPI.model.Coupon;
import com.example.ProjectAPI.repository.CouponRepository;
import com.example.ProjectAPI.service.intf.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CouponServiceImp implements ICouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public ResponseEntity<?> applyCoupon(String code) {
        Coupon coupon = couponRepository.findCouponByCode(code);
        if (coupon == null) {
            return ResponseEntity.status(404).body("không tìm thấy mã giảm giá!");
        }
        if (coupon.getExpiryDate().isBefore(LocalDate.now())) {
            return ResponseEntity.status(400).body("Mã giảm giá đã hết hạn!");
        }
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setCode(code);
        couponDTO.setType(coupon.getDiscountType().name());
        couponDTO.setDiscount(coupon.getDiscountValue());
        return ResponseEntity.ok(couponDTO);
    }
}
