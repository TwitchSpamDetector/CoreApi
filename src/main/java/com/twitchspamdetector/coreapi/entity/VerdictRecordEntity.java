package com.twitchspamdetector.coreapi.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Historial de un mensaje marcado por el Motor de Moderación, junto con la
 * acción final que decidió este servicio (Core API).
 */
@Entity
@Table(name = "verdict_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerdictRecordEntity {

    @Id
    @Column(name = "message_id", nullable = false, updatable = false)
    private UUID messageId;

    @Column(name = "channel_id", nullable = false, updatable = false)
    private String channelId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "spam_score", nullable = false, updatable = false)
    private Double spamScore;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "verdict_record_reasons",
            joinColumns = @JoinColumn(name = "message_id")
    )
    @Column(name = "reason")
    private List<String> reasons;

    @Column(name = "action", nullable = false, updatable = false)
    private String action;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
