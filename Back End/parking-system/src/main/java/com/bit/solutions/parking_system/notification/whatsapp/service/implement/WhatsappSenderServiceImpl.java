package com.bit.solutions.parking_system.notification.whatsapp.service.implement;

import com.bit.solutions.parking_system.notification.whatsapp.service.interfaces.WhatsappSenderService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WhatsappSenderServiceImpl implements WhatsappSenderService {
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.whatsapp-from}")
    private String fromWhatsapp;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendWhatsapp(String phoneNumber, String message) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            log.warn("Attempt to send WhatsApp with empty phone number");
            return;
        }

        if (message == null || message.isBlank()) {
            log.warn("Attempt to send WhatsApp with empty message to={}", phoneNumber);
            return;
        }

        try {
            Message twilioMessage = Message.creator(
                    new PhoneNumber("whatsapp:" + phoneNumber),
                    new PhoneNumber(fromWhatsapp),
                    message
            ).create();

            log.info("WhatsApp sent successfully to={} sid={}", phoneNumber, twilioMessage.getSid());
        } catch (Exception e) {
            log.error("Error sending WhatsApp to={}: {}", phoneNumber, e.getMessage(), e);
            throw new IllegalStateException("Error sending WhatsApp", e);
        }
    }
}
