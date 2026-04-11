package com.bit.solutions.parking_system.notification.strategy;
import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import com.bit.solutions.parking_system.notification.sms.service.interfaces.SmsSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@AllArgsConstructor
public class SmsNotificationStrategy implements NotificationStrategy{
    private final SmsSenderService smsSenderService;
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

        String phoneNumber = record.getNotificationTarget();

        if (phoneNumber == null || phoneNumber.isBlank()) {
            log.warn("Record id={} has no phone number configured for SMS", record.getId());
            return;
        }

        String message = buildMessage(record);

        smsSenderService.sendSms(phoneNumber, message);
    }

    private String buildMessage(Record record) {
        String formattedEntryTime = record.getEntryTime() != null
                ? record.getEntryTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "";

        String rateType = record.getRate() != null && record.getRate().getType() != null
                ? record.getRate().getType().name()
                : "";

        return "Parking ticket created successfully. " +
                " Rate type: " + rateType +
                ", Plate:  " + record.getPlate() +
                ", Entry time " + formattedEntryTime +
                ", Ticket code: " + record.getTicketCode() ;
    }
}
