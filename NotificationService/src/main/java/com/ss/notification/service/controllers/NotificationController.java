package com.ss.notification.service.controllers;

import com.ss.notification.service.dto.EmailRequest;
import com.ss.notification.service.entities.Notification;
import com.ss.notification.service.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public Notification sendEmail(@RequestBody EmailRequest request) {
        return emailService.sendEmail(request.getTo(), request.getSubject(), request.getMessage());
    }
}
