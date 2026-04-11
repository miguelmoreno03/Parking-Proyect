package com.bit.solutions.parking_system.notification.service.implement;
import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import com.bit.solutions.parking_system.notification.service.interfaces.NotificationService;
import com.bit.solutions.parking_system.notification.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final List<NotificationStrategy> strategies;

    @Override
    public void sendNotification(Record record) {
        if (record == null) {
            log.warn("Attempt to send notification with null record");
            return;
        }

        NotificationMethod method = record.getNotificationMethod();

        if (method == null) {
            log.warn("Record id={} has no notification method", record.getId());
            return;
        }

        NotificationStrategy strategy = strategies.stream()
                .filter(s -> s.supports(method))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No notification strategy found for method={} recordId={}", method, record.getId());
                    return new IllegalStateException("No notification strategy found for method: " + method);
                });

        strategy.send(record);

    }
}

