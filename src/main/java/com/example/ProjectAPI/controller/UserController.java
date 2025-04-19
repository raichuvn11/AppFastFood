package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.DeviceTokenDTO;
import com.example.ProjectAPI.DTO.UserDTO;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.service.impl.NotificationService;
import com.example.ProjectAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserProfile(@RequestParam int userId) {
        return userService.getUseProfile(userId);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateUserProfile(@ModelAttribute UserDTO userDTO, @RequestPart(value = "file", required = false) MultipartFile file) {
        return userService.updateUserProfile(userDTO, file);
    }

    @PostMapping("/update-token")
    public String updateDeviceToken(@RequestBody DeviceTokenDTO deviceTokenDTO) {
        System.out.println("Device token>>>>>>" + deviceTokenDTO);
        return notificationService.updateDeviceToken(deviceTokenDTO);
    }

    @PutMapping("/{userId}/address")
    public ResponseEntity<UserDTO> updateAddress(@PathVariable Long userId, @RequestBody String newAddress) {
        User updatedUser = userService.updateAddress(userId, newAddress);
        if (updatedUser != null) {
            UserDTO userDTO = new UserDTO(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getPhone(),
                    updatedUser.getAddress(),
                    updatedUser.getAvatar()
            );
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
