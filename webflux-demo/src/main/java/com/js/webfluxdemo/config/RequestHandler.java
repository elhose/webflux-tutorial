package com.js.webfluxdemo.config;

import com.js.webfluxdemo.dto.Response;
import com.js.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RequestHandler {
    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("number"));
        Mono<Response> square = mathService.getSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("number"));
        Flux<Response> table = mathService.getMultiplicationTable(input);
        return ServerResponse.ok().body(table, Response.class);
    }
}
