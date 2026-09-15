package com.twitchspamdetector.coreapi.service.impl;

import com.twitchspamdetector.coreapi.entity.ChannelEntity;
import com.twitchspamdetector.coreapi.exception.ChannelAlreadyExistsException;
import com.twitchspamdetector.coreapi.exception.ChannelNotFoundException;
import com.twitchspamdetector.coreapi.mapper.ChannelMapper;
import com.twitchspamdetector.coreapi.repository.ChannelRepository;
import com.twitchspamdetector.coreapi.request.CreateChannelRequest;
import com.twitchspamdetector.coreapi.response.ChannelResponse;
import com.twitchspamdetector.coreapi.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;

    @Override
    public List<ChannelResponse> listChannels() {
        return channelRepository.findAll().stream()
                .map(channelMapper::toResponse)
                .toList();
    }

    @Override
    public ChannelResponse createChannel(CreateChannelRequest request) {
        if (channelRepository.existsById(request.getChannelId())) {
            throw new ChannelAlreadyExistsException(request.getChannelId());
        }
        ChannelEntity saved = channelRepository.save(channelMapper.toEntity(request));
        return channelMapper.toResponse(saved);
    }

    @Override
    public ChannelResponse getChannel(String channelId) {
        ChannelEntity entity = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId));
        return channelMapper.toResponse(entity);
    }

    @Override
    public void assertChannelExists(String channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new ChannelNotFoundException(channelId);
        }
    }
}
