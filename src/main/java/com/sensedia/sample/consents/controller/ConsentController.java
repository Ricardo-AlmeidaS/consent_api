package com.sensedia.sample.consents.controller;

import com.sensedia.sample.consents.dto.ConsentRequestDTO;
import com.sensedia.sample.consents.dto.ConsentResponseDTO;
import com.sensedia.sample.consents.dto.UpdateConsentDTO;
import com.sensedia.sample.consents.mapper.ConsentMapper;
import com.sensedia.sample.consents.mapper.ConsentResponseDTOMapper;
import com.sensedia.sample.consents.repository.ConsentRepository;
import com.sensedia.sample.consents.service.ConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentMapper consentMapper;
    private final ConsentResponseDTOMapper consentResponseDTOMapper;
    private final ConsentService consentService;
    private final ConsentRepository consentRepository;

    @PostMapping(value = "/consents", produces = { "application/json" })
    public ResponseEntity<Object> createConsent(@RequestBody ConsentRequestDTO dto) {

        var consent = consentMapper.map(dto);
        var novoConsent = consentService.createConsent(consent);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consentResponseDTOMapper.map(novoConsent));
    }

    @GetMapping(value = "/consents/{id}", produces = { "application/json" })
    public ResponseEntity<ConsentResponseDTO> getConsentById(@PathVariable UUID id) {
        return consentService.getConsentById(id)
                .map(consentResponseDTOMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/consents", produces = { "application/json" })
    public ResponseEntity<Page<ConsentResponseDTO>> listConsents(Pageable pageable) {

        var page = consentService.listConsents(pageable)
                .map(consentResponseDTOMapper::map);

        return ResponseEntity.ok(page);
    }

    @PutMapping(value = "/consents/{id}", produces = { "application/json" })
    public ResponseEntity<ConsentResponseDTO> updateConsent(@PathVariable UUID id, @RequestBody UpdateConsentDTO dto) {
        return consentService.updateConsent(id, dto)
                .map(consentResponseDTOMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/consents/revoke/{id}", produces = { "application/json" })
    public ResponseEntity<ConsentResponseDTO> revokeConsentById(@PathVariable UUID id) {
        return consentService.revokeConsent(id)
                .map(consentResponseDTOMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/consents/delete/{id}")
    public ResponseEntity<Void> deleteConsentById(@PathVariable UUID id) {
        var consent = consentService.deleteConsent(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Consent inexistente"
                ));

        consentRepository.delete(consent);
        return ResponseEntity.noContent().build();
    }
}
