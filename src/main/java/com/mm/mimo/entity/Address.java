package com.mm.mimo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "address_line", columnDefinition = "TEXT")
    private String addressLine;

    private String city;

    private String district;

    private String country;

    @Column(name = "postal_code")
    private String postalCode;


} 