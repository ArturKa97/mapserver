package com.ssva.mapserver.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.ssva.mapserver.exceptions.MapServerClientException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MapServerServiceImplTest {

    private static WireMockServer wireMockServer;
    private MapServerServiceImpl mapServerService;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @Test
    void MapServerServiceImpl_GetMapServerInfo_ShouldReturnStatusOkWithMapServerInfo() {
        //Given
        String responseJson = """
                {
                "mapName" : "Test map",
                "description" : "Test description",
                "layers" : [{"id": 1, "name": "Layer1"}]
                }
                """;

        stubFor(get(urlPathEqualTo("/test"))
                .withQueryParam("f", equalTo("json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseJson)));

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8089")
                .build();

        mapServerService = new
                MapServerServiceImpl(webClient);
        //When + Then
        StepVerifier.create(mapServerService.getMapServerInfo("http://localhost:8089/test"))
                .assertNext(mapServerInfo -> {
                    assertThat(mapServerInfo.getMapName()).isEqualTo("Test map");
                    assertThat(mapServerInfo.getDescription()).isEqualTo("Test description");
                    assertThat(mapServerInfo.getLayers()).isNotEmpty();
                })
                .verifyComplete();
    }
    @Test
    void MapServerServiceImpl_GetMapServerInfo_ShouldReturnStatus404WithEmptyMono() {
        //Given
        stubFor(get(urlPathEqualTo("/notfound"))
                .withQueryParam("f", equalTo("json"))
                .willReturn(aResponse()
                        .withStatus(404)));

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8089")
                .build();

        mapServerService = new MapServerServiceImpl(webClient);

        // When + Then
        StepVerifier.create(mapServerService.getMapServerInfo("http://localhost:8089/notfound"))
                .verifyComplete();
    }
    @Test
    void MapServerServiceImpl_GetMapServerInfo_ShouldReturnAMapServerClientException() {
        // Given
        stubFor(get(urlPathEqualTo("/error"))
                .withQueryParam("f", equalTo("json"))
                .willReturn(aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8089")
                .build();

        mapServerService = new MapServerServiceImpl(webClient);

        //When + Then
        StepVerifier.create(mapServerService.getMapServerInfo("http://localhost:8089/error"))
                .expectError(MapServerClientException.class)
                .verify();
    }
}