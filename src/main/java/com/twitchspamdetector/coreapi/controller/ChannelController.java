package com.twitchspamdetector.coreapi.controller;

import com.twitchspamdetector.coreapi.request.CreateChannelRequest;
import com.twitchspamdetector.coreapi.response.ChannelResponse;
import com.twitchspamdetector.coreapi.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping
    public ResponseEntity<List<ChannelResponse>> listChannels() {
        return ResponseEntity.ok(channelService.listChannels());
    }

    @PostMapping
    public ResponseEntity<ChannelResponse> createChannel(@Valid @RequestBody CreateChannelRequest request) {
        ChannelResponse created = channelService.createChannel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelResponse> getChannel(@PathVariable String channelId) {
        return ResponseEntity.ok(channelService.getChannel(channelId));
    }
}
