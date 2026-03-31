package com.sensedia.sample.consents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sensedia.sample.consents.entity.enums.ConsentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsentResponseDTO(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("status")
        ConsentStatus status,

        @JsonProperty("date")
        LocalDateTime date,

        @JsonProperty("expiration_date")
        LocalDateTime expirationDate

) {}