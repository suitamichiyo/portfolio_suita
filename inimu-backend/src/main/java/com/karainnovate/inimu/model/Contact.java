package com.karainnovate.inimu.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Contact {
    private Long id;
    private String category;
    private String name;
    private String company;
    private String email;
    private String message;
    private String status;
    private LocalDateTime createdAt;
}
