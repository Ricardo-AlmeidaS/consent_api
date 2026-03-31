package com.sensedia.sample.consents.mapper;

import com.sensedia.sample.consents.dto.ConsentRequestDTO;
import com.sensedia.sample.consents.entity.Consent;
import com.sensedia.sample.consents.entity.enums.ConsentStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ConsentMapper {

    //@Mapping(source = "", target = "")
    Consent map(ConsentRequestDTO dto);

    @AfterMapping
    default void afterMapping(@MappingTarget Consent consent) {
        consent.setDate(LocalDateTime.now());
        consent.setStatus(ConsentStatus.ACTIVE);
    }
}
