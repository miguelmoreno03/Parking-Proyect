package com.bit.solutions.parking_system.notification.whatsapp.service.interfaces;

public interface WhatsappSenderService {
    void sendWhatsapp(String phoneNumber, String message);
}
