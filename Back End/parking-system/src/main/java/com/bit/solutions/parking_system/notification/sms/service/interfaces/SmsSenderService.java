package com.bit.solutions.parking_system.notification.sms.service.interfaces;

public interface SmsSenderService {
    void sendSms(String phoneNumber, String message);
}
