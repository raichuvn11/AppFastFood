package com.example.ProjectAPI.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Value("${vnpay.payUrl}")
    private String payUrl;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String secret;

    public static String VNPAY_PAY_URL;
    public static String VNPAY_RETURN_URL;
    public static String VNPAY_TMN_CODE;
    public static String VNPAY_SECRET_KEY;

    @PostConstruct
    public void init() {
        VNPAY_PAY_URL = payUrl;
        VNPAY_RETURN_URL = returnUrl;
        VNPAY_TMN_CODE = tmnCode;
        VNPAY_SECRET_KEY = secret;
    }
}

