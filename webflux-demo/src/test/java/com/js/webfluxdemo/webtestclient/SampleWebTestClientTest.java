package com.js.webfluxdemo.webtestclient;

import com.js.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureWebTestClient
class SampleWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void asd() {
        webTestClient.get()
                     .uri("reactive-math/square/{number}", 10)
                     .exchange()
                     .expectStatus().is2xxSuccessful()
                     .expectHeader().contentType(MediaType.APPLICATION_JSON)
                     .expectBody(Response.class)
                     .consumeWith(result -> Assertions.assertEquals(result.getResponseBody().getOutput(), 100));

    }
}
