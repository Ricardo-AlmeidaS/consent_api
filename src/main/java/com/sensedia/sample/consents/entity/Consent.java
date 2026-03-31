package com.sensedia.sample.consents.entity;

import com.sensedia.sample.consents.entity.enums.ConsentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "consent")
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ConsentStatus status;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

}
