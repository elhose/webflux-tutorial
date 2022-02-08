package com.js.productservice.mapper;

import com.js.productservice.dto.ProductDto;
import com.js.productservice.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getDescription(), product.getPrice());
    }

    public Product toEntity(ProductDto productDto) {
        return new Product(productDto.id(), productDto.description(), productDto.price());
    }
}
