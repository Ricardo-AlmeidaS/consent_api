package com.sensedia.sample.consents.entity.enums;

import lombok.Getter;

@Getter
public enum ConsentStatus {

    ACTIVE(0, "Active"),
    REVOKED(1, "Revoked"),
    EXPIRED(2, "Expired");

    private int code;

    private String description;

    ConsentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
