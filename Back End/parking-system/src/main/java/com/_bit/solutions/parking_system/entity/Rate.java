package com._bit.solutions.parking_system.entity;

import com._bit.solutions.parking_system.entity.Enum.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private Type type;

    @Column(name = "price_per_minute", nullable = false, precision = 10, scale = 2)
    private Double pricePerMinute;

    @OneToMany(mappedBy = "rate")
    private List<Record> records;

}
