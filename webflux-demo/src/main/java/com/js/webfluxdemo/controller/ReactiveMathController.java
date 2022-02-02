package com.js.webfluxdemo.controller;

import com.js.webfluxdemo.dto.MultiplyRequest;
import com.js.webfluxdemo.dto.Response;
import com.js.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.InputMismatchException;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {
    private final ReactiveMathService mathService;

    @GetMapping("square/{number}")
    public Mono<Response> getSquare(@PathVariable int number) {
        return mathService.getSquare(number);
    }

    @GetMapping("table/{number}")
    public Flux<Response> getMultiplicationTable(@PathVariable int number) {
        return mathService.getMultiplicationTable(number);
    }

    @GetMapping(value = "table/{number}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> getMultiplicationTableStream(@PathVariable int number) {
        return mathService.getMultiplicationTable(number);
    }

    @PostMapping(value = "multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequest> requestMono) {
        return mathService.multiply(requestMono);
    }

    @GetMapping("square/{number}/error")
    public Mono<Response> getSquareErrorThrowing(@PathVariable int number) {
        return Mono.just(number)
                   .handle((integer, sink) -> {
                       if (integer > 0) {
                           sink.next(integer);
                       } else {
                           sink.error(new InputMismatchException(
                                   "Only positive numbers are allowed here. Passed number: " + integer));
                       }
                   })
                   .cast(Integer.class)
                   .flatMap(mathService::getSquare);
    }

}
