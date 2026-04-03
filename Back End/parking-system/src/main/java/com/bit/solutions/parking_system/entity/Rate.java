package com.bit.solutions.parking_system.entity;

import com.bit.solutions.parking_system.entity.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="rate")
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

    @Column(name = "price_per_minute", nullable = false)
    private Double pricePerMinute;

    @OneToMany(mappedBy = "rate")
    private List<Record> records;

}
