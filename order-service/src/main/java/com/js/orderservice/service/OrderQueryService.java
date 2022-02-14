package com.js.orderservice.service;

import com.js.orderservice.dto.PurchaseOrderResponseDTO;
import com.js.orderservice.mapper.OrderMapper;
import com.js.orderservice.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderMapper orderMapper;

    public Flux<PurchaseOrderResponseDTO> getProductsByUserId(int userId) {
        return Flux.fromIterable(purchaseOrderRepository.findAllByUserId(userId)) // blocking
                   .map(orderMapper::entityToResponseDTO)
                   .subscribeOn(Schedulers.boundedElastic());
    }
}

