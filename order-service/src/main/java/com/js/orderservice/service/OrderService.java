package com.js.orderservice.service;

import com.js.orderservice.client.ProductClient;
import com.js.orderservice.client.UserClient;
import com.js.orderservice.dto.PurchaseOrderRequestDTO;
import com.js.orderservice.dto.PurchaseOrderResponseDTO;
import com.js.orderservice.dto.service.product.ProductDto;
import com.js.orderservice.dto.service.user.TransactionRequestDto;
import com.js.orderservice.dto.service.user.TransactionResponseDto;
import com.js.orderservice.entity.PurchaseOrder;
import com.js.orderservice.mapper.OrderMapper;
import com.js.orderservice.repository.PurchaseOrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductClient productClient;
    private final UserClient userClient;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderMapper orderMapper;

    public Mono<PurchaseOrderResponseDTO> processOrder(Mono<PurchaseOrderRequestDTO> requestDTOMono) {
        return requestDTOMono.map(ContextHelper::new)
                             .flatMap(this::getProductRequestResponse)
                             .flatMap(this::createTransaction)
                             .flatMap(this::saveOrder) // has blocking call
                             .map(orderMapper::entityToResponseDTO)
                             .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<ContextHelper> getProductRequestResponse(ContextHelper contextHelper) {
        return productClient.getProductById(contextHelper.getPurchaseOrderRequestDTO().productId())
                            .doOnNext(contextHelper::setProductDto)
                            .thenReturn(contextHelper);
    }

    private Mono<ContextHelper> createTransaction(ContextHelper contextHelper) {
        return userClient.createTransaction(new TransactionRequestDto(contextHelper.purchaseOrderRequestDTO.userId(),
                                                                      contextHelper.productDto.price()))
                         .doOnNext(contextHelper::setTransactionResponseDto)
                         .thenReturn(contextHelper);
    }

    private Mono<PurchaseOrder> saveOrder(ContextHelper contextHelper) {
        return Mono.fromSupplier(() -> purchaseOrderRepository.save(
                new PurchaseOrder(contextHelper.purchaseOrderRequestDTO.productId(),
                                  contextHelper.purchaseOrderRequestDTO.userId(),
                                  contextHelper.productDto.price(),
                                  orderMapper.mapTransactionStatusToOrderStatus(
                                          contextHelper.transactionResponseDto.status()))));
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ContextHelper {
        private final PurchaseOrderRequestDTO purchaseOrderRequestDTO;
        private ProductDto productDto;
        private TransactionResponseDto transactionResponseDto;
    }
}
