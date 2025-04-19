package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findCouponByCode(String code);
}
