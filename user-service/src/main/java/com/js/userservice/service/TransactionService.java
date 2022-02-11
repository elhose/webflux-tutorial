package com.js.userservice.service;

import com.js.userservice.dto.TransactionRequestDTO;
import com.js.userservice.dto.TransactionResponseDTO;
import com.js.userservice.dto.TransactionStatus;
import com.js.userservice.mapper.TransactionMapper;
import com.js.userservice.repository.TransactionRepository;
import com.js.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    public Mono<TransactionResponseDTO> createTransaction(TransactionRequestDTO requestDTO) {
        return userRepository.deductAmountFromUserBalance(requestDTO.userId(), requestDTO.price())
                             .filter(Boolean::booleanValue)
                             .map(b -> transactionMapper.transactionRequestToEntity(requestDTO))
                             .flatMap(transactionRepository::save)
                             .map(transaction -> transactionMapper.transactionRequestDtoToResponseDto(
                                     requestDTO, TransactionStatus.APPROVED))
                             .defaultIfEmpty(transactionMapper.transactionRequestDtoToResponseDto(requestDTO,
                                                                                                  TransactionStatus.DECLINED));
    }

}
