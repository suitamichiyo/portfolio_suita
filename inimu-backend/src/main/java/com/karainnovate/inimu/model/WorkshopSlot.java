package com.karainnovate.inimu.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WorkshopSlot {
    private Long id;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int reservedCount;
    private boolean isActive;
}
