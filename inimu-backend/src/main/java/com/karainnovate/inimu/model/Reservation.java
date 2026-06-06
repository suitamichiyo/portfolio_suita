package com.karainnovate.inimu.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private Long slotId;
    private String name;
    private String nameKana;
    private String email;
    private String phone;
    private int numPeople;
    private String allergyNote;
    private String status;
    private LocalDateTime createdAt;
}
