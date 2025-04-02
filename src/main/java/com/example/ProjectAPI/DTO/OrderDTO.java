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
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderTime;
    private String orderStatus;
    private double orderTotal;
    @JsonProperty("orderItems")
    private List<OrderItemDTO> orderItemDTOS;
}
