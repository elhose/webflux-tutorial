package com.js.productservice.controller;

import com.js.productservice.dto.ProductDto;
import com.js.productservice.service.ProductService;
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

@RequestMapping("product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Mono<ProductDto> getProductById(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return productService.insertProduct(productDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ProductDto> updateProductById(@PathVariable("id") String id, @RequestBody Mono<ProductDto> productDtoMono) {
        return productService.updateProduct(id, productDtoMono);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteProductById(@PathVariable("id") String id) {
        return productService.deleteProduct(id);
    }

}
