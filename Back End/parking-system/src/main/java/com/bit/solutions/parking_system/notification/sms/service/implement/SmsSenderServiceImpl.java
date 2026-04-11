package com.bit.solutions.parking_system.notification.sms.service.implement;

import com.bit.solutions.parking_system.notification.sms.service.interfaces.SmsSenderService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsSenderServiceImpl implements SmsSenderService {
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.sms-from}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            log.warn("Attempt to send SMS with empty phone number");
            return;
        }

        if (message == null || message.isBlank()) {
            log.warn("Attempt to send SMS with empty message to={}", phoneNumber);
            return;
        }

        try {
            Message twilioMessage = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(fromNumber),
                    message
            ).create();

            log.info("SMS sent successfully to={} sid={}", phoneNumber, twilioMessage.getSid());
        } catch (Exception e) {
            log.error("Error sending SMS to={}: {}", phoneNumber, e.getMessage(), e);
            throw new IllegalStateException("Error sending SMS", e);
        }
    }
}
