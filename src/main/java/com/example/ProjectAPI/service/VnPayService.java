package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.PaymentDTO;
import com.example.ProjectAPI.Util.VnPayUtil;
import com.example.ProjectAPI.config.PaymentConfig;
import com.example.ProjectAPI.model.Order;
import com.example.ProjectAPI.model.Payment;
import com.example.ProjectAPI.repository.OrderRepository;
import com.example.ProjectAPI.repository.PaymentRepository;
import com.example.ProjectAPI.service.impl.NotificationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class VnPayService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    private NotificationService notificationService;

    public ResponseEntity<?> createPayment(PaymentDTO request) {
        try {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            long amount = request.getAmount() * 100L;
            String bankCode = request.getBankCode();

            String vnp_TxnRef = request.getOrderId().toString();
            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order không tồn tại"));
            Payment payment = new Payment();
            payment.setTransactionRef(vnp_TxnRef);
            payment.setStatus("pending");
            payment.setAmount(request.getAmount());
            payment.setPaymentMethod("VNPAY");
            //payment.setDeviceToken(request.getDe);
            payment.setOrder(order);
            payment.setCreatedAt(LocalDate.now());
            paymentRepository.save(payment);
            String vnp_IpAddr = request.getIpAddress();  // lấy từ client Android gửi lên
            String vnp_TmnCode = PaymentConfig.VNPAY_TMN_CODE;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }

            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", request.getLanguage() != null ? request.getLanguage() : "vn");
            vnp_Params.put("vnp_ReturnUrl", PaymentConfig.VNPAY_RETURN_URL);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (Iterator<String> it = fieldNames.iterator(); it.hasNext();) {
                String name = it.next();
                String value = vnp_Params.get(name);
                if (value != null && !value.isEmpty()) {
                    hashData.append(name).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
                    query.append(URLEncoder.encode(name, StandardCharsets.US_ASCII)).append('=')
                            .append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
                    if (it.hasNext()) {
                        hashData.append('&');
                        query.append('&');
                    }
                }
            }

            String secureHash = VnPayUtil.hmacSHA512(PaymentConfig.VNPAY_SECRET_KEY, hashData.toString());
            query.append("&vnp_SecureHash=").append(secureHash);
            String paymentUrl = PaymentConfig.VNPAY_PAY_URL + "?" + query;

            // Trả về JSON thông qua ResponseEntity
            Map<String, Object> response = new HashMap<>();
            response.put("code", "00");
            response.put("message", "success");
            response.put("data", paymentUrl);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", "99");
            error.put("message", "Lỗi tạo URL thanh toán: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
