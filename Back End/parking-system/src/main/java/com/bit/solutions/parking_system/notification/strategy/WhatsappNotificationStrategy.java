package com.bit.solutions.parking_system.notification.strategy;

import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import com.bit.solutions.parking_system.notification.whatsapp.service.interfaces.WhatsappSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class WhatsappNotificationStrategy implements NotificationStrategy{
    private final WhatsappSenderService whatsappSenderService;

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

        String phoneNumber = record.getNotificationTarget();

        if (phoneNumber == null || phoneNumber.isBlank()) {
            log.warn("Record id={} has no phone number configured for WhatsApp", record.getId());
            return;
        }

        String message = buildMessage(record);

        whatsappSenderService.sendWhatsapp(phoneNumber, message);
    }

    private String buildMessage(Record record) {
        String formattedEntryTime = record.getEntryTime() != null
                ? record.getEntryTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "";

        String rateType = record.getRate() != null && record.getRate().getType() != null
                ? record.getRate().getType().name()
                : "";

        return "🎟️ Your parking ticket was created successfully.\n" +
                "🏷️ Rate type: " + rateType + "\n" +
                "🚗 Plate: " + record.getPlate() + "\n" +
                "🕒 Entry time: " + formattedEntryTime + "\n" +
                "🧾 Ticket code: " + record.getTicketCode() + "\n" +
                "Please keep this code for exit validation.";
    }
}

