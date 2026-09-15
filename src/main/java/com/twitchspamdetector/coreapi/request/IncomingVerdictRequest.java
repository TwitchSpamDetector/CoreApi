package com.twitchspamdetector.coreapi.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Debe reflejar exactamente el schema "Verdict" que produce el Motor de
 * Moderación (ver contrato del ModerationEngine).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingVerdictRequest {

    @NotNull(message = "messageId es requerido")
    private UUID messageId;

    @NotNull(message = "userId es requerido")
    private String userId;

    @NotNull(message = "spamScore es requerido")
    private Float spamScore;

    @NotNull(message = "isSpam es requerido")
    private Boolean isSpam;

    @NotNull(message = "reasons es requerido")
    private List<String> reasons;
}
