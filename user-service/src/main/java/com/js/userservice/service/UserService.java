package com.js.userservice.service;

import com.js.userservice.dto.UserDTO;
import com.js.userservice.mapper.UserMapper;
import com.js.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public Flux<UserDTO> getAllUsers() {
        return userRepository.findAll()
                             .map(mapper::userEntityToDTO);
    }

    public Mono<UserDTO> getUserById(Integer id) {
        return userRepository.findById(id)
                             .map(mapper::userEntityToDTO);
    }

    public Mono<UserDTO> insertUser(Mono<UserDTO> userDTOMono) {
        return userDTOMono.map(mapper::userDTOToEntity)
                          .doOnNext(userRepository::save)
                          .map(mapper::userEntityToDTO);
    }

    public Mono<UserDTO> updateUserById(Integer id, Mono<UserDTO> userDTOMono) {
        return userRepository.findById(id)
                             .flatMap(user -> userDTOMono.map(mapper::userDTOToEntity)
                                                         .doOnNext(updatedUser -> updatedUser.setId(id)))
                             .flatMap(userRepository::save)
                             .map(mapper::userEntityToDTO);

    }

    public Mono<Void> deleteUserById(Integer id) {
        return userRepository.deleteById(id);
    }

}
