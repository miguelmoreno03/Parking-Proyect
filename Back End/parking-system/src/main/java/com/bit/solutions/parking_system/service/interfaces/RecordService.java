package com.bit.solutions.parking_system.service.interfaces;
import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.entity.enums.Type;

import java.util.List;

public interface RecordService {
    List<Record> getAllRecords();
    Record getRecordById(Long id);
    Record createRecord(Record record, Long userId, Type type);
    Record updateRecord(Long id, Record record,Type type);
    void deleteRecord(Long id);
    Record getByTicketCode(String ticketCode);
    Record exitRecord(String ticketCode);
    List<Record> getActiveRecords();
}
