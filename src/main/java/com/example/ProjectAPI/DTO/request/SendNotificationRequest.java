package com.example.ProjectAPI.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendNotificationRequest {
    private String title;
    private String body;
}
