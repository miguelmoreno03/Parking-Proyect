package com.bit.solutions.parking_system.notification.Email.Interfaces;

public interface EmailSenderService {
    void sendParkingTicketEmail(String to, String subject, String htmlContent, byte[] qrCode);
}
