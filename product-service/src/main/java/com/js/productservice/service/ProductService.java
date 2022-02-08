package com.js.productservice.service;

import com.js.productservice.dto.ProductDto;
import com.js.productservice.mapper.ProductMapper;
import com.js.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll()
                                .map(productMapper::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return productRepository.findById(id)
                                .map(productMapper::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono.map(productMapper::toEntity)
                             .flatMap(productRepository::insert)
                             .map(productMapper::toDto);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                                .flatMap(oldProduct -> productDtoMono
                                        .map(productMapper::toEntity)
                                        .doOnNext(product -> product.setId(id)))
                                .flatMap(productRepository::save)
                                .map(productMapper::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }

    public Flux<ProductDto> getAllProductsWithinRange(double min, double max) {
        return productRepository.findByPriceBetween(min, max)
                                .map(productMapper::toDto);
    }
}
