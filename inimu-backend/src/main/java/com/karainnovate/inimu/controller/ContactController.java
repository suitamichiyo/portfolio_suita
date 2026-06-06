package com.karainnovate.inimu.controller;

import com.karainnovate.inimu.mapper.ContactMapper;
import com.karainnovate.inimu.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContactController {

    private final ContactMapper contactMapper;

    @PostMapping("/contacts")
    public ResponseEntity<Map<String, String>> createContact(
            @RequestBody Contact contact) {
        contactMapper.insert(contact);
        return ResponseEntity.ok(Map.of("message", "お問い合わせを受け付けました"));
    }
}
