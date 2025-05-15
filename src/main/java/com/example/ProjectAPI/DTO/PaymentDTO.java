package com.example.ProjectAPI.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {
    private int amount;
    private String bankCode;
    private String language;
    private String ipAddress;
    private Long orderId;
}
