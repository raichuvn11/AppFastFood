package com.example.ProjectAPI.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String img;
}
