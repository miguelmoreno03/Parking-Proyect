package com.bit.solutions.parking_system.service.implement;
import com.bit.solutions.parking_system.entity.Rate;
import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.entity.User;
import com.bit.solutions.parking_system.entity.enums.Status;
import com.bit.solutions.parking_system.entity.enums.Type;
import com.bit.solutions.parking_system.exceptions.BadRequestException;
import com.bit.solutions.parking_system.exceptions.ResourceNotFoundException;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import com.bit.solutions.parking_system.notification.service.interfaces.NotificationService;
import com.bit.solutions.parking_system.repository.RateRepository;
import com.bit.solutions.parking_system.repository.RecordRepository;
import com.bit.solutions.parking_system.repository.UserRepository;
import com.bit.solutions.parking_system.service.interfaces.ConfigurationService;
import com.bit.solutions.parking_system.service.interfaces.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final RateRepository rateRepository;
    private final NotificationService notificationService;
    private final ConfigurationService configurationService;

    @Override
    public List<Record> getAllRecords() {
        log.debug("Fetching all records");
        return recordRepository.findAll();
    }

    @Override
    public Record getRecordById(Long id) {
        log.debug("Fetching record by id={}", id);
        return recordRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Record not found with id ={}",id);
                    return new ResourceNotFoundException("Record not found with id: " + id);
                });
    }

    @Override
    public Record createRecord(Record record, Long userId, Type type) {
        log.debug("Creating record for plate={}", record.getPlate());
        if (recordRepository.existsByPlateAndStatus(record.getPlate(), Status.ACTIVE)) {
            log.warn("Attempt to create duplicate active record for plate={}", record.getPlate());
            throw new BadRequestException("There is already an active record for this plate");
        }
        validateNotificationData(
                record.getNotificationMethod(),
                record.getNotificationTarget()
        );

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found with id={}", userId);
                    return new ResourceNotFoundException("User not found with id: " + userId);
                });
        Rate rate = rateRepository.findByType(type)
                .orElseThrow(() -> {
                    log.warn("Rate not found with type={}", type);
                    return new ResourceNotFoundException("Rate not found for type: " + type);
                });
        record.setUser(user);
        record.setRate(rate);
        record.setEntryTime(LocalDateTime.now());
        Record saved = recordRepository.save(record);
        configurationService.decreaseAvailableSpaces();
        notificationService.sendNotification(saved);
        log.info("Record created successfully with id={} and ticketCode={}", saved.getId(), saved.getTicketCode());
        return saved;
    }

    @Override
    public Record updateRecord(Long id, Record record,Type type) {
        log.debug("Updating record with id={}", id);
        Record existing = recordRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Record not found for update with id={}", id);
                    return new ResourceNotFoundException("Record not found with id: " + id);
                });
        if (record.getPlate() != null) {
            existing.setPlate(record.getPlate().toUpperCase().trim());
            log.debug("Updated plate for record id={}", id);
        }
        if (type != null) {
            if (existing.getStatus() != Status.ACTIVE) {
                log.warn("Attempt to change type on closed record id={}", id);
                throw new BadRequestException("Cannot change type of a closed record");
            }

            Rate newRate = rateRepository.findByType(type)
                    .orElseThrow(() -> {
                        log.warn("Rate not found with type={}", type);
                        return new ResourceNotFoundException("Rate not found for type: " + type);
                    });

            existing.setRate(newRate);
            log.debug("Updated rate/type for record id={}", id);
        }

        Record updated = recordRepository.save(existing);
        log.info("Record updated successfully with id={}", id);
        return updated;
    }

    @Override
    public void deleteRecord(Long id) {
        log.debug("Deleting record with id={}", id);
        Record existing = recordRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Record not found for deletion with id={}", id);
                    return new ResourceNotFoundException("Record not found with id: " + id);
                });
        if (existing.getStatus()==Status.ACTIVE){
            configurationService.increaseAvailableSpaces();
        }
        recordRepository.delete(existing);
        log.info("Record deleted successfully with id={}", id);
    }

    @Override
    public Record getByTicketCode(String ticketCode) {
        log.debug("Fetching record by ticketCode={}", ticketCode);
        return recordRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> {
                    log.warn("Record not found with ticketCode={}", ticketCode);
                    return new ResourceNotFoundException("Record not found with code: " + ticketCode);
                });
    }

    @Override
    public Record exitRecord(String ticketCode) {
        log.debug("Processing exit for ticketCode={}", ticketCode);
        Record record = recordRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> {
                    log.warn("Record not found with ticketCode={}", ticketCode);
                    return new ResourceNotFoundException("Record not found with code: " + ticketCode);
                });
        if (record.getStatus() != Status.ACTIVE) {
            log.warn("Attempt to exit a non-active record ticketCode={}", ticketCode);
            throw new BadRequestException("Record is not active");
        }

        LocalDateTime exitTime = LocalDateTime.now();
        record.setExitTime(exitTime);

        long minutes = java.time.Duration.between(record.getEntryTime(), exitTime).toMinutes();
        record.setTotalMinutes(minutes);

        double amount = minutes * record.getRate().getPricePerMinute();
        record.setAmountPaid(amount);

        record.setStatus(Status.FINISHED);
        configurationService.increaseAvailableSpaces();
        Record updated = recordRepository.save(record);
        log.info("Record finished successfully ticketCode={} totalMinutes={} amountPaid={}",
                ticketCode, minutes, amount);
        return updated;
    }

    @Override
    public List<Record> getActiveRecords() {
        log.debug("Fetching active records");
        return recordRepository.findByStatus(Status.ACTIVE);
    }
    private void validateNotificationData(NotificationMethod method, String target) {
        if (method == null) {
            throw new BadRequestException("Notification method is required");
        }

        if (target == null || target.isBlank()) {
            throw new BadRequestException("Notification target is required");
        }

        String cleanTarget = target.trim();

        switch (method) {
            case EMAIL:
                if (!cleanTarget.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    throw new BadRequestException("Invalid email format");
                }
                break;

            case SMS:
            case WHATSAPP:
                if (!cleanTarget.matches("^\\+?[0-9]{7,15}$")) {
                    throw new BadRequestException("Invalid phone number format");
                }
                break;

            default:
                throw new BadRequestException("Unsupported notification method");
        }}
}
