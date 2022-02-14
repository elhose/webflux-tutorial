package com.js.userservice.controller;

import com.js.userservice.dto.PostUserDTO;
import com.js.userservice.dto.UserDTO;
import com.js.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("all")
    public Flux<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public Mono<UserDTO> getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Mono<UserDTO> saveUser(@RequestBody Mono<PostUserDTO> postUserDTOMono) {
        return userService.insertUser(postUserDTOMono);
    }

    @PutMapping("{id}")
    public Mono<UserDTO> updateUser(@PathVariable("id") Integer id, @RequestBody Mono<UserDTO> userDTOMono) {
        return userService.updateUserById(id, userDTOMono);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable("id") Integer id) {
        return userService.deleteUserById(id);
    }
}

