package com.bit.solutions.parking_system.notification.Email.Interfaces;
import com.bit.solutions.parking_system.entity.Record;

public interface EmailTemplateService {
    String buildParkingTicketHtml(Record record);
}
