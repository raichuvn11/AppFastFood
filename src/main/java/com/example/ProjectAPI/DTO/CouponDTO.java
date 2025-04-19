package com.example.ProjectAPI.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponDTO {
    private String code;
    private String type;
    private double discount;
}
