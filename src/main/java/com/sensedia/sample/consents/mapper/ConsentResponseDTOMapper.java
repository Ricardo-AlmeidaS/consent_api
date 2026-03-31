package com.sensedia.sample.consents.mapper;

import com.sensedia.sample.consents.dto.ConsentResponseDTO;
import com.sensedia.sample.consents.entity.Consent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsentResponseDTOMapper {

    ConsentResponseDTO map(Consent consent);
}
