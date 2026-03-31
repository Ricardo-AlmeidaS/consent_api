package com.sensedia.sample.consents.validator;

import com.sensedia.sample.consents.entity.exception.ValidationException;
import com.sensedia.sample.consents.repository.ConsentRepository;
import lombok .RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CPFValidator {

    private final ConsentRepository consentRepository;

    public void validate(String cpf) {

        if (consentRepository.existsByCpf(cpf)) {
            throw new ValidationException("cpf", "CPF já cadastrado");
        }

        if (cpf == null) {
            throw new ValidationException("cpf", "CPF não pode ser nulo");
        }

        if (!cpf.matches("\\d{11}")) {
            throw new ValidationException("cpf", "CPF deve conter exatamente 11 dígitos numéricos");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new ValidationException("cpf", "CPF inválido (números repetidos)");
        }
    }
}
