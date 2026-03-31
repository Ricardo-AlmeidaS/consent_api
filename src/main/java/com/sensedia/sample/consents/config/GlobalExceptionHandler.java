package com.sensedia.sample.consents.config;

import com.sensedia.sample.consents.dto.ErroResposta;
import com.sensedia.sample.consents.entity.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErroResposta> handleValidationException(ValidationException e) {

        var erro = new ErroResposta(
                "Erro Validação",
                e.getField(),
                e.getMessage()
        );

        return ResponseEntity.badRequest().body(erro);
    }
}
