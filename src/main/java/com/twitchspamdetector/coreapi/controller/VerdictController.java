package com.twitchspamdetector.coreapi.controller;

import com.twitchspamdetector.coreapi.request.IncomingVerdictRequest;
import com.twitchspamdetector.coreapi.response.PagedVerdictRecordsResponse;
import com.twitchspamdetector.coreapi.response.VerdictDecisionResponse;
import com.twitchspamdetector.coreapi.service.VerdictService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channels/{channelId}/verdicts")
@RequiredArgsConstructor
public class VerdictController {

    private final VerdictService verdictService;

    @PostMapping
    public VerdictDecisionResponse receiveVerdict(@PathVariable String channelId,
                                                   @Valid @RequestBody IncomingVerdictRequest request) {
        return verdictService.processVerdict(channelId, request);
    }

    @GetMapping
    public PagedVerdictRecordsResponse getHistory(@PathVariable String channelId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
        return verdictService.getHistory(channelId, page, size);
    }
}
