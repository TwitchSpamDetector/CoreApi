package com.twitchspamdetector.coreapi.exception;

public class ChannelNotFoundException extends RuntimeException {

    public ChannelNotFoundException(String channelId) {
        super("No se encontró el canal solicitado: " + channelId);
    }
}
