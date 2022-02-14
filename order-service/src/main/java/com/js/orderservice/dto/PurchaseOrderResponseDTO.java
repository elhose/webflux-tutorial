package com.js.orderservice.dto;

public record PurchaseOrderResponseDTO(Integer orderId, Integer userId, String productId, Double amount,
                                       OrderStatus orderStatus) {
}
