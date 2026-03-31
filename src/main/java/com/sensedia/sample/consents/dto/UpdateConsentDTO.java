package com.sensedia.sample.consents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sensedia.sample.consents.entity.enums.ConsentStatus;

import java.time.LocalDateTime;

public record UpdateConsentDTO(

    @JsonProperty("status")
    ConsentStatus status,

    @JsonProperty("expiration_date")
    LocalDateTime expirationDate

) {}
