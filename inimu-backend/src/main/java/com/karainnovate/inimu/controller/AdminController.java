package com.karainnovate.inimu.controller;

import com.karainnovate.inimu.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminMapper adminMapper;

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("reservations", adminMapper.findAllReservations());
        model.addAttribute("contacts", adminMapper.findAllContacts());
        return "admin/dashboard";
    }

    @PostMapping("/reservations/{id}/status")
    public String updateReservationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        adminMapper.updateReservationStatus(id, status);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/contacts/{id}/status")
    public String updateContactStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        adminMapper.updateContactStatus(id, status);
        return "redirect:/admin/dashboard";
    }
}
