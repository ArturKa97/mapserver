package com.ssva.mapserver.services;

import com.ssva.mapserver.dtos.MapServerInfo;
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
    public Mono<MapServerInfo> getMapServerInfo(String url) {
        String jsonUrl = url.contains("?") ? url + "&f=json" : url + "?f=json";

        return webClient.get()
                .uri(jsonUrl)
                .exchangeToMono(response -> {
                    //Would be best to build a custom ExchangeFilter class to handle all exceptions and errors,
                    //for now if any request fails it will return an empty Mono which the controller will catch and throw a 404.
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(MapServerResponse.class)
                                .map(mapServerResponse -> {
                                    MapServerInfo mapServerInfo = new MapServerInfo();
                                    mapServerInfo.setMapName(mapServerResponse.getMapName());
                                    mapServerInfo.setDescription(mapServerResponse.getDescription());
                                    mapServerInfo.setLayers(mapServerResponse.getLayers());
                                    return mapServerInfo;
                                });
                    } else {
                        return Mono.empty();
                    }
                })
                .onErrorResume(e -> Mono.empty());

    }
}
