package com.js.userservice.controller;

import com.js.userservice.dto.TransactionRequestDTO;
import com.js.userservice.dto.TransactionResponseDTO;
import com.js.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("transaction")
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDTO> createTransaction(@RequestBody Mono<TransactionRequestDTO> transactionRequestDTOMono) {
        return transactionRequestDTOMono.flatMap(transactionService::createTransaction);
    }
}
