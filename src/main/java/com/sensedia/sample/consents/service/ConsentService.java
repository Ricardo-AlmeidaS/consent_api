package com.sensedia.sample.consents.service;

import com.sensedia.sample.consents.dto.UpdateConsentDTO;
import com.sensedia.sample.consents.entity.Consent;
import com.sensedia.sample.consents.entity.enums.ConsentStatus;
import com.sensedia.sample.consents.repository.ConsentRepository;
import com.sensedia.sample.consents.validator.CPFValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ConsentRepository consentRepository;
    private final CPFValidator cpfValidator;

    public Consent createConsent(Consent consent) {
        cpfValidator.validate(consent.getCpf());
        consentRepository.save(consent);
        return consent;
    }

    public Optional<Consent> getConsentById(@PathVariable UUID id) {
        Optional<Consent> consent = consentRepository.findById(id);
        return consent;
    }

    public Optional<Consent> revokeConsent(@PathVariable UUID id) {

        return consentRepository.findById(id)
                .map(consent -> {
                    markStatusAsRevoked(consent);
                    return consentRepository.save(consent);
                });
    }

    public void markStatusAsRevoked(Consent consent) {
        consent.setStatus(ConsentStatus.REVOKED);
        consent.setExpirationDate(LocalDateTime.now());
    }

    public Page<Consent> listConsents(Pageable pageable) {
        return consentRepository.findAll(pageable);
    }

    public Optional<Consent> updateConsent(UUID id, UpdateConsentDTO dto) {

        return consentRepository.findById(id)
                .map(consent -> {
                    updateConsentFields(dto, consent);
                    return consentRepository.save(consent);
                });
    }

    private void updateConsentFields(UpdateConsentDTO dto, Consent consent) {
        consent.setStatus(dto.status());
        consent.setExpirationDate(dto.expirationDate());
    }

    public Optional<Consent> deleteConsent(UUID id) {
        return consentRepository.findById(id);
    }

    @Transactional
    public int expireActiveConsents() {
        return consentRepository.expireActiveConsents(
                LocalDateTime.now()
        );
    }
}
