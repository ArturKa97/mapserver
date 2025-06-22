package com.ssva.mapserver.services;

import com.ssva.mapserver.models.MapServerResponse;
import reactor.core.publisher.Mono;

public interface MapServerService {

    Mono<MapServerResponse> getMapServerInfo(String url);

}
