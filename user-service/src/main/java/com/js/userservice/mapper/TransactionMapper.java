package com.js.userservice.mapper;

import com.js.userservice.dto.TransactionRequestDTO;
import com.js.userservice.dto.TransactionResponseDTO;
import com.js.userservice.dto.TransactionStatus;
import com.js.userservice.entity.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionMapper {

    public Transaction transactionRequestToEntity(TransactionRequestDTO transactionRequestDTO) {
        return new Transaction(transactionRequestDTO.userId(), transactionRequestDTO.price(), LocalDateTime.now());
    }

    public TransactionResponseDTO transactionRequestDtoToResponseDto(TransactionRequestDTO transactionRequestDTO,
                                                                     TransactionStatus status) {
        return new TransactionResponseDTO(transactionRequestDTO.userId(), transactionRequestDTO.price(), status);
    }
}
