package com.js.webfluxdemo;

import com.js.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebClientGetMonoTest {

    private static final int EXAMPLE_NUMBER = 6;

    @Autowired
    private WebClient webClient;

    @Test
    void blockingTest() {
        Response blockingResponse = webClient.get()
                                  .uri("reactive-math/square/{number}", EXAMPLE_NUMBER)
                                  .retrieve()
                                  .bodyToMono(Response.class)
                                  .block();

        assertEquals(Math.pow(EXAMPLE_NUMBER, 2), blockingResponse.getOutput());
    }

    @Test
    void nonBlockingTest() {
        Mono<Response> responseMono = webClient.get()
                                               .uri("reactive-math/square/{number}", EXAMPLE_NUMBER)
                                               .retrieve()
                                               .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
                    .expectNextMatches(response -> response.getOutput() == Math.pow(EXAMPLE_NUMBER, 2))
                    .verifyComplete();
    }

}
