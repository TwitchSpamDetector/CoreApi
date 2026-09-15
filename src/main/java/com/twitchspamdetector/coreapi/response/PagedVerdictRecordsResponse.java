package com.twitchspamdetector.coreapi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Corresponde al objeto inline que devuelve
 * GET /channels/{channelId}/verdicts en el contrato (content, page,
 * totalElements). Se le puso nombre propio aquí para poder generar/mapear
 * un DTO real; si se retoca el YAML del contrato, considera nombrar ese
 * schema inline como "PagedVerdictRecords" para que quede 1:1.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedVerdictRecordsResponse {

    private List<VerdictRecordResponse> content;
    private int page;
    private long totalElements;
}
