package com.sensedia.sample.consents;

import com.sensedia.sample.consents.controller.ConsentController;
import com.sensedia.sample.consents.dto.ConsentRequestDTO;
import com.sensedia.sample.consents.dto.ConsentResponseDTO;
import com.sensedia.sample.consents.dto.UpdateConsentDTO;
import com.sensedia.sample.consents.entity.Consent;
import com.sensedia.sample.consents.entity.enums.ConsentStatus;
import com.sensedia.sample.consents.mapper.ConsentMapper;
import com.sensedia.sample.consents.mapper.ConsentResponseDTOMapper;
import com.sensedia.sample.consents.repository.ConsentRepository;
import com.sensedia.sample.consents.service.ConsentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsentControllerTest {

    @Mock
    private ConsentMapper consentMapper;

    @Mock
    private ConsentResponseDTOMapper consentResponseDTOMapper;

    @Mock
    private ConsentService consentService;

    @Mock
    private ConsentRepository consentRepository;

    @InjectMocks
    private ConsentController consentController;

    @Test
    void deveCriarConsentComStatusCreated() {
        var dto = new ConsentRequestDTO("12345678901", LocalDateTime.now().plusDays(30));
        var consent = new Consent();
        var responseDto = new ConsentResponseDTO(UUID.randomUUID(), dto.cpf(), ConsentStatus.ACTIVE, LocalDateTime.now(), dto.expirationDate());

        when(consentMapper.map(dto)).thenReturn(consent);
        when(consentService.createConsent(consent)).thenReturn(consent);
        when(consentResponseDTOMapper.map(consent)).thenReturn(responseDto);

        ResponseEntity<Object> response = consentController.createConsent(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void deveBuscarConsentPorIdQuandoExistir() {
        UUID id = UUID.randomUUID();
        var consent = new Consent();
        var responseDto = new ConsentResponseDTO(id, "12345678901", ConsentStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now().plusDays(10));

        when(consentService.getConsentById(id)).thenReturn(Optional.of(consent));
        when(consentResponseDTOMapper.map(consent)).thenReturn(responseDto);

        ResponseEntity<ConsentResponseDTO> response = consentController.getConsentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarConsentPorIdInexistente() {
        UUID id = UUID.randomUUID();
        when(consentService.getConsentById(id)).thenReturn(Optional.empty());

        ResponseEntity<ConsentResponseDTO> response = consentController.getConsentById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deveListarConsentsPaginado() {
        Pageable pageable = PageRequest.of(0, 10);
        var consent = new Consent();
        var responseDto = new ConsentResponseDTO(UUID.randomUUID(), "12345678901", ConsentStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now().plusDays(20));

        Page<Consent> consentPage = new PageImpl<>(List.of(consent));
        when(consentService.listConsents(pageable)).thenReturn(consentPage);
        when(consentResponseDTOMapper.map(consent)).thenReturn(responseDto);

        ResponseEntity<Page<ConsentResponseDTO>> response = consentController.listConsents(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(responseDto, response.getBody().getContent().getFirst());
    }

    @Test
    void deveAtualizarConsentQuandoExistir() {
        UUID id = UUID.randomUUID();
        var updateDto = new UpdateConsentDTO(ConsentStatus.REVOKED, LocalDateTime.now().plusDays(1));
        var consent = new Consent();
        var responseDto = new ConsentResponseDTO(id, "12345678901", ConsentStatus.REVOKED, LocalDateTime.now(), updateDto.expirationDate());

        when(consentService.updateConsent(id, updateDto)).thenReturn(Optional.of(consent));
        when(consentResponseDTOMapper.map(consent)).thenReturn(responseDto);

        ResponseEntity<ConsentResponseDTO> response = consentController.updateConsent(id, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarConsentInexistente() {
        UUID id = UUID.randomUUID();
        var updateDto = new UpdateConsentDTO(ConsentStatus.REVOKED, LocalDateTime.now().plusDays(1));

        when(consentService.updateConsent(id, updateDto)).thenReturn(Optional.empty());

        ResponseEntity<ConsentResponseDTO> response = consentController.updateConsent(id, updateDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deveRevogarConsentQuandoExistir() {
        UUID id = UUID.randomUUID();
        var consent = new Consent();
        var responseDto = new ConsentResponseDTO(id, "12345678901", ConsentStatus.REVOKED, LocalDateTime.now(), LocalDateTime.now());

        when(consentService.revokeConsent(id)).thenReturn(Optional.of(consent));
        when(consentResponseDTOMapper.map(consent)).thenReturn(responseDto);

        ResponseEntity<ConsentResponseDTO> response = consentController.revokeConsentById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoRevogarConsentInexistente() {
        UUID id = UUID.randomUUID();
        when(consentService.revokeConsent(id)).thenReturn(Optional.empty());

        ResponseEntity<ConsentResponseDTO> response = consentController.revokeConsentById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deveDeletarConsentQuandoExistir() {
        UUID id = UUID.randomUUID();
        var consent = new Consent();

        when(consentService.deleteConsent(id)).thenReturn(Optional.of(consent));

        ResponseEntity<Void> response = consentController.deleteConsentById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(consentRepository).delete(consent);
    }

    @Test
    void deveLancarNotFoundAoDeletarConsentInexistente() {
        UUID id = UUID.randomUUID();
        when(consentService.deleteConsent(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> consentController.deleteConsentById(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertInstanceOf(ResponseStatusException.class, exception);
    }
}
