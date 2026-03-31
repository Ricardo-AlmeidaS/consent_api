package com.sensedia.sample.consents.dto;

import lombok.Data;

public record ErroResposta(String mesnagem, String campo, String erro) {
}
