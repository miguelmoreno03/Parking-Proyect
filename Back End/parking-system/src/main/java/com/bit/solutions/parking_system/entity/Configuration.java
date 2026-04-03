package com.bit.solutions.parking_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_capacity",nullable = false)
    private Integer totalCapacity;

    @Column(name = "available_spaces",nullable = false)
    private Integer availableSpaces;

}
