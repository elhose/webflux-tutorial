package com.js.userservice.repository;

import com.js.userservice.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    @Query("update user " +
            "set balance = balance - :amount" +
            "where id = :userId" +
            "and balance >= :amount")
    Mono<Boolean> deductAmountFromUserBalance(Integer userId, Double amount);
}