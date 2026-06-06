package com.karainnovate.inimu.controller;

import com.karainnovate.inimu.mapper.ReservationMapper;
import com.karainnovate.inimu.mapper.WorkshopSlotMapper;
import com.karainnovate.inimu.model.Reservation;
import com.karainnovate.inimu.model.WorkshopSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final WorkshopSlotMapper slotMapper;
    private final ReservationMapper reservationMapper;

    @GetMapping("/reservations/slots")
    public ResponseEntity<List<WorkshopSlot>> getSlots(
            @RequestParam String from,
            @RequestParam String to) {
        List<WorkshopSlot> slots = slotMapper.findByDateRange(
            LocalDate.parse(from), LocalDate.parse(to));
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Map<String, Object>> createReservation(
            @RequestBody Reservation reservation) {
        WorkshopSlot slot = slotMapper.findById(reservation.getSlotId());
        if (slot == null) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "指定されたスロットが存在しません"));
        }
        int remaining = slot.getCapacity() - slot.getReservedCount();
        if (remaining < reservation.getNumPeople()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "空き枠が不足しています"));
        }
        reservationMapper.insert(reservation);
        slotMapper.incrementReservedCount(slot.getId(), reservation.getNumPeople());
        return ResponseEntity.ok(Map.of(
            "message", "予約が完了しました",
            "reservationId", reservation.getId()
        ));
    }
}
