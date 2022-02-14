package com.js.orderservice.controller;

import com.js.orderservice.dto.PurchaseOrderRequestDTO;
import com.js.orderservice.dto.PurchaseOrderResponseDTO;
import com.js.orderservice.service.OrderQueryService;
import com.js.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("order")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderQueryService queryService;

    @PostMapping
    public Mono<PurchaseOrderResponseDTO> processOrder(@RequestBody Mono<PurchaseOrderRequestDTO> purchaseOrderRequestDTOMono) {
        return orderService.processOrder(purchaseOrderRequestDTOMono);
    }

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDTO> getPurchasesForUser(@PathVariable("id") Integer id) {
        return queryService.getProductsByUserId(id);
    }
}
