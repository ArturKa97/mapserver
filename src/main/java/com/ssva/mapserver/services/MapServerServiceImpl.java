package com.ssva.mapserver.services;

import com.ssva.mapserver.models.MapServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MapServerServiceImpl implements MapServerService {

    private final WebClient webClient;

    @Override
    public Mono<MapServerResponse> getMapServerInfo(String url) {
        String jsonUrl = url.contains("?") ? url + "&f=json" : url + "?f=json";

        return webClient.get()
                .uri(jsonUrl)
                .retrieve()
                .bodyToMono(MapServerResponse.class);
    }
}
