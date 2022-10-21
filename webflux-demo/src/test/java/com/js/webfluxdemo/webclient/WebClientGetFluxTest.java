package com.js.webfluxdemo.webclient;

import com.js.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebClientGetFluxTest {

    private static final int EXAMPLE_NUMBER = 6;

    @Autowired
    private WebClient webClient;

    @Test
    void blockingTest() {
        List<Response> blockingResponse = webClient.get()
                                        .uri("reactive-math/table/{number}", EXAMPLE_NUMBER)
                                        .retrieve()
                                        .bodyToFlux(Response.class)
                                        .collectList()
                                        .block();

        assertEquals(10, blockingResponse.size());
        assertEquals(EXAMPLE_NUMBER, blockingResponse.get(0).getOutput());
        assertEquals(EXAMPLE_NUMBER * 10, blockingResponse.get(9).getOutput());
    }

    @Test
    void nonBlockingTest() {
        Flux<Response> responseFlux = webClient.get()
                                               .uri("reactive-math/table/{number}", EXAMPLE_NUMBER)
                                               .retrieve()
                                               .bodyToFlux(Response.class)
                                               .log();

        StepVerifier.create(responseFlux)
                    .expectNextCount(10)
                    .verifyComplete();
    }

    @Test
    void streamEndpointTest() {
        Flux<Response> responseFlux = webClient.get()
                                               .uri("reactive-math/table/{number}/stream", EXAMPLE_NUMBER)
                                               .retrieve()
                                               .bodyToFlux(Response.class)
                                               .log();

        StepVerifier.create(responseFlux)
                    .expectNextCount(10)
                    .verifyComplete();
    }


}
