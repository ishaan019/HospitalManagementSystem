package com.ss.notification.service.services;

import com.ss.notification.service.entities.Notification;
import com.ss.notification.service.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Send a notification (mock SMS/Email)
    public Notification sendNotification(Notification notification) {
        try {
            // TODO: integrate with actual SMS/Email provider
            notification.setStatus("SENT");
        } catch (Exception e) {
            notification.setStatus("FAILED");
        }
        return notificationRepository.save(notification);
    }

    public Page<Notification> listNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    public Page<Notification> filterNotifications(String recipient, String type, String status, Pageable pageable) {
        if (recipient != null) {
            return notificationRepository.findByRecipient(recipient, pageable);
        } else if (type != null) {
            return notificationRepository.findByType(type, pageable);
        } else if (status != null) {
            return notificationRepository.findByStatus(status, pageable);
        } else {
            return notificationRepository.findAll(pageable);
        }
    }
}
