package com.example.ProjectAPI.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đường dẫn thư mục lưu ảnh bên trong project
        String uploadDir = Paths.get("uploads/avatar").toAbsolutePath().toString();

        registry.addResourceHandler("/uploads/avatar/**")
                .addResourceLocations("file:" + uploadDir + "/"); // Quan trọng: phải có "file:"
    }
}
