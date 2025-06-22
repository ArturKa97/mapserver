package com.ssva.mapserver.controllers;

import com.ssva.mapserver.models.MapServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MapServerController {

    private final WebClient webClient;

    @GetMapping("/mapserver-info")
    public Mono<ResponseEntity<MapServiceResponse>> getMapServerInfo (@RequestParam String url) {
        //Checking if url already contains query params, if yes we add more to it, if not we add ?f=json to get the data as json from the API call
        String jsonUrl = url.contains("?") ? url + "&f=json" : url + "?f=json";

        return webClient.get()
                .uri(jsonUrl)
                .retrieve()
                .bodyToMono(MapServiceResponse.class)
                .map(ResponseEntity::ok);
    }

}
