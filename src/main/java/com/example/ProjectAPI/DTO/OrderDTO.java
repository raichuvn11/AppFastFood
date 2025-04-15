package com.example.ProjectAPI.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    private Long userId;
    private String orderAddress;
    private String orderTime;
    private String orderStatus;
    private double orderTotal;
    @JsonProperty("orderItems")
    private List<OrderItemDTO> orderItemDTOS;
    private int rating;
    private String review;
}
