package com.twitchspamdetector.coreapi.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Debe coincidir exactamente con el enum "action" definido en el contrato
 * OpenAPI (VerdictDecision.action / VerdictRecord.action): none, warn,
 * delete, timeout.
 */
public enum ModerationAction {
    NONE("none"),
    WARN("warn"),
    DELETE("delete"),
    TIMEOUT("timeout");

    private final String value;

    ModerationAction(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ModerationAction fromValue(String value) {
        for (ModerationAction action : values()) {
            if (action.value.equalsIgnoreCase(value)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Acción de moderación desconocida: " + value);
    }
}
