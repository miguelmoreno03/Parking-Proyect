package com.bit.solutions.parking_system.notification.strategy;

import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WhatsappNotificationStrategy implements NotificationStrategy{
    @Override
    public boolean supports(NotificationMethod method) {
        return NotificationMethod.WHATSAPP.equals(method);
    }

    @Override
    public void send(Record record) {
        if (record == null) {
            log.warn("Attempt to send WhatsApp notification with null record");
            return;
        }

        String message = buildMessage(record);

        log.info("Sending WhatsApp notification. message='{}'", message);

        // Aquí después irá la integración real con proveedor de WhatsApp
        // Ejemplo futuro:
        // 1. obtener teléfono del usuario
        // 2. construir mensaje
        // 3. llamar API externa
    }

    private String buildMessage(Record record) {
        return "Hello, your parking ticket was created successfully. " +
                "Plate: " + record.getPlate() +
                ", Entry time: " + record.getEntryTime() +
                ", Ticket code: " + record.getTicketCode();
    }
}
