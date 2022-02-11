package com.js.userservice.dto;

public record TransactionResponseDTO(Integer userId, Double price, TransactionStatus status) {
}
