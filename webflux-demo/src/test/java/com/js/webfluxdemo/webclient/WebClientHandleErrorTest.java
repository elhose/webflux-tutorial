package com.js.webfluxdemo.webclient;

import com.js.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebClientHandleErrorTest {

    private static final int EXAMPLE_NUMBER = -6;

    @Autowired
    private WebClient webClient;

    @Test
    void handleException() {
        Mono<Response> responseMono = webClient.get()
                                               .uri("reactive-math/square/{number}/error", EXAMPLE_NUMBER)
                                               .retrieve()
                                               .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
                    .expectError(WebClientResponseException.InternalServerError.class)
                    .verify();
    }

}
