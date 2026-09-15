package com.twitchspamdetector.coreapi.service;

import com.twitchspamdetector.coreapi.request.CreateChannelRequest;
import com.twitchspamdetector.coreapi.response.ChannelResponse;

import java.util.List;

public interface ChannelService {

    List<ChannelResponse> listChannels();

    ChannelResponse createChannel(CreateChannelRequest request);

    ChannelResponse getChannel(String channelId);

    /**
     * Lanza ChannelNotFoundException si el canal no existe. Lo usan otros
     * servicios (p. ej. VerdictService) para validar antes de operar.
     */
    void assertChannelExists(String channelId);
}
