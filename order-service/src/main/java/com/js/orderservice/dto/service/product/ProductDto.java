package com.js.orderservice.dto.service.product;

// FIXME: 14/02/2022 I know that it's bad practice to leave duplicated DTO's but for tutorial's sake it's fine
public record ProductDto(String id, String description, Double price) {
}
