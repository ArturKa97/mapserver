package com.ssva.mapserver.controllers;

import com.ssva.mapserver.models.MapServiceResponse;
import com.ssva.mapserver.services.MapServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MapServerController {

    private final MapServerService mapServerService;

    @GetMapping("/mapserver-info")
    public Mono<ResponseEntity<MapServiceResponse>> getMapServerInfo(@RequestParam String url) {
        String trimmedUrl = url.trim();

        return mapServerService.getMapServerInfo(trimmedUrl)
                .map(ResponseEntity::ok);
    }

}
