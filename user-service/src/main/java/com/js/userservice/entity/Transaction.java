package com.js.userservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Transaction {
    @Id
    private Integer id;
    private Integer userId;
    private Double price;
    private LocalDateTime transactionDate;

    public Transaction(Integer userId, Double price, LocalDateTime transactionDate) {
        this.userId = userId;
        this.price = price;
        this.transactionDate = transactionDate;
    }
}
