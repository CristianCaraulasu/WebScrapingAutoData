package com.example.db.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TractionType {

    FRONT_TRACTION("Tracţiunea faţă "),
    REAR_TRACTION("Tracţiunea spate "),
    INTEGRAL_TRACTION("Tracţiune integrală (4x4) ");

    @Getter
    private final String value;

    public static TractionType fromValue(String value) {
        for (TractionType e : values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
