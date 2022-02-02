package com.js.webfluxdemo.service;

import com.js.webfluxdemo.dto.MultiplyRequest;
import com.js.webfluxdemo.dto.Response;
import com.js.webfluxdemo.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReactiveMathService {

    public Mono<Response> getSquare(int number) {
        return Mono.fromSupplier(() -> number * number)
                   .map(Response::new);
    }

    public Flux<Response> getMultiplicationTable(int number) {
        return Flux.range(1, 10)
                   .doOnNext(this::simulateLongProcess)
                   .map(i -> i * number)
                   .map(Response::new);
    }

    private void simulateLongProcess(int currentNumber) {
        log.info("REACTIVE MathService currently working on -> " + currentNumber);
        SleepUtil.sleepSeconds(1);
    }

    public Mono<Response> multiply(Mono<MultiplyRequest> requestMono) {
        return requestMono.map(dto -> dto.first() * dto.second())
                          .map(Response::new);
    }
}
