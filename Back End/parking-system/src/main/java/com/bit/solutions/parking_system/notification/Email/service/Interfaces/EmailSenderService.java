package com.bit.solutions.parking_system.notification.Email.service.Interfaces;

public interface EmailSenderService {
    void sendParkingTicketEmail(String to, String subject, String htmlContent, byte[] qrCode);
}
