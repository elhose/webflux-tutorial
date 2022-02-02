package com.js.webfluxdemo.config;

import com.js.webfluxdemo.exception.DummyException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                              .GET("router/square/{number}", requestHandler::squareHandler)
                              .GET("router/table/{number}", requestHandler::tableHandler)
                              .GET("router/table/{number}/stream", requestHandler::tableHandlerStream)
                              .POST("router/multiply", requestHandler::multiplyHandler)
                              .GET("router/square/{number}/error", requestHandler::squareErrorHandler)
                              .onError(DummyException.class, (e, serverRequest) -> ServerResponse.badRequest().bodyValue(e.getMessage()))
                              .build();
    }
}
