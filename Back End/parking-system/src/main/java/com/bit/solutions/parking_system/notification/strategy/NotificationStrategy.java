package com.bit.solutions.parking_system.notification.strategy;

import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;

public interface NotificationStrategy {
    boolean supports (NotificationMethod method);
    void send(Record record);
}
