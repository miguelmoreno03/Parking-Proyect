package com.bit.solutions.parking_system.notification.strategy;

import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.springframework.core.NestedExceptionUtils.buildMessage;

@Component
@Slf4j
public class SmsNotificationStrategy implements NotificationStrategy{
    @Override
    public boolean supports(NotificationMethod method) {
        return NotificationMethod.SMS.equals(method);
    }

    @Override
    public void send(Record record) {
        if (record == null) {
            log.warn("Attempt to send SMS notification with null record");
            return;
        }

        String message = buildMessage(record);

        log.info("Sending SMS notification. message='{}'", message);


        // Aquí después irá la integración real con proveedor SMS
        // Ejemplo futuro:
        // 1. obtener teléfono del usuario
        // 2. construir mensaje
        // 3. llamar API externa
    }

    private String buildMessage(Record record) {
        return "Parking ticket created successfully. " +
                "Plate: " + record.getPlate() +
                ", Entry time: " + record.getEntryTime() +
                ", Ticket code: " + record.getTicketCode();
    }
}
