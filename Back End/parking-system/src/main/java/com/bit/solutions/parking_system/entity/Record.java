package com.bit.solutions.parking_system.entity;
import com.bit.solutions.parking_system.entity.enums.Status;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_code", nullable = false, unique = true, length = 50)
    private String ticketCode;

    @Column(nullable = false, length = 10)
    private String plate;

    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @Column(name = "total_minutes")
    private Long totalMinutes;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationMethod notificationMethod;
    @Column(name = "notification_target")
    private String notificationTarget;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "rate_id", nullable = false)
    private Rate rate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.ticketCode == null || this.ticketCode.isBlank()) {
            this.ticketCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}