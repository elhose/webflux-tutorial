package com.js.productservice.data;

import com.js.productservice.dto.ProductDto;
import com.js.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSetup implements CommandLineRunner {
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {

        Flux.from(prepareDummyData())
            .doFirst(() -> productService.deleteAllProducts().subscribe())
            .flatMap(p -> productService.insertProduct(Mono.just(p)))
            .subscribe(productDto -> log.info("Inserted dummy test data -> {}", productDto),
                       e -> log.error(e.getMessage()),
                       () -> log.info("All dummy test data inserted!"));
    }

    private Flux<ProductDto> prepareDummyData() {
        return Flux.just(new ProductDto("11", "ergonomic mouse", 400.0),
                         new ProductDto("22", "SSD 512 GB", 300.0),
                         new ProductDto("33", "Monitor", 1250.0),
                         new ProductDto("44", "ergonomic chair", 1950.0),
                         new ProductDto("55", "compressed air", 15.0));
    }
}
