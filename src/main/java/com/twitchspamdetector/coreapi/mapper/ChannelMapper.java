package com.twitchspamdetector.coreapi.mapper;

import com.twitchspamdetector.coreapi.entity.ChannelEntity;
import com.twitchspamdetector.coreapi.request.CreateChannelRequest;
import com.twitchspamdetector.coreapi.response.ChannelResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChannelMapper {

    public ChannelEntity toEntity(CreateChannelRequest request) {
        return ChannelEntity.builder()
                .channelId(request.getChannelId())
                .displayName(request.getDisplayName())
                .createdAt(Instant.now())
                .build();
    }

    public ChannelResponse toResponse(ChannelEntity entity) {
        return ChannelResponse.builder()
                .channelId(entity.getChannelId())
                .displayName(entity.getDisplayName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
