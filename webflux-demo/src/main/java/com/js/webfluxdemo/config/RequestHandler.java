package com.js.webfluxdemo.config;

import com.js.webfluxdemo.dto.MultiplyRequest;
import com.js.webfluxdemo.dto.Response;
import com.js.webfluxdemo.exception.DummyException;
import com.js.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.InputMismatchException;

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

    public Mono<ServerResponse> tableHandlerStream(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("number"));
        Flux<Response> table = mathService.getMultiplicationTable(input);
        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_EVENT_STREAM)
                             .body(table, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultiplyRequest> multiplyRequestMono = serverRequest.bodyToMono(MultiplyRequest.class);
        Mono<Response> multiply = mathService.multiply(multiplyRequestMono);
        return ServerResponse.ok().body(multiply, Response.class);
    }

    public Mono<ServerResponse> squareErrorHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("number"));
        if (input < 0) {
            return Mono.error(new DummyException("Positive number only! Passed" + " -> " + input));
        }
        Mono<Response> square = mathService.getSquare(input);
        return ServerResponse.ok().body(square, Response.class);
    }
}
