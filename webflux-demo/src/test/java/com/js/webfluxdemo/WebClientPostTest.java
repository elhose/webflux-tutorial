package com.js.webfluxdemo;

import com.js.webfluxdemo.dto.MultiplyRequest;
import com.js.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebClientPostTest {

    @Autowired
    private WebClient webClient;

    @Test
    void blockingTest() {
        MultiplyRequest multiplyRequest = new MultiplyRequest(3, 5);
        Response blockingResponse = webClient.post()
                                  .uri("reactive-math/multiply")
                                  .bodyValue(multiplyRequest)
                                  .retrieve()
                                  .bodyToMono(Response.class)
                                  .block();

        assertEquals(multiplyRequest.first() * multiplyRequest.second(), blockingResponse.getOutput());
    }

    @Test
    void nonBlockingTest() {
        MultiplyRequest multiplyRequest = new MultiplyRequest(3, 5);
        Mono<Response> responseMono = webClient.post()
                                               .uri("reactive-math/multiply")
                                               .bodyValue(multiplyRequest)
                                               .retrieve()
                                               .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
                    .expectNextMatches(
                            response -> response.getOutput() == multiplyRequest.first() * multiplyRequest.second())
                    .verifyComplete();
    }






}
