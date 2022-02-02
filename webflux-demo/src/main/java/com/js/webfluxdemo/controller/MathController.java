package com.js.webfluxdemo.controller;

import com.js.webfluxdemo.dto.Response;
import com.js.webfluxdemo.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("math")
@RequiredArgsConstructor
public class MathController {
    private final MathService mathService;

    @GetMapping("square/{number}")
    public ResponseEntity<Response> getSquare(@PathVariable int number) {
        return ResponseEntity.ok(mathService.getSquare(number));
    }

    @GetMapping("table/{number}")
    public ResponseEntity<List<Response>> getMultiplicationTable(@PathVariable int number) {
        return ResponseEntity.ok(mathService.getMultiplicationTable(number));
    }
}
