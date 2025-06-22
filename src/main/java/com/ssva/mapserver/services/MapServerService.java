package com.ssva.mapserver.services;

import com.ssva.mapserver.dtos.MapServerInfo;
import reactor.core.publisher.Mono;

public interface MapServerService {

    Mono<MapServerInfo> getMapServerInfo(String url);

}
