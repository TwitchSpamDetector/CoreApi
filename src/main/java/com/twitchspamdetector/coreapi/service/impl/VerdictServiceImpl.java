package com.twitchspamdetector.coreapi.service.impl;

import com.twitchspamdetector.coreapi.entity.VerdictRecordEntity;
import com.twitchspamdetector.coreapi.mapper.VerdictMapper;
import com.twitchspamdetector.coreapi.repository.VerdictRecordRepository;
import com.twitchspamdetector.coreapi.request.IncomingVerdictRequest;
import com.twitchspamdetector.coreapi.response.ModerationAction;
import com.twitchspamdetector.coreapi.response.PagedVerdictRecordsResponse;
import com.twitchspamdetector.coreapi.response.VerdictDecisionResponse;
import com.twitchspamdetector.coreapi.service.ChannelService;
import com.twitchspamdetector.coreapi.service.VerdictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerdictServiceImpl implements VerdictService {

    // TODO: mover estos umbrales a la configuración propia de cada canal en
    // cuanto el schema Channel del contrato incluya esos campos. Por ahora
    // es una regla global fija para dejar el flujo end-to-end funcionando.
    private static final double TIMEOUT_THRESHOLD = 0.9;
    private static final double DELETE_THRESHOLD = 0.6;

    private final VerdictRecordRepository verdictRecordRepository;
    private final ChannelService channelService;
    private final VerdictMapper verdictMapper;

    @Override
    public VerdictDecisionResponse processVerdict(String channelId, IncomingVerdictRequest request) {
        channelService.assertChannelExists(channelId);

        ModerationAction action = decideAction(request);
        VerdictRecordEntity entity = verdictMapper.toEntity(channelId, request, action);
        VerdictRecordEntity saved = verdictRecordRepository.save(entity);

        return verdictMapper.toDecisionResponse(saved);
    }

    @Override
    public PagedVerdictRecordsResponse getHistory(String channelId, int page, int size) {
        channelService.assertChannelExists(channelId);

        Page<VerdictRecordEntity> result = verdictRecordRepository
                .findByChannelIdOrderByCreatedAtDesc(channelId, PageRequest.of(page, size));

        return PagedVerdictRecordsResponse.builder()
                .content(result.getContent().stream().map(verdictMapper::toRecordResponse).toList())
                .page(result.getNumber())
                .totalElements(result.getTotalElements())
                .build();
    }

    private ModerationAction decideAction(IncomingVerdictRequest request) {
        if (Boolean.FALSE.equals(request.getIsSpam())) {
            return ModerationAction.NONE;
        }
        double score = request.getSpamScore() != null ? request.getSpamScore() : 0.0;
        if (score >= TIMEOUT_THRESHOLD) {
            return ModerationAction.TIMEOUT;
        }
        if (score >= DELETE_THRESHOLD) {
            return ModerationAction.DELETE;
        }
        return ModerationAction.WARN;
    }
}
