package com.twitchspamdetector.coreapi.exception;

public class ChannelAlreadyExistsException extends RuntimeException {

    public ChannelAlreadyExistsException(String channelId) {
        super("Ya existe un canal registrado con channelId: " + channelId);
    }
}
