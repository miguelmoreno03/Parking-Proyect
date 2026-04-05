package com.bit.solutions.parking_system.controller;

import com.bit.solutions.parking_system.dto.RecordCreateDTO;
import com.bit.solutions.parking_system.dto.RecordResponseDTO;
import com.bit.solutions.parking_system.dto.RecordUpdateDTO;
import com.bit.solutions.parking_system.mappers.RecordMapper;
import com.bit.solutions.parking_system.service.interfaces.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.bit.solutions.parking_system.entity.Record;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("records")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<RecordResponseDTO>> getAll() {
        List<Record> records = recordService.getAllRecords();
        List<RecordResponseDTO> response = new ArrayList<>();

        for (Record record : records) {
            response.add(RecordMapper.toDTO(record));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordResponseDTO> getById(@PathVariable Long id) {
        Record record = recordService.getRecordById(id);
        return ResponseEntity.ok(RecordMapper.toDTO(record));
    }
    @PostMapping
    public ResponseEntity<RecordResponseDTO> create(
            @Valid @RequestBody RecordCreateDTO dto) {

        Record record = RecordMapper.toEntity(dto);

        Record saved = recordService.createRecord(
                record,
                dto.getUserId(),
                dto.getType()
        );

        return ResponseEntity
                .created(URI.create("/records/" + saved.getId()))
                .body(RecordMapper.toDTO(saved));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RecordResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RecordUpdateDTO dto) {

        Record record = new Record();
        record.setPlate(dto.getPlate());

        Record updated = recordService.updateRecord(
                id,
                record,
                dto.getType()
        );

        return ResponseEntity.ok(RecordMapper.toDTO(updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.ok("Record with id " + id + " was successfully deleted");
    }
    @GetMapping("/ticket/{ticketCode}")
    public ResponseEntity<RecordResponseDTO> getByTicketCode(@PathVariable String ticketCode) {
        Record record = recordService.getByTicketCode(ticketCode);
        return ResponseEntity.ok(RecordMapper.toDTO(record));
    }
    @PostMapping("/exit/{ticketCode}")
    public ResponseEntity<RecordResponseDTO> exit(@PathVariable String ticketCode) {
        Record record = recordService.exitRecord(ticketCode);
        return ResponseEntity.ok(RecordMapper.toDTO(record));
    }
    @GetMapping("/active")
    public ResponseEntity<List<RecordResponseDTO>> getActive() {
        List<Record> records = recordService.getActiveRecords();
        List<RecordResponseDTO> response = new ArrayList<>();

        for (Record record : records) {
            response.add(RecordMapper.toDTO(record));
        }

        return ResponseEntity.ok(response);
    }
}
