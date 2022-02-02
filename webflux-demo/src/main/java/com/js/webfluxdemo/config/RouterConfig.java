package com.js.webfluxdemo.config;

import com.js.webfluxdemo.exception.DummyException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                              .path("router", this::serverResponseRouterFunction)
                              .path("predicate", this::predicateResponseRouterFunction)
                              .build();
    }

    private RouterFunction<ServerResponse> predicateResponseRouterFunction() {
        return RouterFunctions.route()
                              .GET("square/{number}", RequestPredicates.path("*/1?"),
                                   request -> ServerResponse.ok().bodyValue("FIRST CONDITION"))
                              .GET("square/{number}", RequestPredicates.path("*/0"),
                                   request -> ServerResponse.ok().bodyValue("ZERO"))
                              .GET("square/{number}",
                                   request -> ServerResponse.badRequest().bodyValue("ERROR ON EVEYTHING ELSE"))
                              .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                              .GET("square/{number}", requestHandler::squareHandler)
                              .GET("table/{number}", requestHandler::tableHandler)
                              .GET("table/{number}/stream", requestHandler::tableHandlerStream)
                              .POST("multiply", requestHandler::multiplyHandler)
                              .GET("square/{number}/error", requestHandler::squareErrorHandler)
                              .onError(DummyException.class,
                                       (e, serverRequest) -> ServerResponse.badRequest().bodyValue(e.getMessage()))
                              .build();
    }
}
