package com.twitchspamdetector.coreapi.mapper;

import com.twitchspamdetector.coreapi.entity.VerdictRecordEntity;
import com.twitchspamdetector.coreapi.request.IncomingVerdictRequest;
import com.twitchspamdetector.coreapi.response.ModerationAction;
import com.twitchspamdetector.coreapi.response.VerdictDecisionResponse;
import com.twitchspamdetector.coreapi.response.VerdictRecordResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class VerdictMapper {

    public VerdictRecordEntity toEntity(String channelId, IncomingVerdictRequest request, ModerationAction action) {
        return VerdictRecordEntity.builder()
                .messageId(request.getMessageId())
                .channelId(channelId)
                .userId(request.getUserId())
                .spamScore(request.getSpamScore() != null ? request.getSpamScore().doubleValue() : null)
                .reasons(request.getReasons())
                .action(action.getValue())
                .createdAt(Instant.now())
                .build();
    }

    public VerdictDecisionResponse toDecisionResponse(VerdictRecordEntity entity) {
        return VerdictDecisionResponse.builder()
                .messageId(entity.getMessageId())
                .action(ModerationAction.fromValue(entity.getAction()))
                .build();
    }

    public VerdictRecordResponse toRecordResponse(VerdictRecordEntity entity) {
        return VerdictRecordResponse.builder()
                .messageId(entity.getMessageId())
                .userId(entity.getUserId())
                .spamScore(entity.getSpamScore())
                .reasons(entity.getReasons())
                .action(ModerationAction.fromValue(entity.getAction()))
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
