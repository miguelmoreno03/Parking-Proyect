package com.bit.solutions.parking_system.notification.service.interfaces;
import com.bit.solutions.parking_system.entity.Record;

public interface NotificationService {
    void sendNotification(Record record);
}
