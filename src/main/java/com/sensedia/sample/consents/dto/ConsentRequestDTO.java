package com.sensedia.sample.consents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ConsentRequestDTO(

        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("expirationDate")
        LocalDateTime expirationDate

) {}