package com.ssva.mapserver.controllers;

import com.ssva.mapserver.dtos.MapServerInfo;
import com.ssva.mapserver.models.Layer;
import com.ssva.mapserver.services.MapServerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MapServerController.class)
public class MapServerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MapServerService mapServerService;

    @Test
    void MapServerController_GetMapServerInfo_ShouldReturnMapServerInfo() {
        //Given
        MapServerInfo mockInfo = new MapServerInfo();
        mockInfo.setMapName("Mock Map");
        mockInfo.setDescription("Mock Description");
        mockInfo.setLayers(List.of(new Layer(1, "Layer1")));

        when(mapServerService.getMapServerInfo(anyString()))
                .thenReturn(Mono.just(mockInfo));

        // When + Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/mapserver-info")
                        .queryParam("url", "http://someurl")
                        .queryParam("f", "json")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MapServerInfo.class)
                .value(info -> {
                    assert info.getMapName().equals(mockInfo.getMapName());
                    assert info.getDescription().equals(mockInfo.getDescription());
                    assert info.getLayers().equals(mockInfo.getLayers());
                });
    }

    @Test
    void MapServerController_GetMapServerInfo_ShouldReturn404WithEmptyMono() {
        //Given
        when(mapServerService.getMapServerInfo(anyString()))
                .thenReturn(Mono.empty());

        //When + Then
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/mapserver-info")
                        .queryParam("url", "http://someurl")
                        .queryParam("f", "json")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

}