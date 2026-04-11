package com.bit.solutions.parking_system.notification.strategy;
import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.notification.Email.service.Interfaces.EmailSenderService;
import com.bit.solutions.parking_system.notification.Email.service.Interfaces.EmailTemplateService;
import com.bit.solutions.parking_system.notification.QR.QrCodeService;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationStrategy implements NotificationStrategy{
    private final QrCodeService qrCodeService;
    private final EmailTemplateService emailTemplateService;
    private final EmailSenderService emailSenderService;

    @Override
    public boolean supports(NotificationMethod method) {
        return NotificationMethod.EMAIL.equals(method);
    }
    @Override
    public void send(Record record) {
        if (record == null) {
            log.warn("Attempt to send Email notification with null record");
            return;
        }

        String to = record.getNotificationTarget();

        if (to == null || to.isBlank()) {
            log.warn("Record id={} has no email target configured", record.getId());
            return;
        }

        String subject = buildSubject(record);
        String htmlContent = emailTemplateService.buildParkingTicketHtml(record);
        byte[] qrCode = qrCodeService.generateQrCode(record.getTicketCode());

        emailSenderService.sendParkingTicketEmail(
                to,
                subject,
                htmlContent,
                qrCode
        );

        log.info("Parking ticket email sent successfully to={} for ticketCode={}",
                to,
                record.getTicketCode());
    }

    private String buildSubject(Record record) {
        return "Your parking ticket " + record.getPlate();
    }
}
