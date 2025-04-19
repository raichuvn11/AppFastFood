package com.example.ProjectAPI.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderStatusDTO {
    private Long id;
    private String date;
    private String status;
    private Double total;
}
