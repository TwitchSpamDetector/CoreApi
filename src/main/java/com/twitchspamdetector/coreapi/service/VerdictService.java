package com.twitchspamdetector.coreapi.service;

import com.twitchspamdetector.coreapi.request.IncomingVerdictRequest;
import com.twitchspamdetector.coreapi.response.PagedVerdictRecordsResponse;
import com.twitchspamdetector.coreapi.response.VerdictDecisionResponse;

public interface VerdictService {

    VerdictDecisionResponse processVerdict(String channelId, IncomingVerdictRequest request);

    PagedVerdictRecordsResponse getHistory(String channelId, int page, int size);
}
