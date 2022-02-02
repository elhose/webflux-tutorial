package com.js.webfluxdemo.service;

import com.js.webfluxdemo.dto.Response;
import com.js.webfluxdemo.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class MathService {

    public Response getSquare(int number) {
        return new Response(number * number);
    }

    public List<Response> getMultiplicationTable(int number) {
        return IntStream.range(1, 11)
                        .peek(this::simulateLongProcess)
                        .map(i -> i * number)
                        .mapToObj(Response::new)
                        .toList();
    }

    private void simulateLongProcess(int currentNumber) {
        log.info("MathService currently working on -> " + currentNumber);
        SleepUtil.sleepSeconds(1);
    }


}
