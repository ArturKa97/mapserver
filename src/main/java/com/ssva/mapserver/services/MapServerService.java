package com.ssva.mapserver.services;

import com.ssva.mapserver.models.MapServiceResponse;
import reactor.core.publisher.Mono;

public interface MapServerService {

    Mono<MapServiceResponse> getMapServerInfo(String url);

}
