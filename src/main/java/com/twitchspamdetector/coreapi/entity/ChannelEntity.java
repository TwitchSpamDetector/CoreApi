package com.twitchspamdetector.coreapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Representa un canal/streamer registrado en la plataforma.
 *
 * NOTA: el contrato OpenAPI describe que la acción final de un veredicto se
 * decide "según la configuración de cada canal", pero el schema actual de
 * Channel todavía no define esos campos de configuración (p. ej. umbrales de
 * spamScore por canal). Mientras tanto, VerdictServiceImpl aplica una regla
 * global fija. Cuando se agregue la configuración por canal, este es el
 * lugar natural para incorporarla (columnas nuevas + migración Flyway).
 */
@Entity
@Table(name = "channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelEntity {

    @Id
    @Column(name = "channel_id", nullable = false, updatable = false)
    private String channelId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
