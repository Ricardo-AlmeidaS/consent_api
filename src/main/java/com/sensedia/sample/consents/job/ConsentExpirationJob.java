package com.sensedia.sample.consents.job;

import com.sensedia.sample.consents.service.ConsentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsentExpirationJob {

    private final ConsentService consentService;

    @Scheduled(fixedDelayString = "${consents.expiration.fixed-delay-ms:150000}")
    public void expireConsents() {
        int updated = consentService.expireActiveConsents();
        log.info("Expired consents updated: {}", updated);
    }
}
