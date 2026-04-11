package com.bit.solutions.parking_system.notification.Email.service.Implement;
import com.bit.solutions.parking_system.notification.Email.service.Interfaces.EmailTemplateService;
import com.bit.solutions.parking_system.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private  final TemplateEngine templateEngine;

    @Override
    public String buildParkingTicketHtml(Record record) {
        Context context = new Context();

        String formattedEntryTime = record.getEntryTime() != null
                ? record.getEntryTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "";

        String rateType = "";
        if (record.getRate() != null && record.getRate().getType() != null) {
            rateType = record.getRate().getType().name();
        }

        context.setVariable("rateType", rateType);
        context.setVariable("plate", record.getPlate());
        context.setVariable("entryTime", formattedEntryTime);

        return templateEngine.process("email/parking-ticket", context);
    }
}
